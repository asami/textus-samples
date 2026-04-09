package caller

import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import scala.jdk.CollectionConverters.*
import scala.util.Using
import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.datatype.PathName
import org.goldenport.protocol.Request
import org.goldenport.protocol.Response
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.protocol.spec.*
import org.goldenport.cncf.action.{ActionCall, QueryAction}
import org.goldenport.cncf.component.{Component, ComponentCreate, ComponentId}
import org.goldenport.cncf.component.DescriptorRecordLoader
import org.goldenport.schema.DataType

final class CallerComponent extends Component {
  override def subsystemDefinitionRecords: Vector[Record] =
    Vector(Record.data("name" -> "testsubsystemwiring"))
}

object CallerComponent extends Component.Factory {
  val name = "callercomp"
  val componentId = ComponentId(name)

  protected def create_Components(params: ComponentCreate): Vector[Component] =
    Vector(CallerComponent())

  protected def create_Core(
    params: ComponentCreate,
    comp: Component
  ): Component.Core =
    spec_create(
      name,
      componentId,
      MainService
    )
}

object MainService extends ServiceDefinition {
  val specification = ServiceDefinition.Specification.Builder("main")
    .operation(HelloOperation)
    .build()

  object HelloOperation extends OperationDefinition {
    val specification = OperationDefinition.Specification.Builder("hello").copy(
      response = ResponseDefinition(result = List(DataType.Named("Record")))
    ).build()

    override def createOperationRequest(
      req: Request
    ): Consequence[HelloQuery] =
      Consequence.success(HelloQuery(req))
  }
}

final case class HelloQuery(
  request: Request
) extends QueryAction() {
  override def createCall(core: ActionCall.Core): ActionCall =
    HelloActionCall(core, this)
}

final case class HelloActionCall(
  core: ActionCall.Core,
  query: HelloQuery
) extends ActionCall {
  override def execute(): Consequence[OperationResponse] = {
    val wiring = _wiring_record()
    val includeCalltree = executionContext.framework.callTreeEnabled
    val targetComponent = wiring.getString(PathName(Vector("callercomp", "main", "hello", "target_component")))
      .getOrElse("calleecomp")
    val targetService = wiring.getString(PathName(Vector("callercomp", "main", "hello", "target_service")))
      .getOrElse("main")
    val targetOperation = wiring.getString(PathName(Vector("callercomp", "main", "hello", "target_operation")))
      .getOrElse("hello")
    val delegatedRequest = Request.of(targetComponent, targetService, targetOperation)
    for {
      subsystem <- component.flatMap(_.subsystem).map(Consequence.success).getOrElse(Consequence.failure("subsystem is not initialized"))
      response <- subsystem.execute(delegatedRequest)
      text <- _response_text(response)
    } yield {
      val ports = subsystem.descriptor.map(_.declaredPorts).getOrElse(Vector.empty)
      val wiringBindings = subsystem.descriptor.map(_.resolvedWiringBindings).getOrElse(Vector.empty)
      val fields = Vector.newBuilder[(String, Any)]
      fields += "message" -> s"callercomp delegated to ${targetComponent}.${targetService}.${targetOperation} -> ${text}"
      fields += "delegated_to" -> Record.data(
        "component" -> targetComponent,
        "service" -> targetService,
        "operation" -> targetOperation
      )
      fields += "callee_result" -> text
      fields += "ports" -> ports
      fields += "wiring" -> wiring
      fields += "wiringBindings" -> wiringBindings
      if (includeCalltree) {
        val calltree = executionContext.observability.callTreeContext.build().map(_.toRecord).getOrElse(Record.empty)
        fields += "calltree" -> calltree
      }
      val result = Record.data(fields.result()*)
      OperationResponse.RecordResponse(result)
    }
  }

  private def _wiring_record(): Record =
    component
      .flatMap(_.subsystem)
      .flatMap(_.descriptor)
      .map { descriptor =>
        if (!descriptor.wiring.isEmpty)
          descriptor.wiring
        else
          _load_descriptor_record(descriptor.path)
            .flatMap(_wiring_from_record)
            .filterNot(_.isEmpty)
            .orElse(_load_wiring_from_text(descriptor.path))
            .getOrElse(Record.empty)
      }
      .getOrElse(Record.empty)

  private def _load_descriptor_record(path: java.nio.file.Path): Option[Record] = {
    val name = path.getFileName.toString.toLowerCase
    if (name.endsWith(".sar") || name.endsWith(".zip")) {
      val uri = URI.create(s"jar:${path.toUri}")
      Using.resource(FileSystems.newFileSystem(uri, Map.empty[String, String].asJava)) { fs =>
        Vector(
          "subsystem-descriptor.yaml",
          "subsystem-descriptor.yml",
          "descriptor.yaml",
          "descriptor.yml",
          "subsystem-descriptor.json",
          "descriptor.json"
        ).iterator
          .map(fs.getPath("/").resolve(_))
          .find(java.nio.file.Files.isRegularFile(_))
          .flatMap(p => DescriptorRecordLoader.load(p).toOption.flatMap(_.headOption))
      }
    } else {
      DescriptorRecordLoader.load(path).toOption.flatMap(_.headOption)
    }
  }

  private def _wiring_from_record(rec: Record): Option[Record] =
    rec.getRecord("wiring").orElse {
      val entries = rec.asMap.iterator.collect {
        case (k, v) if k.startsWith("wiring/") =>
          k.stripPrefix("wiring/") -> v
        case (k, v) if k.startsWith("wiring.") =>
          k.stripPrefix("wiring.") -> v
      }.toVector
      if (entries.isEmpty) None else Some(Record.create(entries))
    }

  private def _load_wiring_from_text(path: java.nio.file.Path): Option[Record] = {
    def parse(text: String): Option[Record] = {
      val entries = text.linesIterator.flatMap { line =>
        val trimmed = line.trim
        if (trimmed.startsWith("wiring/") || trimmed.startsWith("wiring.")) {
          trimmed.split(":", 2) match {
            case Array(k, v) =>
              Some(k.stripPrefix("wiring/").stripPrefix("wiring.") -> v.trim)
            case _ =>
              None
          }
        } else None
      }.toVector
      if (entries.isEmpty) None else Some(Record.create(entries))
    }

    val name = path.getFileName.toString.toLowerCase
    if (name.endsWith(".sar") || name.endsWith(".zip")) {
      val uri = URI.create(s"jar:${path.toUri}")
      Using.resource(FileSystems.newFileSystem(uri, Map.empty[String, String].asJava)) { fs =>
        Vector(
          "subsystem-descriptor.yaml",
          "subsystem-descriptor.yml",
          "descriptor.yaml",
          "descriptor.yml"
        ).iterator
          .map(fs.getPath("/").resolve(_))
          .find(Files.isRegularFile(_))
          .flatMap(p => parse(Files.readString(p)))
      }
    } else if (Files.isRegularFile(path)) {
      parse(Files.readString(path))
    } else {
      None
    }
  }

  private def _response_text(response: Response): Consequence[String] =
    response match {
      case Response.Void() =>
        Consequence.success("")
      case Response.Scalar(value) =>
        Consequence.success(value.toString)
      case Response.Json(value) =>
        Consequence.success(value)
      case Response.Yaml(value) =>
        Consequence.success(value)
      case Response.Xml(value) =>
        Consequence.success(value)
      case Response.Opaque(value) =>
        Consequence.success(value.toString)
    }
}
