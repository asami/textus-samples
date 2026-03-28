package org.sample.crud
 
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
import org.goldenport.cncf.directive.*
import org.goldenport.cncf.action.*
import org.goldenport.cncf.component.*
import org.goldenport.cncf.statemachine.*
import org.goldenport.cncf.unitofwork.ExecUowM
import org.goldenport.cncf.unitofwork.UnitOfWork.uowmNotImplemented
import org.goldenport.cncf.entity.*
import org.goldenport.cncf.component.Component
 
class CrudComponent() extends Component with CollectionTransitionRuleProvider{
  import CrudComponent.*
 
 
 
 
  // lenslikeupdate_methods
  // validate_method
  // iri_method
  // properties_method
  // StateMachine transition rule provider
  protected def stateMachineGuardResolver: GuardBindingResolver[Any, TransitionEvent] = new GuardBindingResolver[Any, TransitionEvent] {
    def resolve(name: String): Consequence[Guard[Any, TransitionEvent]] =
      Consequence.failure(s"Missing state machine guard binding: $name")
  }
  override def stateMachineTransitionRules: Vector[CollectionTransitionRule[Any]] = Vector.empty
  override def stateMachineDefinitions: Vector[org.goldenport.cncf.statemachine.CmlStateMachineDefinition] = Vector.empty
  override def eventReceptionDefinitions: Vector[org.goldenport.cncf.event.CmlEventDefinition] = Vector.empty
  override def eventRoutingDefinitions: Vector[org.goldenport.cncf.event.CmlRoutingDefinition] = Vector.empty
  override def eventSubscriptionDefinitions: Vector[org.goldenport.cncf.event.CmlSubscriptionDefinition] = Vector.empty
  override def aggregateDefinitions: Vector[org.goldenport.cncf.entity.aggregate.AggregateDefinition] = Vector(
    org.goldenport.cncf.entity.aggregate.AggregateDefinition(
      name = "item",
      entityName = "item"
    )
  )
  override def viewDefinitions: Vector[org.goldenport.cncf.entity.view.ViewDefinition] = Vector(
    org.goldenport.cncf.entity.view.ViewDefinition(
      name = "item",
      entityName = "item",
      viewNames = Vector.empty
    )
  )
  override def operationDefinitions: Vector[org.goldenport.cncf.operation.CmlOperationDefinition] = Vector(
    org.goldenport.cncf.operation.CmlOperationDefinition(
      name = "createItem",
      kind = "COMMAND",
      inputType = "CreateItem",
      outputType = "CreateItemResult",
      inputValueKind = "COMMAND_VALUE",
      parameters = Vector.empty
    )
    ,
    org.goldenport.cncf.operation.CmlOperationDefinition(
      name = "getItem",
      kind = "QUERY",
      inputType = "GetItem",
      outputType = "ItemResult",
      inputValueKind = "QUERY_VALUE",
      parameters = Vector.empty
    )
    ,
    org.goldenport.cncf.operation.CmlOperationDefinition(
      name = "listItems",
      kind = "QUERY",
      inputType = "ListItems",
      outputType = "ListItemsResult",
      inputValueKind = "QUERY_VALUE",
      parameters = Vector.empty
    )
  )
  def componentDefinitionRecords: Vector[Record] = Vector(
    Record.data(
      "name" -> "Crud",
      "coordinates" -> Vector.empty,
      "componentlets" -> Vector.empty,
      "extension_points" -> Vector.empty,
      "extension_bindings" -> Record.empty
    )
  )
  def subsystemDefinitionRecords: Vector[Record] = Vector.empty
  
}
 
object CrudComponent {
 
  // Schema
 
 
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
  // Item-facing operations for basic CRUD access.
  object ItemService extends ServiceDefinition {
    val specification = ServiceDefinition.Specification.Builder("Item").
      operation(
        CreateItemOperation
      ).operation(
        GetItemOperation
      ).operation(
        ListItemsOperation
      ).build()
    // Creates a new item record.
 
    object CreateItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("createItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[CreateItemCommand] = {
        CreateItemCommand.create(req)
      }
    }
 
    final case class CreateItemCommand(
      request: Request,
      record: Record
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.Item.createCreateItemActionCall(core, this)
          case None => CreateItemActionCall(core, this)
        }
      }
    }
    object CreateItemCommand {
      def create(request: Request): Consequence[CreateItemCommand] = {
        Consequence.success(request.toRecord).
          map(CreateItemCommand(request, _))
      }
    }
 
    abstract class CreateItemActionCall() extends FunctionalActionCall {
      
    }
    object CreateItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: CreateItemCommand
      ) extends CreateItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: CreateItemCommand
      ): CreateItemActionCall = Instance(core, action)
    }
    // Returns a single item by identifier.
 
    object GetItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("getItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[GetItemQuery] = {
        GetItemQuery.create(req)
      }
    }
 
    final case class GetItemQuery(
      request: Request,
      record: Record
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.Item.createGetItemActionCall(core, this)
          case None => GetItemActionCall(core, this)
        }
      }
    }
    object GetItemQuery {
      def create(request: Request): Consequence[GetItemQuery] = {
        Consequence.success(request.toRecord).
          map(GetItemQuery(request, _))
      }
    }
 
    abstract class GetItemActionCall() extends FunctionalActionCall {
      
    }
    object GetItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: GetItemQuery
      ) extends GetItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: GetItemQuery
      ): GetItemActionCall = Instance(core, action)
    }
    // Lists item records with optional filters.
 
    object ListItemsOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("listItems").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[ListItemsQuery] = {
        ListItemsQuery.create(req)
      }
    }
 
    final case class ListItemsQuery(
      request: Request,
      record: Record
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.Item.createListItemsActionCall(core, this)
          case None => ListItemsActionCall(core, this)
        }
      }
    }
    object ListItemsQuery {
      def create(request: Request): Consequence[ListItemsQuery] = {
        Consequence.success(request.toRecord).
          map(ListItemsQuery(request, _))
      }
    }
 
    abstract class ListItemsActionCall() extends FunctionalActionCall {
      
    }
    object ListItemsActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: ListItemsQuery
      ) extends ListItemsActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: ListItemsQuery
      ): ListItemsActionCall = Instance(core, action)
    }
  }
  object AggregateService extends ServiceDefinition {
    val specification = ServiceDefinition.Specification.Builder("aggregate").
      operation(
        CreateItemOperation
      ).operation(
        LoadItemOperation
      ).operation(
        SaveItemOperation
      ).operation(
        UpdateItemOperation
      ).operation(
        DeleteItemOperation
      ).operation(
        SearchItemOperation
      ).build()
 
    object CreateItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("createItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[CreateItemCommand] = {
        CreateItemCommand.create(req)
      }
    }
 
    final case class CreateItemCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.aggregate.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.aggregate.createCreateItemActionCall(core, this)
          case None => CreateItemActionCall(core, this)
        }
      }
    }
    object CreateItemCommand {
      def create(request: Request): Consequence[CreateItemCommand] = {
        org.sample.crud.entity.aggregate.Item.createC(request.toRecord).
          map(CreateItemCommand(request, _))
      }
    }
 
    abstract class CreateItemActionCall() extends FunctionalActionCall {
      
    }
    object CreateItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: CreateItemCommand
      ) extends CreateItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: CreateItemCommand
      ): CreateItemActionCall = Instance(core, action)
    }
 
    object LoadItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("loadItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[LoadItemQuery] = {
        LoadItemQuery.create(req)
      }
    }
 
    final case class LoadItemQuery(
      request: Request,
      id: EntityId
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.aggregate.createLoadItemActionCall(core, this)
          case None => LoadItemActionCall(core, this)
        }
      }
    }
    object LoadItemQuery {
      def create(request: Request): Consequence[LoadItemQuery] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(LoadItemQuery(request, _))
      }
    }
 
    abstract class LoadItemActionCall() extends FunctionalActionCall {
      
    }
    object LoadItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: LoadItemQuery
      ) extends LoadItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- aggregate_load_option[org.sample.crud.entity.aggregate.Item](action.id)
          } yield {
            OperationResponse.create(r.map(_.toRecord()))
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: LoadItemQuery
      ): LoadItemActionCall = Instance(core, action)
    }
 
    object SaveItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("saveItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SaveItemCommand] = {
        SaveItemCommand.create(req)
      }
    }
 
    final case class SaveItemCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.aggregate.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.aggregate.createSaveItemActionCall(core, this)
          case None => SaveItemActionCall(core, this)
        }
      }
    }
    object SaveItemCommand {
      def create(request: Request): Consequence[SaveItemCommand] = {
        org.sample.crud.entity.aggregate.Item.createC(request.toRecord).
          map(SaveItemCommand(request, _))
      }
    }
 
    abstract class SaveItemActionCall() extends FunctionalActionCall {
      
    }
    object SaveItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SaveItemCommand
      ) extends SaveItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SaveItemCommand
      ): SaveItemActionCall = Instance(core, action)
    }
 
    object UpdateItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("updateItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[UpdateItemCommand] = {
        UpdateItemCommand.create(req)
      }
    }
 
    final case class UpdateItemCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.aggregate.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.aggregate.createUpdateItemActionCall(core, this)
          case None => UpdateItemActionCall(core, this)
        }
      }
    }
    object UpdateItemCommand {
      def create(request: Request): Consequence[UpdateItemCommand] = {
        org.sample.crud.entity.aggregate.Item.createC(request.toRecord).
          map(UpdateItemCommand(request, _))
      }
    }
 
    abstract class UpdateItemActionCall() extends FunctionalActionCall {
      
    }
    object UpdateItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: UpdateItemCommand
      ) extends UpdateItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: UpdateItemCommand
      ): UpdateItemActionCall = Instance(core, action)
    }
 
    object DeleteItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("deleteItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[DeleteItemCommand] = {
        DeleteItemCommand.create(req)
      }
    }
 
    final case class DeleteItemCommand(
      request: Request,
      id: EntityId
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.aggregate.createDeleteItemActionCall(core, this)
          case None => DeleteItemActionCall(core, this)
        }
      }
    }
    object DeleteItemCommand {
      def create(request: Request): Consequence[DeleteItemCommand] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(DeleteItemCommand(request, _))
      }
    }
 
    abstract class DeleteItemActionCall() extends FunctionalActionCall {
      
    }
    object DeleteItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: DeleteItemCommand
      ) extends DeleteItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- uowmNotImplemented[org.goldenport.cncf.unitofwork.UnitOfWorkOp, Unit]
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: DeleteItemCommand
      ): DeleteItemActionCall = Instance(core, action)
    }
 
    object SearchItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("searchItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SearchItemQuery] = {
        SearchItemQuery.create(req)
      }
    }
 
    final case class SearchItemQuery(
      request: Request,
      q: _root_.org.sample.crud.entity.query.Item
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.aggregate.createSearchItemActionCall(core, this)
          case None => SearchItemActionCall(core, this)
        }
      }
    }
    object SearchItemQuery {
      def create(request: Request): Consequence[SearchItemQuery] = {
        org.sample.crud.entity.query.Item.createC(request.toRecord).
          map(SearchItemQuery(request, _))
      }
    }
 
    abstract class SearchItemActionCall() extends FunctionalActionCall {
      
    }
    object SearchItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SearchItemQuery
      ) extends SearchItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- aggregate_search[org.sample.crud.entity.aggregate.Item](org.sample.crud.entity.query.Item.collectionId.name, Query(action.q))
          } yield {
            OperationResponse.create(r)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SearchItemQuery
      ): SearchItemActionCall = Instance(core, action)
    }
  }
  object ViewService extends ServiceDefinition {
    val specification = ServiceDefinition.Specification.Builder("view").
      operation(
        LoadItemOperation
      ).operation(
        LoadItemByViewOperation
      ).operation(
        SearchItemOperation
      ).operation(
        SearchItemRecordOperation
      ).build()
 
    object LoadItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("loadItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[LoadItemQuery] = {
        LoadItemQuery.create(req)
      }
    }
 
    final case class LoadItemQuery(
      request: Request,
      id: EntityId
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.view.createLoadItemActionCall(core, this)
          case None => LoadItemActionCall(core, this)
        }
      }
    }
    object LoadItemQuery {
      def create(request: Request): Consequence[LoadItemQuery] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(LoadItemQuery(request, _))
      }
    }
 
    abstract class LoadItemActionCall() extends FunctionalActionCall {
      
    }
    object LoadItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: LoadItemQuery
      ) extends LoadItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- view_load[org.sample.crud.entity.view.Item](org.sample.crud.entity.query.Item.collectionId.name, action.id)
          } yield {
            OperationResponse(r.toRecord())
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: LoadItemQuery
      ): LoadItemActionCall = Instance(core, action)
    }
 
    object LoadItemByViewOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("loadItemByView").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[LoadItemByViewQuery] = {
        LoadItemByViewQuery.create(req)
      }
    }
 
    final case class LoadItemByViewQuery(
      request: Request,
      id: EntityId
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.view.createLoadItemByViewActionCall(core, this)
          case None => LoadItemByViewActionCall(core, this)
        }
      }
    }
    object LoadItemByViewQuery {
      def create(request: Request): Consequence[LoadItemByViewQuery] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(LoadItemByViewQuery(request, _))
      }
    }
 
    abstract class LoadItemByViewActionCall() extends FunctionalActionCall {
      
    }
    object LoadItemByViewActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: LoadItemByViewQuery
      ) extends LoadItemByViewActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- view_load[org.sample.crud.entity.view.Item](org.sample.crud.entity.query.Item.collectionId.name, action_required_property_string("view").TAKE, action.id)
          } yield {
            OperationResponse(r.toRecord())
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: LoadItemByViewQuery
      ): LoadItemByViewActionCall = Instance(core, action)
    }
 
    object SearchItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("searchItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SearchItemQuery] = {
        SearchItemQuery.create(req)
      }
    }
 
    final case class SearchItemQuery(
      request: Request,
      q: _root_.org.sample.crud.entity.query.Item,
      view: Option[String]
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.view.createSearchItemActionCall(core, this)
          case None => SearchItemActionCall(core, this)
        }
      }
    }
    object SearchItemQuery {
      def create(request: Request): Consequence[SearchItemQuery] = {
        for {
          q <- org.sample.crud.entity.query.Item.createC(request.toRecord)
          view <- Consequence.success(request.toRecord.getString("view"))
        } yield SearchItemQuery(request, q, view)
      }
    }
 
    abstract class SearchItemActionCall() extends FunctionalActionCall {
      
    }
    object SearchItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SearchItemQuery
      ) extends SearchItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- action.view.fold(view_search[org.sample.crud.entity.view.Item](org.sample.crud.entity.query.Item.collectionId.name, Query(action.q)))(viewname => view_search[org.sample.crud.entity.view.Item](org.sample.crud.entity.query.Item.collectionId.name, viewname, Query(action.q)))
          } yield {
            OperationResponse.create(r)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SearchItemQuery
      ): SearchItemActionCall = Instance(core, action)
    }
 
    object SearchItemRecordOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("searchItemRecord").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SearchItemRecordQuery] = {
        SearchItemRecordQuery.create(req)
      }
    }
 
    final case class SearchItemRecordQuery(
      request: Request,
      q: Query[Record]
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.view.createSearchItemRecordActionCall(core, this)
          case None => SearchItemRecordActionCall(core, this)
        }
      }
    }
    object SearchItemRecordQuery {
      def create(request: Request): Consequence[SearchItemRecordQuery] = {
        Consequence.success(Query(request.toRecord)).
          map(SearchItemRecordQuery(request, _))
      }
    }
 
    abstract class SearchItemRecordActionCall() extends FunctionalActionCall {
      
    }
    object SearchItemRecordActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SearchItemRecordQuery
      ) extends SearchItemRecordActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- action_property_string("view").fold(view_search[org.sample.crud.entity.view.Item](org.sample.crud.entity.query.Item.collectionId.name, action.q))(viewname => view_search[org.sample.crud.entity.view.Item](org.sample.crud.entity.query.Item.collectionId.name, viewname, action.q))
          } yield {
            OperationResponse.create(r)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SearchItemRecordQuery
      ): SearchItemRecordActionCall = Instance(core, action)
    }
  }
  object EntityService extends ServiceDefinition {
    val specification = ServiceDefinition.Specification.Builder("entity").
      operation(
        CreateItemOperation
      ).operation(
        CreateItemRecordOperation
      ).operation(
        LoadItemOperation
      ).operation(
        LoadItemRecordOperation
      ).operation(
        SaveItemOperation
      ).operation(
        SaveItemRecordOperation
      ).operation(
        UpdateItemOperation
      ).operation(
        UpdateItemRecordOperation
      ).operation(
        DeleteItemOperation
      ).operation(
        DeleteItemHardOperation
      ).operation(
        SearchItemOperation
      ).operation(
        SearchItemRecordOperation
      ).build()
 
    object CreateItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("createItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[CreateItemCommand] = {
        CreateItemCommand.create(req)
      }
    }
 
    final case class CreateItemCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.create.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createCreateItemActionCall(core, this)
          case None => CreateItemActionCall(core, this)
        }
      }
    }
    object CreateItemCommand {
      def create(request: Request): Consequence[CreateItemCommand] = {
        org.sample.crud.entity.create.Item.createC(request.toRecord).
          map(CreateItemCommand(request, _))
      }
    }
 
    abstract class CreateItemActionCall() extends FunctionalActionCall {
      
    }
    object CreateItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: CreateItemCommand
      ) extends CreateItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- entity_create(action.entity)
          } yield {
            OperationResponse(r.toRecord)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: CreateItemCommand
      ): CreateItemActionCall = Instance(core, action)
    }
 
    object CreateItemRecordOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("createItemRecord").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[CreateItemRecordCommand] = {
        CreateItemRecordCommand.create(req)
      }
    }
 
    final case class CreateItemRecordCommand(
      request: Request,
      record: Record
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createCreateItemRecordActionCall(core, this)
          case None => CreateItemRecordActionCall(core, this)
        }
      }
    }
    object CreateItemRecordCommand {
      def create(request: Request): Consequence[CreateItemRecordCommand] = {
        Consequence.success(request.toRecord).
          map(CreateItemRecordCommand(request, _))
      }
    }
 
    abstract class CreateItemRecordActionCall() extends FunctionalActionCall {
      
    }
    object CreateItemRecordActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: CreateItemRecordCommand
      ) extends CreateItemRecordActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            entity <- exec_pure(org.sample.crud.entity.create.Item.create(action.record))
            r <- entity_create(entity)
          } yield {
            OperationResponse(r.toRecord)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: CreateItemRecordCommand
      ): CreateItemRecordActionCall = Instance(core, action)
    }
 
    object LoadItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("loadItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[LoadItemQuery] = {
        LoadItemQuery.create(req)
      }
    }
 
    final case class LoadItemQuery(
      request: Request,
      id: EntityId
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createLoadItemActionCall(core, this)
          case None => LoadItemActionCall(core, this)
        }
      }
    }
    object LoadItemQuery {
      def create(request: Request): Consequence[LoadItemQuery] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(LoadItemQuery(request, _))
      }
    }
 
    abstract class LoadItemActionCall() extends FunctionalActionCall {
      
    }
    object LoadItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: LoadItemQuery
      ) extends LoadItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- entity_load[org.sample.crud.entity.Item](action.id)
          } yield {
            OperationResponse(r.toRecord())
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: LoadItemQuery
      ): LoadItemActionCall = Instance(core, action)
    }
 
    object LoadItemRecordOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("loadItemRecord").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[LoadItemRecordQuery] = {
        LoadItemRecordQuery.create(req)
      }
    }
 
    final case class LoadItemRecordQuery(
      request: Request,
      id: EntityId
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createLoadItemRecordActionCall(core, this)
          case None => LoadItemRecordActionCall(core, this)
        }
      }
    }
    object LoadItemRecordQuery {
      def create(request: Request): Consequence[LoadItemRecordQuery] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(LoadItemRecordQuery(request, _))
      }
    }
 
    abstract class LoadItemRecordActionCall() extends FunctionalActionCall {
      
    }
    object LoadItemRecordActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: LoadItemRecordQuery
      ) extends LoadItemRecordActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- entity_load[org.sample.crud.entity.Item](action.id)
          } yield {
            OperationResponse(r.toRecord())
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: LoadItemRecordQuery
      ): LoadItemRecordActionCall = Instance(core, action)
    }
 
    object SaveItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("saveItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SaveItemCommand] = {
        SaveItemCommand.create(req)
      }
    }
 
    final case class SaveItemCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.create.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createSaveItemActionCall(core, this)
          case None => SaveItemActionCall(core, this)
        }
      }
    }
    object SaveItemCommand {
      def create(request: Request): Consequence[SaveItemCommand] = {
        org.sample.crud.entity.create.Item.createC(request.toRecord).
          map(SaveItemCommand(request, _))
      }
    }
 
    abstract class SaveItemActionCall() extends FunctionalActionCall {
      
    }
    object SaveItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SaveItemCommand
      ) extends SaveItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            entity <- exec_pure(org.sample.crud.entity.Item.create(action.entity.toRecord()))
            _ <- entity_save(entity)
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SaveItemCommand
      ): SaveItemActionCall = Instance(core, action)
    }
 
    object SaveItemRecordOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("saveItemRecord").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SaveItemRecordCommand] = {
        SaveItemRecordCommand.create(req)
      }
    }
 
    final case class SaveItemRecordCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.create.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createSaveItemRecordActionCall(core, this)
          case None => SaveItemRecordActionCall(core, this)
        }
      }
    }
    object SaveItemRecordCommand {
      def create(request: Request): Consequence[SaveItemRecordCommand] = {
        org.sample.crud.entity.create.Item.createC(request.toRecord).
          map(SaveItemRecordCommand(request, _))
      }
    }
 
    abstract class SaveItemRecordActionCall() extends FunctionalActionCall {
      
    }
    object SaveItemRecordActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SaveItemRecordCommand
      ) extends SaveItemRecordActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            entity <- exec_pure(org.sample.crud.entity.Item.create(action.entity.toRecord()))
            _ <- entity_save(entity)
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SaveItemRecordCommand
      ): SaveItemRecordActionCall = Instance(core, action)
    }
 
    object UpdateItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("updateItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[UpdateItemCommand] = {
        UpdateItemCommand.create(req)
      }
    }
 
    final case class UpdateItemCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.update.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createUpdateItemActionCall(core, this)
          case None => UpdateItemActionCall(core, this)
        }
      }
    }
    object UpdateItemCommand {
      def create(request: Request): Consequence[UpdateItemCommand] = {
        org.sample.crud.entity.update.Item.createC(request.toRecord).
          map(UpdateItemCommand(request, _))
      }
    }
 
    abstract class UpdateItemActionCall() extends FunctionalActionCall {
      
    }
    object UpdateItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: UpdateItemCommand
      ) extends UpdateItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            id <- exec_pure(Consequence.successOrRecordNotFound[EntityId]("id", action.request.toRecord).TAKE)
            _ <- entity_update(id, action.entity)
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: UpdateItemCommand
      ): UpdateItemActionCall = Instance(core, action)
    }
 
    object UpdateItemRecordOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("updateItemRecord").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[UpdateItemRecordCommand] = {
        UpdateItemRecordCommand.create(req)
      }
    }
 
    final case class UpdateItemRecordCommand(
      request: Request,
      entity: _root_.org.sample.crud.entity.update.Item
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createUpdateItemRecordActionCall(core, this)
          case None => UpdateItemRecordActionCall(core, this)
        }
      }
    }
    object UpdateItemRecordCommand {
      def create(request: Request): Consequence[UpdateItemRecordCommand] = {
        org.sample.crud.entity.update.Item.createC(request.toRecord).
          map(UpdateItemRecordCommand(request, _))
      }
    }
 
    abstract class UpdateItemRecordActionCall() extends FunctionalActionCall {
      
    }
    object UpdateItemRecordActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: UpdateItemRecordCommand
      ) extends UpdateItemRecordActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            id <- exec_pure(Consequence.successOrRecordNotFound[EntityId]("id", action.request.toRecord).TAKE)
            _ <- entity_update(id, action.entity)
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: UpdateItemRecordCommand
      ): UpdateItemRecordActionCall = Instance(core, action)
    }
 
    object DeleteItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("deleteItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[DeleteItemCommand] = {
        DeleteItemCommand.create(req)
      }
    }
 
    final case class DeleteItemCommand(
      request: Request,
      id: EntityId
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createDeleteItemActionCall(core, this)
          case None => DeleteItemActionCall(core, this)
        }
      }
    }
    object DeleteItemCommand {
      def create(request: Request): Consequence[DeleteItemCommand] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(DeleteItemCommand(request, _))
      }
    }
 
    abstract class DeleteItemActionCall() extends FunctionalActionCall {
      
    }
    object DeleteItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: DeleteItemCommand
      ) extends DeleteItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- entity_delete(action.id)
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: DeleteItemCommand
      ): DeleteItemActionCall = Instance(core, action)
    }
 
    object DeleteItemHardOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("deleteItemHard").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[DeleteItemHardCommand] = {
        DeleteItemHardCommand.create(req)
      }
    }
 
    final case class DeleteItemHardCommand(
      request: Request,
      id: EntityId
    ) extends CommandAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createDeleteItemHardActionCall(core, this)
          case None => DeleteItemHardActionCall(core, this)
        }
      }
    }
    object DeleteItemHardCommand {
      def create(request: Request): Consequence[DeleteItemHardCommand] = {
        org.simplemodeling.model.datatype.EntityId.createC(request.toRecord).
          map(DeleteItemHardCommand(request, _))
      }
    }
 
    abstract class DeleteItemHardActionCall() extends FunctionalActionCall {
      
    }
    object DeleteItemHardActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: DeleteItemHardCommand
      ) extends DeleteItemHardActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            _ <- entity_delete_hard(action.id)
          } yield {
            OperationResponse.void
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: DeleteItemHardCommand
      ): DeleteItemHardActionCall = Instance(core, action)
    }
 
    object SearchItemOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("searchItem").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SearchItemQuery] = {
        SearchItemQuery.create(req)
      }
    }
 
    final case class SearchItemQuery(
      request: Request,
      q: _root_.org.sample.crud.entity.query.Item
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createSearchItemActionCall(core, this)
          case None => SearchItemActionCall(core, this)
        }
      }
    }
    object SearchItemQuery {
      def create(request: Request): Consequence[SearchItemQuery] = {
        org.sample.crud.entity.query.Item.createC(request.toRecord).
          map(SearchItemQuery(request, _))
      }
    }
 
    abstract class SearchItemActionCall() extends FunctionalActionCall {
      
    }
    object SearchItemActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SearchItemQuery
      ) extends SearchItemActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- entity_search[org.sample.crud.entity.Item](org.sample.crud.entity.query.Item.collectionId, Query(action.q))
          } yield {
            OperationResponse.create(r)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SearchItemQuery
      ): SearchItemActionCall = Instance(core, action)
    }
 
    object SearchItemRecordOperation extends OperationDefinition {
      val specification = OperationDefinition.Specification.Builder("searchItemRecord").
        build()
 
      override def createOperationRequest(
        req: Request
      ): Consequence[SearchItemRecordQuery] = {
        SearchItemRecordQuery.create(req)
      }
    }
 
    final case class SearchItemRecordQuery(
      request: Request,
      q: Query[Record]
    ) extends QueryAction() {
      override def createCall(core: ActionCall.Core): ActionCall = {
        core.getFactory[CrudComponent.Factory] match {
          case Some(s) => s.entity.createSearchItemRecordActionCall(core, this)
          case None => SearchItemRecordActionCall(core, this)
        }
      }
    }
    object SearchItemRecordQuery {
      def create(request: Request): Consequence[SearchItemRecordQuery] = {
        Consequence.success(Query(request.toRecord)).
          map(SearchItemRecordQuery(request, _))
      }
    }
 
    abstract class SearchItemRecordActionCall() extends FunctionalActionCall {
      
    }
    object SearchItemRecordActionCall {
      case class Instance(
        core: ActionCall.Core,
        override val action: SearchItemRecordQuery
      ) extends SearchItemRecordActionCall {
        protected def build_Program: ExecUowM[OperationResponse] = {
          for {
            r <- entity_search[org.sample.crud.entity.Item](org.sample.crud.entity.query.Item.collectionId, action.q)
          } yield {
            OperationResponse.create(r)
          }
        }
      }
 
      def apply(
        core: ActionCall.Core,
        action: SearchItemRecordQuery
      ): SearchItemRecordActionCall = Instance(core, action)
    }
  }
  // CRUD sample component for item management.
  val name = "Crud"
  val componentId = ComponentId(name) // TODO
 
  class Factory extends Component.Factory {
    protected def create_Components(params: ComponentCreate): Vector[Component] =
      Vector(CrudComponent())
 
    protected def create_Core(
      params: ComponentCreate,
      comp: Component
    ): Component.Core = spec_create(
      name,
      componentId,
      Vector(
      ItemService,
      AggregateService,
      ViewService,
      EntityService)
    )
 
    val Item = ItemServiceFactory()
    val aggregate = AggregateServiceFactory()
    val view = ViewServiceFactory()
    val entity = EntityServiceFactory()
  }
  class ItemServiceFactory() {
    import ItemService.*
    def createCreateItemActionCall(
      core: ActionCall.Core,
      action: CreateItemCommand
    ): CreateItemActionCall = {
      CreateItemActionCall(core, action)
    }
    def createGetItemActionCall(
      core: ActionCall.Core,
      action: GetItemQuery
    ): GetItemActionCall = {
      GetItemActionCall(core, action)
    }
    def createListItemsActionCall(
      core: ActionCall.Core,
      action: ListItemsQuery
    ): ListItemsActionCall = {
      ListItemsActionCall(core, action)
    }
  }
  class AggregateServiceFactory() {
    import AggregateService.*
    def createCreateItemActionCall(
      core: ActionCall.Core,
      action: CreateItemCommand
    ): CreateItemActionCall = {
      CreateItemActionCall(core, action)
    }
    def createLoadItemActionCall(
      core: ActionCall.Core,
      action: LoadItemQuery
    ): LoadItemActionCall = {
      LoadItemActionCall(core, action)
    }
    def createSaveItemActionCall(
      core: ActionCall.Core,
      action: SaveItemCommand
    ): SaveItemActionCall = {
      SaveItemActionCall(core, action)
    }
    def createUpdateItemActionCall(
      core: ActionCall.Core,
      action: UpdateItemCommand
    ): UpdateItemActionCall = {
      UpdateItemActionCall(core, action)
    }
    def createDeleteItemActionCall(
      core: ActionCall.Core,
      action: DeleteItemCommand
    ): DeleteItemActionCall = {
      DeleteItemActionCall(core, action)
    }
    def createSearchItemActionCall(
      core: ActionCall.Core,
      action: SearchItemQuery
    ): SearchItemActionCall = {
      SearchItemActionCall(core, action)
    }
  }
  class ViewServiceFactory() {
    import ViewService.*
    def createLoadItemActionCall(
      core: ActionCall.Core,
      action: LoadItemQuery
    ): LoadItemActionCall = {
      LoadItemActionCall(core, action)
    }
    def createLoadItemByViewActionCall(
      core: ActionCall.Core,
      action: LoadItemByViewQuery
    ): LoadItemByViewActionCall = {
      LoadItemByViewActionCall(core, action)
    }
    def createSearchItemActionCall(
      core: ActionCall.Core,
      action: SearchItemQuery
    ): SearchItemActionCall = {
      SearchItemActionCall(core, action)
    }
    def createSearchItemRecordActionCall(
      core: ActionCall.Core,
      action: SearchItemRecordQuery
    ): SearchItemRecordActionCall = {
      SearchItemRecordActionCall(core, action)
    }
  }
  class EntityServiceFactory() {
    import EntityService.*
    def createCreateItemActionCall(
      core: ActionCall.Core,
      action: CreateItemCommand
    ): CreateItemActionCall = {
      CreateItemActionCall(core, action)
    }
    def createCreateItemRecordActionCall(
      core: ActionCall.Core,
      action: CreateItemRecordCommand
    ): CreateItemRecordActionCall = {
      CreateItemRecordActionCall(core, action)
    }
    def createLoadItemActionCall(
      core: ActionCall.Core,
      action: LoadItemQuery
    ): LoadItemActionCall = {
      LoadItemActionCall(core, action)
    }
    def createLoadItemRecordActionCall(
      core: ActionCall.Core,
      action: LoadItemRecordQuery
    ): LoadItemRecordActionCall = {
      LoadItemRecordActionCall(core, action)
    }
    def createSaveItemActionCall(
      core: ActionCall.Core,
      action: SaveItemCommand
    ): SaveItemActionCall = {
      SaveItemActionCall(core, action)
    }
    def createSaveItemRecordActionCall(
      core: ActionCall.Core,
      action: SaveItemRecordCommand
    ): SaveItemRecordActionCall = {
      SaveItemRecordActionCall(core, action)
    }
    def createUpdateItemActionCall(
      core: ActionCall.Core,
      action: UpdateItemCommand
    ): UpdateItemActionCall = {
      UpdateItemActionCall(core, action)
    }
    def createUpdateItemRecordActionCall(
      core: ActionCall.Core,
      action: UpdateItemRecordCommand
    ): UpdateItemRecordActionCall = {
      UpdateItemRecordActionCall(core, action)
    }
    def createDeleteItemActionCall(
      core: ActionCall.Core,
      action: DeleteItemCommand
    ): DeleteItemActionCall = {
      DeleteItemActionCall(core, action)
    }
    def createDeleteItemHardActionCall(
      core: ActionCall.Core,
      action: DeleteItemHardCommand
    ): DeleteItemHardActionCall = {
      DeleteItemHardActionCall(core, action)
    }
    def createSearchItemActionCall(
      core: ActionCall.Core,
      action: SearchItemQuery
    ): SearchItemActionCall = {
      SearchItemActionCall(core, action)
    }
    def createSearchItemRecordActionCall(
      core: ActionCall.Core,
      action: SearchItemRecordQuery
    ): SearchItemRecordActionCall = {
      SearchItemRecordActionCall(core, action)
    }
  }
}

