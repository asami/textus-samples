package org.sample.aggregateexternalupdate

import io.circe.Json
import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.protocol.{Property, Request}
import org.goldenport.cncf.cli.{CncfRuntime, RunMode}
import org.goldenport.cncf.component.{ComponentCreate, ComponentFactory, ComponentOrigin}

object ExternalUpdateAggregateDemo:
  private val IdPattern = "(?m)^id:\\s*(\\S+)\\s*$".r

  def main(args: Array[String]): Unit =
    val runtime = new CncfRuntime
    val subsystem = runtime.initializeForEmbedding(modeHint = Some(RunMode.Command)).TAKE
    val factory = new OrderExternalUpdateFactory
    val initialized = factory.create(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    val _ = subsystem.add(Vector(ComponentFactory().bootstrap(initialized.head)))
    try
      val userId = _create(
        "createUserRecord",
        subsystem,
        component = "AggregateExternalUpdateSample",
        operation = "createUserRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("name", "Alice", None)
        )
      )
      val orderId = _create(
        "createOrderRecord",
        subsystem,
        component = "AggregateExternalUpdateSample",
        operation = "createOrderRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("userId", userId, None),
          Property("name", "Alpha", None),
          Property("status", "Active", None)
        )
      )
      val shipmentId = _create(
        "createShipmentOrderRecord",
        subsystem,
        component = "AggregateExternalUpdateSample",
        operation = "createShipmentOrderRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("orderId", orderId, None),
          Property("title", "Outbound-1", None),
          Property("status", "Active", None)
        )
      )

      val aggregateText = _execute_string(
        "cancelOrder",
        subsystem,
        Request.of(
          component = "AggregateExternalUpdateSample",
          service = "Order",
          operation = "cancelOrder",
          properties = List(
            Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
            Property("orderId", orderId, None)
          )
        )
      )

      val orderText = _execute_string(
        "loadOrderRecord",
        subsystem,
        Request.of(
          component = "AggregateExternalUpdateSample",
          service = "entity",
          operation = "loadOrderRecord",
          properties = List(
            Property("id", orderId, None)
          )
        )
      )
      val shipmentText = _execute_string(
        "loadShipmentOrderRecord",
        subsystem,
        Request.of(
          component = "AggregateExternalUpdateSample",
          service = "entity",
          operation = "loadShipmentOrderRecord",
          properties = List(
            Property("id", shipmentId, None)
          )
        )
      )
      val userText = _execute_string(
        "loadUserRecord",
        subsystem,
        Request.of(
          component = "AggregateExternalUpdateSample",
          service = "entity",
          operation = "loadUserRecord",
          properties = List(
            Property("id", userId, None)
          )
        )
      )
      val result = Json.obj(
        "userId" -> Json.fromString(userId),
        "orderId" -> Json.fromString(orderId),
        "shipmentOrderId" -> Json.fromString(shipmentId),
        "semantic" -> Json.obj(
          "orderStatus" -> Json.fromString("Cancelled"),
          "shipmentOrderFollowUp" -> Json.fromString("Cancelled via AggregateBehavior"),
          "userAssociation" -> Json.fromString("unchanged")
        ),
        "aggregate" -> Json.fromString(aggregateText),
        "order" -> Json.fromString(orderText),
        "shipmentOrder" -> Json.fromString(shipmentText),
        "user" -> Json.fromString(userText)
      )
      println(result.noSpaces)
    finally
      runtime.closeEmbedding()

  private def _create(
    label: String,
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    component: String,
    operation: String,
    properties: List[Property]
  ): String =
    _extract_id(
      _execute_string(
        label,
        subsystem,
        Request.of(component = component, service = "entity", operation = operation, properties = properties)
      )
    )

  private def _execute_string(
    label: String,
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    request: Request
  ): String =
    subsystem.execute(request) match
      case Consequence.Success(response) => response.print
      case Consequence.Failure(c) => throw new IllegalStateException(s"$label: ${c.show}")

  private def _extract_id(text: String): String =
    IdPattern.findFirstMatchIn(text).map(_.group(1)).getOrElse {
      throw new IllegalStateException(s"Missing id in response: $text")
    }
