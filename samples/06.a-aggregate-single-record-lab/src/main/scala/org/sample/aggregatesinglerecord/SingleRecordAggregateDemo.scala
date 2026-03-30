package org.sample.aggregatesinglerecord

import io.circe.syntax._
import io.circe.Json
import org.goldenport.record.Record
import org.goldenport.datatype.Name
import domain.value.OrderLine
import org.sample.aggregatesinglerecord.entity.Order
import org.simplemodeling.model.datatype.EntityId

object SingleRecordAggregateDemo {
  def main(args: Array[String]): Unit = {
    val line1 = OrderLine.create(Name("Widget"), 2)
    val line2 = OrderLine.create(Name("Cable"), 1)
    val order = Order.Builder()
      .withId(EntityId.parse("major-minor-entity-order-20260330000000-aaa111").TAKE)
      .withName(Name("Alpha"))
      .withStatus("Active")
      .withLines(Vector(line1, line2))
      .build()
    val record = order.toRecord()
    val restored = Order.createC(record).TAKE
    val payload = Map[String, io.circe.Json](
      "pattern" -> "single-record-aggregate".asJson,
      "entity" -> "Order".asJson,
      "value-object" -> "OrderLine".asJson,
      "record" -> _record_json(record),
      "restored" -> _record_json(restored.toRecord()),
      "line-count" -> restored.lines.size.asJson
    )
    println(payload.asJson.noSpaces)
  }

  private def _record_json(p: Record): Json =
    Json.obj(p.asMap.iterator.map { case (k, v) => k -> _json(v) }.toSeq: _*)

  private def _json(v: Any): Json = v match {
    case null => Json.Null
    case m: String => m.asJson
    case m: Int => m.asJson
    case m: Long => m.asJson
    case m: Double => m.asJson
    case m: Float => m.toDouble.asJson
    case m: BigDecimal => m.asJson
    case m: BigInt => m.asJson
    case m: Boolean => m.asJson
    case m: Record => _record_json(m)
    case m: Seq[?] => Json.arr(m.iterator.map(_json).toSeq: _*)
    case m: Array[?] => Json.arr(m.iterator.map(_json).toSeq: _*)
    case m: Map[?, ?] =>
      Json.obj(m.iterator.map { case (k, value) => k.toString -> _json(value) }.toSeq: _*)
    case m: org.goldenport.text.Presentable => m.print.asJson
    case other => other.toString.asJson
  }
}
