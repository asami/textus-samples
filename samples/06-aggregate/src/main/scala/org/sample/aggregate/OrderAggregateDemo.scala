package org.sample.aggregate

import io.circe.Json
import org.goldenport.Consequence
import org.goldenport.protocol.{Property, Request}
import org.goldenport.cncf.cli.CncfRuntime
import org.goldenport.cncf.cli.RunMode
import org.goldenport.cncf.component.{ComponentCreate, ComponentFactory, ComponentOrigin}

object OrderAggregateDemo {
  private val IdPattern = "(?m)^id:\\s*(\\S+)\\s*$".r

  def main(args: Array[String]): Unit = {
    val runtime = new CncfRuntime
    val subsystem = runtime.initializeForEmbedding(modeHint = Some(RunMode.Command)).TAKE
    val customFactory = new OrderAggregateFactory
    val initialized = customFactory.create(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    val component = ComponentFactory().bootstrap(initialized.head)
    val _ = subsystem.add(Vector(component))
    try {
      val orderId = _create_order(subsystem)
      val addLineText = _add_line(subsystem, orderId, "Widget", 2)
      val invalidAddLine = _execute_failure_string(
        subsystem,
        Request.of(
          component = "AggregateSample",
          service = "Order",
          operation = "addLine",
          properties = List(
            Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
            Property("orderId", orderId, None),
            Property("lineName", "Broken", None),
            Property("quantity", "0", None)
          )
        )
      )
      val loadText = _execute_string(
        subsystem,
        Request.of(
          component = "AggregateSample",
          service = "Order",
          operation = "loadOrderAggregate",
          properties = List(Property("id", orderId, None))
        )
      )
      val searchText = _execute_string(
        subsystem,
        Request.of(
          component = "AggregateSample",
          service = "Order",
          operation = "searchOrderAggregate",
          properties = List(Property("name", "Alpha", None))
        )
      )
      val result = Json.obj(
        "orderId" -> Json.fromString(orderId),
        "addLine" -> Json.fromString(addLineText),
        "invalidAddLine" -> Json.fromString(invalidAddLine),
        "load" -> Json.fromString(loadText),
        "search" -> Json.fromString(searchText)
      )
      println(result.noSpaces)
    } finally {
      runtime.closeEmbedding()
    }
  }

  private def _create_order(subsystem: org.goldenport.cncf.subsystem.Subsystem): String = {
    val text = _execute_string(
      subsystem,
      Request.of(
        component = "AggregateSample",
        service = "entity",
        operation = "createOrderRecord",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("name", "Alpha", None),
          Property("status", "Active", None)
        )
      )
    )
    _extract_id(text)
  }

  private def _add_line(
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    orderId: String,
    lineName: String,
    quantity: Int
  ): String =
    _execute_string(
      subsystem,
      Request.of(
        component = "AggregateSample",
        service = "Order",
        operation = "addLine",
        properties = List(
          Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
          Property("orderId", orderId, None),
          Property("lineName", lineName, None),
          Property("quantity", quantity.toString, None)
        )
      )
    )

  private def _execute_string(
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    request: Request
  ): String =
    subsystem.execute(request) match {
      case Consequence.Success(response) =>
        response.print
      case Consequence.Failure(c) =>
        throw new IllegalStateException(c.show)
    }

  private def _execute_failure_string(
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    request: Request
  ): String =
    subsystem.execute(request) match {
      case Consequence.Success(response) =>
        throw new IllegalStateException(s"Expected failure but got success: ${response.print}")
      case Consequence.Failure(c) =>
        c.show
    }

  private def _extract_id(text: String): String =
    IdPattern.findFirstMatchIn(text).map(_.group(1)).getOrElse {
      throw new IllegalStateException(s"Missing id in response: $text")
    }
}
