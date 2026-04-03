package org.sample.aggregateexternalupdate

import io.circe.Json
import io.circe.syntax.*

object ExternalUpdateDemo:
  def main(args: Array[String]): Unit =
    val payload = Json.obj(
      "sample" -> "07.c-aggregate-external-update-semantics".asJson,
      "updateSemantics" -> Json.arr(
        "Order cancellation follows up to ShipmentOrder".asJson,
        "User stays plain external association".asJson
      )
    )
    println(payload.spaces2)
