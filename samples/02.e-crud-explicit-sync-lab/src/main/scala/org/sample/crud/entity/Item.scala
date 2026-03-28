package org.sample.crud.entity
 
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
import org.simplemodeling.model.value.AuditAttributes
import org.simplemodeling.model.value.ContextualAttributes
import org.simplemodeling.model.value.DescriptiveAttributes
import org.simplemodeling.model.value.LifecycleAttributes
import org.simplemodeling.model.value.MediaAttributes
import org.simplemodeling.model.value.NameAttributes
import org.simplemodeling.model.value.PublicationAttributes
import org.simplemodeling.model.value.ResourceAttributes
import org.simplemodeling.model.value.SecurityAttributes
 
case class Item(override val id: EntityId, nameAttributes: NameAttributes, descriptiveAttributes: DescriptiveAttributes, lifecycleAttributes: LifecycleAttributes, publicationAttributes: PublicationAttributes, securityAttributes: SecurityAttributes, resourceAttributes: ResourceAttributes, auditAttributes: AuditAttributes, mediaAttributes: MediaAttributes, contextualAttribute: ContextualAttributes) extends org.simplemodeling.model.SimpleEntity with EntityPersistable{
  import Item.*
  override protected def name_Attributes: NameAttributes = nameAttributes
  override protected def descriptive_Attributes: DescriptiveAttributes = descriptiveAttributes
  override protected def lifecycle_Attributes: LifecycleAttributes = lifecycleAttributes
  override protected def publication_Attributes: PublicationAttributes = publicationAttributes
  override protected def security_Attributes: SecurityAttributes = securityAttributes
  override protected def resource_Attributes: ResourceAttributes = resourceAttributes
  override protected def audit_Attributes: AuditAttributes = auditAttributes
  override protected def media_Attributes: MediaAttributes = mediaAttributes
  override protected def contextual_Attribute: ContextualAttributes = contextualAttribute
 
 
 
 
 
  def schema(): Schema = {
    Item.schema
  }
 
 
  def withId(id: EntityId): Item = {
    copy(id = id)
  }
 
 
  def withName_Attributes(name_Attributes: NameAttributes): Item = {
    copy(nameAttributes = name_Attributes)
  }
 
 
  def withDescriptive_Attributes(descriptive_Attributes: DescriptiveAttributes): Item = {
    copy(descriptiveAttributes = descriptive_Attributes)
  }
 
 
  def withLifecycle_Attributes(lifecycle_Attributes: LifecycleAttributes): Item = {
    copy(lifecycleAttributes = lifecycle_Attributes)
  }
 
 
  def withPublication_Attributes(publication_Attributes: PublicationAttributes): Item = {
    copy(publicationAttributes = publication_Attributes)
  }
 
 
  def withSecurity_Attributes(security_Attributes: SecurityAttributes): Item = {
    copy(securityAttributes = security_Attributes)
  }
 
 
  def withResource_Attributes(resource_Attributes: ResourceAttributes): Item = {
    copy(resourceAttributes = resource_Attributes)
  }
 
 
  def withAudit_Attributes(audit_Attributes: AuditAttributes): Item = {
    copy(auditAttributes = audit_Attributes)
  }
 
 
  def withMedia_Attributes(media_Attributes: MediaAttributes): Item = {
    copy(mediaAttributes = media_Attributes)
  }
 
 
  def withContextual_Attribute(contextual_Attribute: ContextualAttributes): Item = {
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
 
  val schema: org.goldenport.schema.Schema = org.goldenport.schema.Schema(
    columns = Vector(
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("id"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("name_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("descriptive_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("lifecycle_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("publication_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("security_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("resource_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("audit_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("media_Attributes"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      ),
      org.goldenport.schema.Column(
        baseContent = org.simplemodeling.model.value.BaseContent.simple("contextual_Attribute"),
        domain = org.goldenport.schema.ValueDomain(
          datatype = org.goldenport.schema.XString,
          multiplicity = org.goldenport.schema.Multiplicity.One
        )
      )
    )
  )
 
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
 
  case class Builder(id: Option[EntityId] = None, name_Attributes: Option[NameAttributes] = None, descriptive_Attributes: Option[DescriptiveAttributes] = None, lifecycle_Attributes: Option[LifecycleAttributes] = None, publication_Attributes: Option[PublicationAttributes] = None, security_Attributes: Option[SecurityAttributes] = None, resource_Attributes: Option[ResourceAttributes] = None, audit_Attributes: Option[AuditAttributes] = None, media_Attributes: Option[MediaAttributes] = None, contextual_Attribute: Option[ContextualAttributes] = None, _failures: Vector[Consequence.Failure[_]] = Vector.empty) {
 
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
 
 
    def withName_Attributes(name_Attributes: NameAttributes): Item.Builder = {
      copy(name_Attributes = Some(name_Attributes))
    }
 
    def withName_Attributes(name_Attributes: Option[NameAttributes]): Item.Builder = {
      copy(name_Attributes = name_Attributes)
    }
 
 
    def withDescriptive_Attributes(descriptive_Attributes: DescriptiveAttributes): Item.Builder = {
      copy(descriptive_Attributes = Some(descriptive_Attributes))
    }
 
    def withDescriptive_Attributes(descriptive_Attributes: Option[DescriptiveAttributes]): Item.Builder = {
      copy(descriptive_Attributes = descriptive_Attributes)
    }
 
 
    def withLifecycle_Attributes(lifecycle_Attributes: LifecycleAttributes): Item.Builder = {
      copy(lifecycle_Attributes = Some(lifecycle_Attributes))
    }
 
    def withLifecycle_Attributes(lifecycle_Attributes: Option[LifecycleAttributes]): Item.Builder = {
      copy(lifecycle_Attributes = lifecycle_Attributes)
    }
 
 
    def withPublication_Attributes(publication_Attributes: PublicationAttributes): Item.Builder = {
      copy(publication_Attributes = Some(publication_Attributes))
    }
 
    def withPublication_Attributes(publication_Attributes: Option[PublicationAttributes]): Item.Builder = {
      copy(publication_Attributes = publication_Attributes)
    }
 
 
    def withSecurity_Attributes(security_Attributes: SecurityAttributes): Item.Builder = {
      copy(security_Attributes = Some(security_Attributes))
    }
 
    def withSecurity_Attributes(security_Attributes: Option[SecurityAttributes]): Item.Builder = {
      copy(security_Attributes = security_Attributes)
    }
 
 
    def withResource_Attributes(resource_Attributes: ResourceAttributes): Item.Builder = {
      copy(resource_Attributes = Some(resource_Attributes))
    }
 
    def withResource_Attributes(resource_Attributes: Option[ResourceAttributes]): Item.Builder = {
      copy(resource_Attributes = resource_Attributes)
    }
 
 
    def withAudit_Attributes(audit_Attributes: AuditAttributes): Item.Builder = {
      copy(audit_Attributes = Some(audit_Attributes))
    }
 
    def withAudit_Attributes(audit_Attributes: Option[AuditAttributes]): Item.Builder = {
      copy(audit_Attributes = audit_Attributes)
    }
 
 
    def withMedia_Attributes(media_Attributes: MediaAttributes): Item.Builder = {
      copy(media_Attributes = Some(media_Attributes))
    }
 
    def withMedia_Attributes(media_Attributes: Option[MediaAttributes]): Item.Builder = {
      copy(media_Attributes = media_Attributes)
    }
 
 
    def withContextual_Attribute(contextual_Attribute: ContextualAttributes): Item.Builder = {
      copy(contextual_Attribute = Some(contextual_Attribute))
    }
 
    def withContextual_Attribute(contextual_Attribute: Option[ContextualAttributes]): Item.Builder = {
      copy(contextual_Attribute = contextual_Attribute)
    }
 
 
    def buildC(): Consequence[Item] = {
      (
        Consequence.successOrPropertyNotFound(PROP_ID, id),
        Consequence.success(name_Attributes.getOrElse(org.simplemodeling.model.value.NameAttributes.simple(Name("unknown")))),
        Consequence.success(descriptive_Attributes.getOrElse(org.simplemodeling.model.value.DescriptiveAttributes.empty)),
        Consequence.success(lifecycle_Attributes.getOrElse(org.simplemodeling.model.value.LifecycleAttributes(java.time.ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, java.time.ZoneOffset.UTC), None, Identifier("system"), None, org.simplemodeling.model.statemachine.PostStatus.default, org.simplemodeling.model.statemachine.Aliveness.default))),
        Consequence.success(publication_Attributes.getOrElse(org.simplemodeling.model.value.PublicationAttributes(None, None, None, None, None))),
        Consequence.success(security_Attributes.getOrElse(org.simplemodeling.model.value.SecurityAttributes(org.goldenport.datatype.ObjectId(Identifier("system")), org.goldenport.datatype.ObjectId(Identifier("system")), org.simplemodeling.model.value.SecurityAttributes.Rights(org.simplemodeling.model.value.SecurityAttributes.Rights.Permissions(read = true, write = true, execute = true), org.simplemodeling.model.value.SecurityAttributes.Rights.Permissions(read = true, write = false, execute = false), org.simplemodeling.model.value.SecurityAttributes.Rights.Permissions(read = true, write = false, execute = false)), org.goldenport.datatype.ObjectId(Identifier("system"))))),
        Consequence.success(resource_Attributes.getOrElse(org.simplemodeling.model.value.ResourceAttributes())),
        Consequence.success(audit_Attributes.getOrElse(org.simplemodeling.model.value.AuditAttributes())),
        Consequence.success(media_Attributes.getOrElse(org.simplemodeling.model.value.MediaAttributes(None, Vector.empty, Vector.empty, Vector.empty, Vector.empty))),
        Consequence.success(contextual_Attribute.getOrElse(org.simplemodeling.model.value.ContextualAttributes()))
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
        (
          _record_get_as_c[Name](record, List("name")),
          _record_get_as_c[String](record, List("title"))
        ).mapN { (namev, titlev) =>
          val base = name_Attributes.getOrElse(namev.map(NameAttributes.simple).getOrElse(NameAttributes.simple(Name("unknown"))))
          titlev.fold(base)(t => base.copy(title = Some(I18nTitle(t))))
        }
        , 
        Consequence.success(descriptive_Attributes.getOrElse(org.simplemodeling.model.value.DescriptiveAttributes.empty)), 
        Consequence.success(lifecycle_Attributes.getOrElse(org.simplemodeling.model.value.LifecycleAttributes(java.time.ZonedDateTime.of(1970, 1, 1, 0, 0, 0, 0, java.time.ZoneOffset.UTC), None, Identifier("system"), None, org.simplemodeling.model.statemachine.PostStatus.default, org.simplemodeling.model.statemachine.Aliveness.default))), 
        Consequence.success(publication_Attributes.getOrElse(org.simplemodeling.model.value.PublicationAttributes(None, None, None, None, None))), 
        Consequence.success(security_Attributes.getOrElse(org.simplemodeling.model.value.SecurityAttributes(org.goldenport.datatype.ObjectId(Identifier("system")), org.goldenport.datatype.ObjectId(Identifier("system")), org.simplemodeling.model.value.SecurityAttributes.Rights(org.simplemodeling.model.value.SecurityAttributes.Rights.Permissions(read = true, write = true, execute = true), org.simplemodeling.model.value.SecurityAttributes.Rights.Permissions(read = true, write = false, execute = false), org.simplemodeling.model.value.SecurityAttributes.Rights.Permissions(read = true, write = false, execute = false)), org.goldenport.datatype.ObjectId(Identifier("system"))))), 
        Consequence.success(resource_Attributes.getOrElse(org.simplemodeling.model.value.ResourceAttributes())), 
        Consequence.success(audit_Attributes.getOrElse(org.simplemodeling.model.value.AuditAttributes())), 
        Consequence.success(media_Attributes.getOrElse(org.simplemodeling.model.value.MediaAttributes(None, Vector.empty, Vector.empty, Vector.empty, Vector.empty))), 
        Consequence.success(contextual_Attribute.getOrElse(org.simplemodeling.model.value.ContextualAttributes()))
      ).mapN(Item.apply)
    }
 
 
 
    def build(record: Record): Item = {
      buildC(record).take
    }
  }
 
  object Builder {
  }
 
  def createC(id: EntityId, name_Attributes: NameAttributes, descriptive_Attributes: DescriptiveAttributes, lifecycle_Attributes: LifecycleAttributes, publication_Attributes: PublicationAttributes, security_Attributes: SecurityAttributes, resource_Attributes: ResourceAttributes, audit_Attributes: AuditAttributes, media_Attributes: MediaAttributes, contextual_Attribute: ContextualAttributes): Consequence[Item] = {
    val builder = Builder()
    val builder2 = builder.withId(id).withName_Attributes(name_Attributes).withDescriptive_Attributes(descriptive_Attributes).withLifecycle_Attributes(lifecycle_Attributes).withPublication_Attributes(publication_Attributes).withSecurity_Attributes(security_Attributes).withResource_Attributes(resource_Attributes).withAudit_Attributes(audit_Attributes).withMedia_Attributes(media_Attributes).withContextual_Attribute(contextual_Attribute)
    builder2.buildC()
  }
 
 
  def create(id: EntityId, name_Attributes: NameAttributes, descriptive_Attributes: DescriptiveAttributes, lifecycle_Attributes: LifecycleAttributes, publication_Attributes: PublicationAttributes, security_Attributes: SecurityAttributes, resource_Attributes: ResourceAttributes, audit_Attributes: AuditAttributes, media_Attributes: MediaAttributes, contextual_Attribute: ContextualAttributes): Item = {
    createC(id, name_Attributes, descriptive_Attributes, lifecycle_Attributes, publication_Attributes, security_Attributes, resource_Attributes, audit_Attributes, media_Attributes, contextual_Attribute).take
  }
 
 
 
  def createC(record: Record): Consequence[Item] = {
    val builder = Builder()
    builder.buildC(record)
  }
 
 
 
  def create(record: Record): Item = {
    createC(record).take
  }
 
}

