package org.sample.crud.entity.update
 
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
import org.simplemodeling.model.value.AuditAttributesUpdate
import org.simplemodeling.model.value.ContextualAttributesUpdate
import org.simplemodeling.model.value.DescriptiveAttributesUpdate
import org.simplemodeling.model.value.LifecycleAttributesUpdate
import org.simplemodeling.model.value.MediaAttributesUpdate
import org.simplemodeling.model.value.NameAttributesUpdate
import org.simplemodeling.model.value.PublicationAttributesUpdate
import org.simplemodeling.model.value.ResourceAttributesUpdate
import org.simplemodeling.model.value.SecurityAttributesUpdate
 
case class Item(override val id: Update[EntityId], nameAttributes: NameAttributesUpdate, descriptiveAttributes: DescriptiveAttributesUpdate, lifecycleAttributes: LifecycleAttributesUpdate, publicationAttributes: PublicationAttributesUpdate, securityAttributes: SecurityAttributesUpdate, resourceAttributes: ResourceAttributesUpdate, auditAttributes: AuditAttributesUpdate, mediaAttributes: MediaAttributesUpdate, contextualAttribute: ContextualAttributesUpdate) extends org.simplemodeling.model.SimpleEntityUpdate with EntityPersistableUpdate{
  import Item.*
  override def name_Attributes: NameAttributesUpdate = nameAttributes
  override def descriptive_Attributes: DescriptiveAttributesUpdate = descriptiveAttributes
  override def lifecycle_Attributes: LifecycleAttributesUpdate = lifecycleAttributes
  override def publication_Attributes: PublicationAttributesUpdate = publicationAttributes
  override def security_Attributes: SecurityAttributesUpdate = securityAttributes
  override def resource_Attributes: ResourceAttributesUpdate = resourceAttributes
  override def audit_Attributes: AuditAttributesUpdate = auditAttributes
  override def media_Attributes: MediaAttributesUpdate = mediaAttributes
  override def contextual_Attribute: ContextualAttributesUpdate = contextualAttribute
 
 
 
 
 
  def schema(): Schema = {
    Item.schema
  }
 
 
  def withId(id: Update[EntityId]): Item = {
    copy(id = id)
  }
 
 
  def withName_Attributes(name_Attributes: NameAttributesUpdate): Item = {
    copy(nameAttributes = name_Attributes)
  }
 
 
  def withDescriptive_Attributes(descriptive_Attributes: DescriptiveAttributesUpdate): Item = {
    copy(descriptiveAttributes = descriptive_Attributes)
  }
 
 
  def withLifecycle_Attributes(lifecycle_Attributes: LifecycleAttributesUpdate): Item = {
    copy(lifecycleAttributes = lifecycle_Attributes)
  }
 
 
  def withPublication_Attributes(publication_Attributes: PublicationAttributesUpdate): Item = {
    copy(publicationAttributes = publication_Attributes)
  }
 
 
  def withSecurity_Attributes(security_Attributes: SecurityAttributesUpdate): Item = {
    copy(securityAttributes = security_Attributes)
  }
 
 
  def withResource_Attributes(resource_Attributes: ResourceAttributesUpdate): Item = {
    copy(resourceAttributes = resource_Attributes)
  }
 
 
  def withAudit_Attributes(audit_Attributes: AuditAttributesUpdate): Item = {
    copy(auditAttributes = audit_Attributes)
  }
 
 
  def withMedia_Attributes(media_Attributes: MediaAttributesUpdate): Item = {
    copy(mediaAttributes = media_Attributes)
  }
 
 
  def withContextual_Attribute(contextual_Attribute: ContextualAttributesUpdate): Item = {
    copy(contextualAttribute = contextual_Attribute)
  }
  // lenslikeupdate_methods
  // validate_method
  // iri_method
  // properties_method
 
  def toRecord(): Record = {
    Record.dataAuto(
      "id" -> _to_external_value(id), 
      "name_attributes" -> _to_external_value(name_Attributes), 
      "descriptive_attributes" -> _to_external_value(descriptive_Attributes), 
      "lifecycle_attributes" -> _to_external_value(lifecycle_Attributes), 
      "publication_attributes" -> _to_external_value(publication_Attributes), 
      "security_attributes" -> _to_external_value(security_Attributes), 
      "resource_attributes" -> _to_external_value(resource_Attributes), 
      "audit_attributes" -> _to_external_value(audit_Attributes), 
      "media_attributes" -> _to_external_value(media_Attributes), 
      "contextual_attribute" -> _to_external_value(contextual_Attribute)
    )
  }
 
  def toDataStore(): Record = {
    Record.dataAuto(
      "id" -> _to_data_store_value(id), 
      "name_attributes" -> _to_data_store_value(name_Attributes), 
      "descriptive_attributes" -> _to_data_store_value(descriptive_Attributes), 
      "lifecycle_attributes" -> _to_data_store_value(lifecycle_Attributes), 
      "publication_attributes" -> _to_data_store_value(publication_Attributes), 
      "security_attributes" -> _to_data_store_value(security_Attributes), 
      "resource_attributes" -> _to_data_store_value(resource_Attributes), 
      "audit_attributes" -> _to_data_store_value(audit_Attributes), 
      "media_attributes" -> _to_data_store_value(media_Attributes), 
      "contextual_attribute" -> _to_data_store_value(contextual_Attribute)
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
  final val PROP_NAME_ATTRIBUTES = "name_Attributes"
  final val INPUT_KEYS_NAME_ATTRIBUTES: List[String] = List("name_Attributes", "name_attributes").distinct
  final val PROP_DESCRIPTIVE_ATTRIBUTES = "descriptive_Attributes"
  final val INPUT_KEYS_DESCRIPTIVE_ATTRIBUTES: List[String] = List("descriptive_Attributes", "descriptive_attributes").distinct
  final val PROP_LIFECYCLE_ATTRIBUTES = "lifecycle_Attributes"
  final val INPUT_KEYS_LIFECYCLE_ATTRIBUTES: List[String] = List("lifecycle_Attributes", "lifecycle_attributes").distinct
  final val PROP_PUBLICATION_ATTRIBUTES = "publication_Attributes"
  final val INPUT_KEYS_PUBLICATION_ATTRIBUTES: List[String] = List("publication_Attributes", "publication_attributes").distinct
  final val PROP_SECURITY_ATTRIBUTES = "security_Attributes"
  final val INPUT_KEYS_SECURITY_ATTRIBUTES: List[String] = List("security_Attributes", "security_attributes").distinct
  final val PROP_RESOURCE_ATTRIBUTES = "resource_Attributes"
  final val INPUT_KEYS_RESOURCE_ATTRIBUTES: List[String] = List("resource_Attributes", "resource_attributes").distinct
  final val PROP_AUDIT_ATTRIBUTES = "audit_Attributes"
  final val INPUT_KEYS_AUDIT_ATTRIBUTES: List[String] = List("audit_Attributes", "audit_attributes").distinct
  final val PROP_MEDIA_ATTRIBUTES = "media_Attributes"
  final val INPUT_KEYS_MEDIA_ATTRIBUTES: List[String] = List("media_Attributes", "media_attributes").distinct
  final val PROP_CONTEXTUAL_ATTRIBUTE = "contextual_Attribute"
  final val INPUT_KEYS_CONTEXTUAL_ATTRIBUTE: List[String] = List("contextual_Attribute", "contextual_attribute").distinct
 
  val schema: org.goldenport.schema.Schema = org.sample.crud.entity.Item.schema
 
  given CanEqual[Item,Item] = CanEqual.derived
  // Domain semantic equality = equality of identity
  given Eq[Item] = Eq.fromUniversalEquals
  val collectionId: EntityCollectionId = EntityCollectionId("major", "minor", "item")
  given EntityPersistentUpdate[Item] with
    def toRecord(e: Item): Record = e.toDataStore()
    def fromRecord(r: Record): Consequence[Item] = createC(r)
    def collection(e: Item): EntityCollectionId = collectionId
 
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
 
  case class Builder(id: Option[Update[EntityId]] = None, name_Attributes: Option[NameAttributesUpdate] = None, descriptive_Attributes: Option[DescriptiveAttributesUpdate] = None, lifecycle_Attributes: Option[LifecycleAttributesUpdate] = None, publication_Attributes: Option[PublicationAttributesUpdate] = None, security_Attributes: Option[SecurityAttributesUpdate] = None, resource_Attributes: Option[ResourceAttributesUpdate] = None, audit_Attributes: Option[AuditAttributesUpdate] = None, media_Attributes: Option[MediaAttributesUpdate] = None, contextual_Attribute: Option[ContextualAttributesUpdate] = None, _failures: Vector[Consequence.Failure[_]] = Vector.empty) {
 
    def withId(id: EntityId): Item.Builder = {
      copy(id = Some(Update.set(id)))
    }
 
    def withId(id: Update[EntityId]): Item.Builder = {
      copy(id = Some(id))
    }
 
 
    def withName_Attributes(name_Attributes: NameAttributesUpdate): Item.Builder = {
      copy(name_Attributes = Some(name_Attributes))
    }
 
    def withName_Attributes(name_Attributes: Option[NameAttributesUpdate]): Item.Builder = {
      copy(name_Attributes = name_Attributes)
    }
 
 
    def withDescriptive_Attributes(descriptive_Attributes: DescriptiveAttributesUpdate): Item.Builder = {
      copy(descriptive_Attributes = Some(descriptive_Attributes))
    }
 
    def withDescriptive_Attributes(descriptive_Attributes: Option[DescriptiveAttributesUpdate]): Item.Builder = {
      copy(descriptive_Attributes = descriptive_Attributes)
    }
 
 
    def withLifecycle_Attributes(lifecycle_Attributes: LifecycleAttributesUpdate): Item.Builder = {
      copy(lifecycle_Attributes = Some(lifecycle_Attributes))
    }
 
    def withLifecycle_Attributes(lifecycle_Attributes: Option[LifecycleAttributesUpdate]): Item.Builder = {
      copy(lifecycle_Attributes = lifecycle_Attributes)
    }
 
 
    def withPublication_Attributes(publication_Attributes: PublicationAttributesUpdate): Item.Builder = {
      copy(publication_Attributes = Some(publication_Attributes))
    }
 
    def withPublication_Attributes(publication_Attributes: Option[PublicationAttributesUpdate]): Item.Builder = {
      copy(publication_Attributes = publication_Attributes)
    }
 
 
    def withSecurity_Attributes(security_Attributes: SecurityAttributesUpdate): Item.Builder = {
      copy(security_Attributes = Some(security_Attributes))
    }
 
    def withSecurity_Attributes(security_Attributes: Option[SecurityAttributesUpdate]): Item.Builder = {
      copy(security_Attributes = security_Attributes)
    }
 
 
    def withResource_Attributes(resource_Attributes: ResourceAttributesUpdate): Item.Builder = {
      copy(resource_Attributes = Some(resource_Attributes))
    }
 
    def withResource_Attributes(resource_Attributes: Option[ResourceAttributesUpdate]): Item.Builder = {
      copy(resource_Attributes = resource_Attributes)
    }
 
 
    def withAudit_Attributes(audit_Attributes: AuditAttributesUpdate): Item.Builder = {
      copy(audit_Attributes = Some(audit_Attributes))
    }
 
    def withAudit_Attributes(audit_Attributes: Option[AuditAttributesUpdate]): Item.Builder = {
      copy(audit_Attributes = audit_Attributes)
    }
 
 
    def withMedia_Attributes(media_Attributes: MediaAttributesUpdate): Item.Builder = {
      copy(media_Attributes = Some(media_Attributes))
    }
 
    def withMedia_Attributes(media_Attributes: Option[MediaAttributesUpdate]): Item.Builder = {
      copy(media_Attributes = media_Attributes)
    }
 
 
    def withContextual_Attribute(contextual_Attribute: ContextualAttributesUpdate): Item.Builder = {
      copy(contextual_Attribute = Some(contextual_Attribute))
    }
 
    def withContextual_Attribute(contextual_Attribute: Option[ContextualAttributesUpdate]): Item.Builder = {
      copy(contextual_Attribute = contextual_Attribute)
    }
 
 
    def buildC(): Consequence[Item] = {
      (
        Consequence.success(id.getOrElse(Update.noop[EntityId])),
        Consequence.success(name_Attributes.getOrElse(org.simplemodeling.model.value.NameAttributesUpdate())),
        Consequence.success(descriptive_Attributes.getOrElse(org.simplemodeling.model.value.DescriptiveAttributesUpdate())),
        Consequence.success(lifecycle_Attributes.getOrElse(org.simplemodeling.model.value.LifecycleAttributesUpdate())),
        Consequence.success(publication_Attributes.getOrElse(org.simplemodeling.model.value.PublicationAttributesUpdate())),
        Consequence.success(security_Attributes.getOrElse(org.simplemodeling.model.value.SecurityAttributesUpdate())),
        Consequence.success(resource_Attributes.getOrElse(org.simplemodeling.model.value.ResourceAttributesUpdate())),
        Consequence.success(audit_Attributes.getOrElse(org.simplemodeling.model.value.AuditAttributesUpdate())),
        Consequence.success(media_Attributes.getOrElse(org.simplemodeling.model.value.MediaAttributesUpdate())),
        Consequence.success(contextual_Attribute.getOrElse(org.simplemodeling.model.value.ContextualAttributesUpdate()))
      ).mapN(Item.apply)
    }
 
 
    def build(): Item = {
      buildC().take
    }
 
 
    def buildC(record: Record): Consequence[Item] = {
      (
        _record_get_as_c[EntityId](record, INPUT_KEYS_ID).flatMap {
          case Some(s) => Consequence.success(Update.set(s))
          case None => Consequence.success(id.getOrElse(Update.noop[EntityId]))
        }, 
        Consequence.success(name_Attributes.getOrElse(org.simplemodeling.model.value.NameAttributesUpdate())), 
        Consequence.success(descriptive_Attributes.getOrElse(org.simplemodeling.model.value.DescriptiveAttributesUpdate())), 
        Consequence.success(lifecycle_Attributes.getOrElse(org.simplemodeling.model.value.LifecycleAttributesUpdate())), 
        Consequence.success(publication_Attributes.getOrElse(org.simplemodeling.model.value.PublicationAttributesUpdate())), 
        Consequence.success(security_Attributes.getOrElse(org.simplemodeling.model.value.SecurityAttributesUpdate())), 
        Consequence.success(resource_Attributes.getOrElse(org.simplemodeling.model.value.ResourceAttributesUpdate())), 
        Consequence.success(audit_Attributes.getOrElse(org.simplemodeling.model.value.AuditAttributesUpdate())), 
        Consequence.success(media_Attributes.getOrElse(org.simplemodeling.model.value.MediaAttributesUpdate())), 
        Consequence.success(contextual_Attribute.getOrElse(org.simplemodeling.model.value.ContextualAttributesUpdate()))
      ).mapN(Item.apply)
    }
 
 
 
    def build(record: Record): Item = {
      buildC(record).take
    }
  }
 
  object Builder {
  }
 
  def createC(name_Attributes: NameAttributesUpdate, descriptive_Attributes: DescriptiveAttributesUpdate, lifecycle_Attributes: LifecycleAttributesUpdate, publication_Attributes: PublicationAttributesUpdate, security_Attributes: SecurityAttributesUpdate, resource_Attributes: ResourceAttributesUpdate, audit_Attributes: AuditAttributesUpdate, media_Attributes: MediaAttributesUpdate, contextual_Attribute: ContextualAttributesUpdate): Consequence[Item] = {
    val builder = Builder()
    val builder2 = builder.withName_Attributes(name_Attributes).withDescriptive_Attributes(descriptive_Attributes).withLifecycle_Attributes(lifecycle_Attributes).withPublication_Attributes(publication_Attributes).withSecurity_Attributes(security_Attributes).withResource_Attributes(resource_Attributes).withAudit_Attributes(audit_Attributes).withMedia_Attributes(media_Attributes).withContextual_Attribute(contextual_Attribute)
    builder2.buildC()
  }
 
 
  def create(name_Attributes: NameAttributesUpdate, descriptive_Attributes: DescriptiveAttributesUpdate, lifecycle_Attributes: LifecycleAttributesUpdate, publication_Attributes: PublicationAttributesUpdate, security_Attributes: SecurityAttributesUpdate, resource_Attributes: ResourceAttributesUpdate, audit_Attributes: AuditAttributesUpdate, media_Attributes: MediaAttributesUpdate, contextual_Attribute: ContextualAttributesUpdate): Item = {
    createC(name_Attributes, descriptive_Attributes, lifecycle_Attributes, publication_Attributes, security_Attributes, resource_Attributes, audit_Attributes, media_Attributes, contextual_Attribute).take
  }
 
 
 
  def createC(record: Record): Consequence[Item] = {
    val builder = Builder()
    builder.buildC(record)
  }
 
 
 
  def create(record: Record): Item = {
    createC(record).take
  }
 
}

