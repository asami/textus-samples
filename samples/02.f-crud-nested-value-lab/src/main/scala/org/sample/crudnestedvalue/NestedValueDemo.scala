package org.sample.crudnestedvalue

import io.circe.Json
import io.circe.syntax._
import org.goldenport.record.Record
import org.goldenport.datatype.Name
import domain.value.Address
import domain.value.CountryCode
import org.sample.crudnestedvalue.entity.Person
import org.simplemodeling.model.datatype.EntityId

object NestedValueDemo:
  def main(args: Array[String]): Unit =
    val person = Person.Builder()
      .withId(EntityId.parse("major-minor-entity-person-20260331000000-aaa111").TAKE)
      .withName(Name("alice"))
      .withAddress(
        Address(
        street = "1-2-3 Marunouchi",
        city = "Tokyo",
        country = CountryCode("JP")
      )
      )
      .build()
    val record = person.toRecord()
    val restored = Person.createC(record).TAKE
    val payload = Map[String, Json](
      "pattern" -> "crud-nested-value".asJson,
      "record" -> _record_json(record),
      "restored" -> _record_json(restored.toRecord()),
      "country" -> restored.address.country.value.asJson
    )
    println(payload.asJson.noSpaces)

  private def _record_json(p: Record): Json =
    Json.obj(p.asMap.iterator.map { case (k, v) => k -> _json(v) }.toSeq: _*)

  private def _json(v: Any): Json = v match
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
    case m: Map[?, ?] => Json.obj(m.iterator.map { case (k, value) => k.toString -> _json(value) }.toSeq: _*)
    case m: org.goldenport.text.Presentable => m.print.asJson
    case other => other.toString.asJson
