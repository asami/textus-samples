package org.sample.aggregaterelationboundary

import io.circe.Json
import io.circe.parser.parse
import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.protocol.{Property, Request}
import org.goldenport.cncf.cli.{CncfRuntime, RunMode}
import org.goldenport.cncf.component.{ComponentCreate, ComponentFactory, ComponentOrigin}

object RelationBoundaryAggregateDemo:
  private val IdPattern = "(?m)^id:\\s*(\\S+)\\s*$".r

  def main(args: Array[String]): Unit =
    val runtime = new CncfRuntime
    val subsystem = runtime.initializeForEmbedding(modeHint = Some(RunMode.Command)).TAKE
    val factory = new AggregateRelationBoundarySampleComponent.Factory
    val initialized = factory.create(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    val _ = subsystem.add(Vector(ComponentFactory().bootstrap(initialized.head)))
    try
      val userId = _create(
        subsystem,
        component = "AggregateRelationBoundarySample",
        operation = "createUserRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("name", "Alice", None)
        )
      )
      val orderId = _create(
        subsystem,
        component = "AggregateRelationBoundarySample",
        operation = "createOrderRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("userId", userId, None),
          Property("name", "Alpha", None),
          Property("status", "Active", None),
          Property(
            "lines",
            Vector(
              Record.data("name" -> "Widget", "quantity" -> 2)
            ),
            None
          )
        )
      )
      val _ = _create(
        subsystem,
        component = "AggregateRelationBoundarySample",
        operation = "createShipmentOrderRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("orderId", orderId, None),
          Property("title", "Outbound-1", None)
        )
      )
      val loadText = _execute_string(
        subsystem,
        Request.of(
          component = "AggregateRelationBoundarySample",
          service = "aggregate",
          operation = "loadOrder",
          properties = List(
            Property("id", orderId, None),
            Property("textus.output.format", "json", None)
          )
        )
      )
      val searchText = _execute_string(
        subsystem,
        Request.of(
          component = "AggregateRelationBoundarySample",
          service = "aggregate",
          operation = "searchOrder",
          properties = List(
            Property("name", "Alpha", None),
            Property("textus.output.format", "json", None)
          )
        )
      )
      val result = Json.obj(
        "userId" -> Json.fromString(userId),
        "orderId" -> Json.fromString(orderId),
        "load" -> parse(loadText).getOrElse(Json.fromString(loadText)),
        "search" -> parse(searchText).getOrElse(Json.fromString(searchText))
      )
      println(result.noSpaces)
    finally
      runtime.closeEmbedding()

  private def _create(
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    component: String,
    operation: String,
    properties: List[Property]
  ): String =
    _extract_id(
      _execute_string(
        subsystem,
        Request.of(component = component, service = "entity", operation = operation, properties = properties)
      )
    )

  private def _execute_string(
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    request: Request
  ): String =
    subsystem.execute(request) match
      case Consequence.Success(response) => response.print
      case Consequence.Failure(c) => throw new IllegalStateException(c.show)

  private def _extract_id(text: String): String =
    IdPattern.findFirstMatchIn(text).map(_.group(1)).getOrElse {
      throw new IllegalStateException(s"Missing id in response: $text")
    }
