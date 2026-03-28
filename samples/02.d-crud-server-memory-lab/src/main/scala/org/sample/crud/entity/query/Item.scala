package org.sample.crud.entity.query
 
import scala.language.strictEquality
import cats.*
import cats.implicits.*
import cats.syntax.all.*
import io.circe.Codec
import io.circe.generic.semiauto.*
import org.goldenport.Consequence
import org.goldenport.ConsequenceT
import org.goldenport.datatype.*
import org.goldenport.schema.Schema
import org.goldenport.record.Record
import org.goldenport.protocol.*
import org.goldenport.protocol.spec.*
import org.goldenport.protocol.operation.*
import org.simplemodeling.model.datatype.*
import org.simplemodeling.model.value.*
import org.simplemodeling.model.directive.*
import org.goldenport.cncf.entity.*
import org.simplemodeling.model.directive.Condition
 
case class Item(override val id: Condition[EntityId], name: Condition[Name], title: Condition[String]) extends org.simplemodeling.model.SimpleEntityQuery with EntityPersistableQuery derives Codec.AsObject {
  import Item.*
 
 
 
 
  def schema(): Schema = {
    Item.schema
  }
 
 
  def withId(id: Condition[EntityId]): Item = {
    copy(id = id)
  }
 
 
  def withName(name: Condition[Name]): Item = {
    copy(name = name)
  }
 
 
  def withTitle(title: Condition[String]): Item = {
    copy(title = title)
  }
  // lenslikeupdate_methods
  // validate_method
  // iri_method
  // properties_method
 
  def toRecord(): Record = {
    Record.dataAuto(
      "id" -> _to_external_value(id), 
      "name" -> _to_external_value(name), 
      "title" -> _to_external_value(title)
    )
  }
 
  def toDataStore(): Record = {
    Record.dataAuto(
      "id" -> _to_data_store_value(id), 
      "name" -> _to_data_store_value(name), 
      "title" -> _to_data_store_value(title)
    )
  }
  private def _to_external_value(v: Any): Any = v match {
    case m if java.util.Objects.isNull(m) => null
    case m: String => m
    case m: java.lang.Number => m
    case m: java.lang.Boolean => m
    case m: java.lang.Character => m.toString
    case m: Record => m
    case m: Option[?] => m.map(_to_external_value)
    case m: Seq[?] => m.map(_to_external_value)
    case m: Set[?] => m.toVector.map(_to_external_value)
    case m: Array[?] => m.toVector.map(_to_external_value)
    case m: Map[?, ?] => m.iterator.map { case (k, value) => k.toString -> _to_external_value(value) }.toMap
    case m: org.goldenport.text.Presentable => m.print
    case other => other.toString
  }
  
  private def _to_data_store_value(v: Any): Any = v match {
    case m: org.simplemodeling.model.directive.Update[?] => m
    case other => _to_external_value(other)
  }
  
}
 
object Item {
  final val PROP_ID = "id"
  final val INPUT_KEYS_ID: List[String] = List("id").distinct
  final val PROP_NAME = "name"
  final val INPUT_KEYS_NAME: List[String] = List("name").distinct
  final val PROP_TITLE = "title"
  final val INPUT_KEYS_TITLE: List[String] = List("title").distinct
 
  val schema: org.goldenport.schema.Schema = org.sample.crud.entity.Item.schema
 
  given CanEqual[Item,Item] = CanEqual.derived
  // Domain semantic equality = equality of identity
  given Eq[Item] = Eq.fromUniversalEquals
  val collectionId: EntityCollectionId = EntityCollectionId("major", "minor", "item")
  given EntityPersistentQuery[Item] = EntityPersistentQuery.derived(createC, collectionId)
 
  private def _record_get_as_c[A](
    record: Record,
    keys: List[String]
  )(using vr: org.goldenport.convert.ValueReader[A]): Consequence[Option[A]] = {
    keys.foldLeft(Consequence.success(Option.empty[A])) { (z, key) =>
      z.flatMap {
        case s @ Some(_) => Consequence.success(s)
        case None => record.getAsC[A](key)
      }
    }
  }
 
  case class Builder(id: Option[Condition[EntityId]] = None, name: Option[Condition[Name]] = None, title: Option[Condition[String]] = None, _failures: Vector[Consequence.Failure[_]] = Vector.empty) {
 
    def withId(id: EntityId): Item.Builder = {
      copy(id = Some(Condition.is(id)))
    }
 
    def withId(id: Condition[EntityId]): Item.Builder = {
      copy(id = Some(id))
    }
 
 
    def withName(name: Name): Item.Builder = {
      copy(name = Some(Condition.is(name)))
    }
 
    def withName(name: Condition[Name]): Item.Builder = {
      copy(name = Some(name))
    }
 
 
    def withTitle(title: String): Item.Builder = {
      copy(title = Some(Condition.is(title)))
    }
 
    def withTitle(title: Condition[String]): Item.Builder = {
      copy(title = Some(title))
    }
 
 
    def buildC(): Consequence[Item] = {
      (
        Consequence.successOrPropertyNotFound(PROP_ID, id),
        Consequence.successOrPropertyNotFound(PROP_NAME, name),
        Consequence.successOrPropertyNotFound(PROP_TITLE, title)
      ).mapN(Item.apply)
    }
 
 
    def build(): Item = {
      buildC().take
    }
 
 
    def buildC(record: Record): Consequence[Item] = {
      (
        _record_get_as_c[EntityId](record, INPUT_KEYS_ID).flatMap {
          case Some(s) => Consequence.success(Condition.is(s))
          case None => Consequence.successOrPropertyNotFound(PROP_ID, id)
        }, 
        _record_get_as_c[Name](record, INPUT_KEYS_NAME).flatMap {
          case Some(s) => Consequence.success(Condition.is(s))
          case None => Consequence.successOrPropertyNotFound(PROP_NAME, name)
        }, 
        _record_get_as_c[String](record, INPUT_KEYS_TITLE).flatMap {
          case Some(s) => Consequence.success(Condition.is(s))
          case None => Consequence.successOrPropertyNotFound(PROP_TITLE, title)
        }
      ).mapN(Item.apply)
    }
 
 
 
    def build(record: Record): Item = {
      buildC(record).take
    }
  }
 
  object Builder {
  }
 
  def createC(): Consequence[Item] = {
    val builder = Builder()
    builder.buildC()
  }
 
 
  def create(): Item = {
    createC().take
  }
 
 
 
  def createC(record: Record): Consequence[Item] = {
    val builder = Builder()
    builder.buildC(record)
  }
 
 
 
  def create(record: Record): Item = {
    createC(record).take
  }
 
}

