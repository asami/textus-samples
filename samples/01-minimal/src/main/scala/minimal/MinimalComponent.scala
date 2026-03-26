package minimal

import org.goldenport.Consequence
import org.goldenport.protocol.Request
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.protocol.spec.*
import org.goldenport.cncf.action.{ActionCall, QueryAction}
import org.goldenport.cncf.component.{Component, ComponentCreate, ComponentId}

final class MinimalComponent extends Component

object MinimalComponent extends Component.Factory {
  val name = "minimal"
  val componentId = ComponentId(name)

  protected def create_Components(params: ComponentCreate): Vector[Component] =
    Vector(MinimalComponent())

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
    val specification = OperationDefinition.Specification.Builder("hello").build()

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
  override def execute(): Consequence[OperationResponse] =
    response_string("Hello CNCF")
}
