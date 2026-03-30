package org.sample.aggregate

import cats.implicits.*
import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.{ActionCall, AggregateBehavior}
import org.goldenport.cncf.directive.Query
import org.goldenport.cncf.entity.{EntityQuery, EntityStore}
import org.goldenport.cncf.entity.aggregate.AggregateDefinition
import org.goldenport.cncf.entity.aggregate.{AggregateCollection, ContextualAggregateBuilder, ContextualAggregateQuery}
import org.goldenport.cncf.context.ExecutionContext
import org.goldenport.cncf.component.Component
import org.goldenport.cncf.unitofwork.ExecUowM
import org.simplemodeling.model.datatype.EntityId

final class OrderAggregateFactory extends AggregateSampleComponent.Factory {
  override val Order: AggregateSampleComponent.OrderServiceFactory = new OrderAggregateServiceFactory
  override val entity: AggregateSampleComponent.EntityServiceFactory = new AggregateEntityServiceFactory

  private lazy val _order_aggregate_definition: AggregateDefinition =
    new AggregateSampleComponent().aggregateDefinitions.find(_.name == "order").getOrElse(
      throw new IllegalStateException("Missing aggregate definition: order")
    )

  private lazy val _order_line_member_name: String =
    _order_aggregate_definition.members.headOption.map(_.entityName).getOrElse("order_line")

  private lazy val _order_line_join_field_name: String =
    _order_aggregate_definition.members.headOption.flatMap(_.joinFieldName).getOrElse("orderId")

  private lazy val _add_line_command_name: String =
    _order_aggregate_definition.commands.find(_.name == "addLine").map(_.name).getOrElse("addLine")

  private lazy val _quantity_positive_invariant_name: String =
    _order_aggregate_definition.invariants.find(_.name == "quantityPositive").map(_.name).getOrElse("quantityPositive")

  override def aggregate_collection_bindings(
    comp: Component
  ): Vector[Component.AggregateCollectionBinding] =
    Vector(
      Component.AggregateCollectionBinding(
        aggregate_name = "order",
        collection = new AggregateCollection[org.sample.aggregate.entity.aggregate.Order](
          builder = OrderAggregateCollectionBuilder,
          queryfn = OrderAggregateCollectionQuery
        )
      )
    )

  override def create_aggregate_behavior(
    action: org.goldenport.cncf.action.Action,
    core: ActionCall.Core
  ): Option[AggregateBehavior[?]] =
    action.request.operation match {
      case name if name == _add_line_command_name => Some(AddLineAggregateBehavior(core))
      case _ => super.create_aggregate_behavior(action, core)
    }

  class OrderAggregateServiceFactory extends AggregateSampleComponent.OrderServiceFactory {
    override def createAddLineActionCall(
      core: ActionCall.Core,
      action: AggregateSampleComponent.OrderService.AddLineCommand
    ): AggregateSampleComponent.OrderService.AddLineActionCall =
      AddLineActionCall(core, action)
  }

  object AddLineActionCall {
    def apply(
      core: ActionCall.Core,
      action: AggregateSampleComponent.OrderService.AddLineCommand
    ): AggregateSampleComponent.OrderService.AddLineActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: AggregateSampleComponent.OrderService.AddLineCommand
    ) extends AggregateSampleComponent.OrderService.AddLineActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          orderId <- exec_pure(Consequence.successOrRecordNotFound[EntityId]("orderId", action.record).TAKE)
          behavior <- exec_from(resolve_aggregate_behavior().map(_.asInstanceOf[AggregateBehavior[Record]]))
          _ <- exec_from(invoke_aggregate_behavior(behavior, action.record))
          aggregate <- _load_order_aggregate_record(orderId)
        } yield OperationResponse.RecordResponse(aggregate)

      }

      private def _load_order_aggregate_record(id: EntityId): ExecUowM[Record] =
        exec_from(OrderAggregateCollectionBuilder.build_with_context(id)(using execution_context).map(_.toRecord()))
    }
  }

  class AggregateEntityServiceFactory extends AggregateSampleComponent.EntityServiceFactory {
    override def createCreateOrderRecordActionCall(
      core: ActionCall.Core,
      action: AggregateSampleComponent.EntityService.CreateOrderRecordCommand
    ): AggregateSampleComponent.EntityService.CreateOrderRecordActionCall =
      CreateOrderRecordActionCall(core, action)

    override def createCreateOrderLineRecordActionCall(
      core: ActionCall.Core,
      action: AggregateSampleComponent.EntityService.CreateOrderLineRecordCommand
    ): AggregateSampleComponent.EntityService.CreateOrderLineRecordActionCall =
      CreateOrderLineRecordActionCall(core, action)

  }

  object CreateOrderRecordActionCall {
    def apply(
      core: ActionCall.Core,
      action: AggregateSampleComponent.EntityService.CreateOrderRecordCommand
    ): AggregateSampleComponent.EntityService.CreateOrderRecordActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: AggregateSampleComponent.EntityService.CreateOrderRecordCommand
    ) extends AggregateSampleComponent.EntityService.CreateOrderRecordActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          entity <- exec_pure(org.sample.aggregate.entity.create.Order.create(action.record))
          created <- entity_create(entity)
        } yield OperationResponse(created.toRecord)
      }
    }
  }

  object CreateOrderLineRecordActionCall {
    def apply(
      core: ActionCall.Core,
      action: AggregateSampleComponent.EntityService.CreateOrderLineRecordCommand
    ): AggregateSampleComponent.EntityService.CreateOrderLineRecordActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: AggregateSampleComponent.EntityService.CreateOrderLineRecordCommand
    ) extends AggregateSampleComponent.EntityService.CreateOrderLineRecordActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          entity <- exec_pure(org.sample.aggregate.entity.create.OrderLine.create(action.record))
          created <- entity_create(entity)
        } yield OperationResponse(created.toRecord)
      }
    }
  }


  object AddLineAggregateBehavior {
    def apply(core: ActionCall.Core): AddLineAggregateBehavior = Instance(core)

    final case class Instance(core: ActionCall.Core) extends AddLineAggregateBehavior
  }

  sealed trait AddLineAggregateBehavior extends AggregateBehavior[Record] {
    protected def build_Program(target: Record): ExecUowM[OperationResponse] = {
      for {
        orderId <- exec_pure(Consequence.successOrRecordNotFound[EntityId]("orderId", target).TAKE)
        lineName <- exec_pure(Consequence.successOrRecordNotFound[String]("lineName", target).TAKE)
        quantity <- exec_pure(Consequence.successOrRecordNotFound[Int]("quantity", target).TAKE)
        _ <- exec_from(_validate_quantity_positive(quantity))
        entity <- exec_pure(
          org.sample.aggregate.entity.create.OrderLine.Builder()
            .withOrderId(orderId)
            .withName(lineName)
            .withQuantity(quantity)
            .build()
        )
        created <- entity_create(entity)
      } yield OperationResponse.RecordResponse(Record.data("id" -> created.id.print))
    }
  }

  object OrderAggregateCollectionBuilder extends ContextualAggregateBuilder[org.sample.aggregate.entity.aggregate.Order] {
    def build_with_context(id: EntityId)(using ctx: ExecutionContext): Consequence[org.sample.aggregate.entity.aggregate.Order] =
      given ExecutionContext = ExecutionContext.withAggregateInternalRead(ctx, true)
      for {
        order <- _load_order(id)
        lines <- _load_member_lines(id)
      } yield org.sample.aggregate.entity.aggregate.Order(
        id = order.id,
        name = order.name,
        status = order.status,
        lines = lines
      )

    private def _load_member_lines(
      orderId: EntityId
    )(using ctx: ExecutionContext): Consequence[Vector[org.sample.aggregate.entity.aggregate.OrderLine]] =
      _search_order_lines(
        EntityQuery(
          org.sample.aggregate.entity.query.OrderLine.collectionId,
          Query(
            Record.data(_order_line_join_field_name -> orderId.print)
          )
        )
      ).map(_.map(x =>
        org.sample.aggregate.entity.aggregate.OrderLine(
          id = x.id,
          orderId = x.orderId,
          name = x.name,
          quantity = x.quantity
        )
      ))

    private def _load_order(
      id: EntityId
    )(using ctx: ExecutionContext): Consequence[org.sample.aggregate.entity.Order] =
      ctx.entitySpace.entityOption[org.sample.aggregate.entity.Order]("order") match {
        case Some(collection) =>
          collection.resolve(id).recoverWith {
            case _ =>
              EntityStore.standard()
                .load[org.sample.aggregate.entity.Order](id)
                .flatMap {
                  case Some(s) => Consequence.success(s)
                  case None => Consequence.failure(s"Order not found: ${id.print}")
                }
          }
        case None =>
          EntityStore.standard()
            .load[org.sample.aggregate.entity.Order](id)
            .flatMap {
              case Some(s) => Consequence.success(s)
              case None => Consequence.failure(s"Order not found: ${id.print}")
            }
      }

    private def _search_order_lines(
      query: EntityQuery[org.sample.aggregate.entity.OrderLine]
    )(using ctx: ExecutionContext): Consequence[Vector[org.sample.aggregate.entity.OrderLine]] =
      ctx.entitySpace.entityOption[org.sample.aggregate.entity.OrderLine]("order_line") match {
        case Some(collection) =>
          collection.search(query).map(_.data).flatMap { xs =>
            if (xs.nonEmpty)
              Consequence.success(xs)
            else
              EntityStore.standard().search[org.sample.aggregate.entity.OrderLine](query).map(_.data)
          }.recoverWith { case _ =>
            EntityStore.standard().search[org.sample.aggregate.entity.OrderLine](query).map(_.data)
          }
        case None =>
          EntityStore.standard().search[org.sample.aggregate.entity.OrderLine](query).map(_.data)
      }
  }

  object OrderAggregateCollectionQuery extends ContextualAggregateQuery[org.sample.aggregate.entity.aggregate.Order] {
    def query_with_context(
      q: Query[?]
    )(using ctx: ExecutionContext): Consequence[Vector[org.sample.aggregate.entity.aggregate.Order]] = {
      given ExecutionContext = ExecutionContext.withAggregateInternalRead(ctx, true)
      for {
        orders <- _search_orders(
          EntityQuery(
            org.sample.aggregate.entity.query.Order.collectionId,
            Query(
              q.query match {
                case m: Record => _sanitize_query_record(m)
                case _ => Record.empty
              }
            )
          )
        )
        aggregates <- orders.toList.traverse(x => OrderAggregateCollectionBuilder.build_with_context(x.id)).map(_.toVector)
      } yield aggregates
    }

    private def _search_orders(
      query: EntityQuery[org.sample.aggregate.entity.Order]
    )(using ctx: ExecutionContext): Consequence[Vector[org.sample.aggregate.entity.Order]] =
      ctx.entitySpace.entityOption[org.sample.aggregate.entity.Order]("order") match {
        case Some(collection) =>
          collection.search(query).map(_.data).flatMap { xs =>
            if (xs.nonEmpty)
              Consequence.success(xs)
            else
              EntityStore.standard().search[org.sample.aggregate.entity.Order](query).map(_.data)
          }.recoverWith { case _ =>
            EntityStore.standard().search[org.sample.aggregate.entity.Order](query).map(_.data)
          }
        case None =>
          EntityStore.standard().search[org.sample.aggregate.entity.Order](query).map(_.data)
      }
  }

  private def _sanitize_query_record(p: Record): Record = {
    val filtered = p.asMap.filterNot { case (k, _) =>
      k.startsWith("security.") ||
      k.startsWith("cncf.security.") ||
      k.startsWith("textus.") ||
      k.startsWith("cncf.")
    }
    Record.create(filtered)
  }

  private def _validate_quantity_positive(quantity: Int): Consequence[Unit] =
    if (quantity > 0)
      Consequence.unit
    else
      Consequence.failure(s"${_quantity_positive_invariant_name}: quantity must be > 0")
}
