package org.sample.aggregaterelationboundary

import io.circe.Json
import io.circe.syntax.*

object RelationBoundaryDemo:
  def main(args: Array[String]): Unit =
    val payload = Json.obj(
      "sample" -> "06.b-aggregate-relation-boundary-model".asJson,
      "relationAxes" -> Json.arr(
        Json.obj("name" -> "OrderLine".asJson, "kind" -> "composition".asJson, "boundary" -> "internal".asJson),
        Json.obj("name" -> "ShipmentOrder".asJson, "kind" -> "aggregation".asJson, "boundary" -> "external".asJson),
        Json.obj("name" -> "User".asJson, "kind" -> "association".asJson, "boundary" -> "external".asJson)
      ),
      "confirmed" -> Json.arr(
        "kind and boundary are separate axes".asJson,
        "ShipmentOrder is stronger than association but outside aggregate transaction boundary".asJson,
        "User remains plain external association".asJson
      )
    )
    println(payload.spaces2)
