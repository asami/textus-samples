package org.sample.crud.entity.aggregate
 
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
import org.simplemodeling.model.datatype.EntityId
 
case class Item(override val id: EntityId, override val name: Name, override val title: String) extends org.simplemodeling.model.SimpleEntity with EntityPersistable derives Codec.AsObject {
  import Item.*
 
 
 
 
  def schema(): Schema = {
    Item.schema
  }
 
 
  def withId(id: EntityId): Item = {
    copy(id = id)
  }
 
 
  def withName(name: Name): Item = {
    copy(name = name)
  }
 
 
  def withTitle(title: String): Item = {
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
  given Eq[Item] = Eq.by(_.id)
  val collectionId: EntityCollectionId = EntityCollectionId("major", "minor", "item")
  given EntityPersistent[Item] with
    def id(e: Item): EntityId = e.id
    def toRecord(e: Item): Record = e.toDataStore()
    def fromRecord(r: Record): Consequence[Item] = createC(r)
 
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
 
  case class Builder(id: Option[EntityId] = None, name: Option[Name] = None, title: Option[String] = None, _failures: Vector[Consequence.Failure[_]] = Vector.empty) {
 
    def withId(id: EntityId): Item.Builder = {
      copy(id = Some(id))
    }
 
    def withId(id: Option[EntityId]): Item.Builder = {
      copy(id = id)
    }
 
    def withId(id: String): Item.Builder = {
      EntityId.parse(id) match {
        case Consequence.Success(s) => copy(id = Some(s))
        case m: Consequence.Failure[_] => copy(_failures = _failures :+ m)
      }
    }
 
    def withIdOption(id: Option[String]): Item.Builder = {
      id match {
        case Some(s) => withId(s)
        case None => this
      }
    }
 
 
    def withName(name: Name): Item.Builder = {
      copy(name = Some(name))
    }
 
    def withName(name: Option[Name]): Item.Builder = {
      copy(name = name)
    }
 
    def withName(name: String): Item.Builder = {
      Name.parse(name) match {
        case Consequence.Success(s) => copy(name = Some(s))
        case m: Consequence.Failure[_] => copy(_failures = _failures :+ m)
      }
    }
 
    def withNameOption(name: Option[String]): Item.Builder = {
      name match {
        case Some(s) => withName(s)
        case None => this
      }
    }
 
 
    def withTitle(title: String): Item.Builder = {
      copy(title = Some(title))
    }
 
    def withTitle(title: Option[String]): Item.Builder = {
      copy(title = title)
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
          case Some(s) => Consequence.success(s)
          case None => Consequence.successOrPropertyNotFound(PROP_ID, id)
        }, 
        _record_get_as_c[Name](record, INPUT_KEYS_NAME).flatMap {
          case Some(s) => Consequence.success(s)
          case None => Consequence.successOrPropertyNotFound(PROP_NAME, name)
        }, 
        _record_get_as_c[String](record, INPUT_KEYS_TITLE).flatMap {
          case Some(s) => Consequence.success(s)
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
 
  def createC(id: EntityId, name: Name, title: String): Consequence[Item] = {
    val builder = Builder()
    val builder2 = builder.withId(id).withName(name).withTitle(title)
    builder2.buildC()
  }
 
 
  def create(id: EntityId, name: Name, title: String): Item = {
    createC(id, name, title).take
  }
 
 
 
  def createC(record: Record): Consequence[Item] = {
    val builder = Builder()
    builder.buildC(record)
  }
 
 
 
  def create(record: Record): Item = {
    createC(record).take
  }
 
}

