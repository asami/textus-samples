package org.sample.aggregate.impl

import cats.implicits.*
import org.goldenport.Consequence
import org.goldenport.cncf.action.{ActionCall, AggregateBehavior}
import org.goldenport.cncf.component.Component
import org.goldenport.cncf.directive.Query
import org.goldenport.cncf.entity.aggregate.AggregateDefinition
import org.goldenport.cncf.unitofwork.ExecUowM
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.record.Record
import org.simplemodeling.model.datatype.EntityId

final class AggregateSampleComponentFactory extends org.sample.aggregate.AggregateSampleComponent.Factory {
  override val Order: org.sample.aggregate.AggregateSampleComponent.OrderServiceFactory = new OrderAggregateServiceFactory
  override val entity: org.sample.aggregate.AggregateSampleComponent.EntityServiceFactory = new AggregateEntityServiceFactory

  private lazy val _order_aggregate_definition: AggregateDefinition =
    new org.sample.aggregate.AggregateSampleComponent().aggregateDefinitions.find(_.name == "order").getOrElse(
      throw new IllegalStateException("Missing aggregate definition: order")
    )

  private lazy val _add_line_command_name: String =
    _order_aggregate_definition.commands.find(_.name == "addLine").map(_.name).getOrElse("addLine")

  private lazy val _quantity_positive_invariant_name: String =
    _order_aggregate_definition.invariants.find(_.name == "quantityPositive").map(_.name).getOrElse("quantityPositive")

  override def create_aggregate_behavior(
    action: org.goldenport.cncf.action.Action,
    core: ActionCall.Core
  ): Option[AggregateBehavior[?]] =
    action.request.operation match {
      case name if name == _add_line_command_name => Some(AddLineAggregateBehavior(core))
      case _ => super.create_aggregate_behavior(action, core)
    }

  class OrderAggregateServiceFactory extends org.sample.aggregate.AggregateSampleComponent.OrderServiceFactory {
    override def createLoadOrderAggregateActionCall(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.OrderService.LoadOrderAggregate
    ): org.sample.aggregate.AggregateSampleComponent.OrderService.LoadOrderAggregateActionCall =
      LoadOrderAggregateActionCall(core, action)

    override def createSearchOrderAggregateActionCall(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.OrderService.SearchOrderAggregate
    ): org.sample.aggregate.AggregateSampleComponent.OrderService.SearchOrderAggregateActionCall =
      SearchOrderAggregateActionCall(core, action)

    override def createAddLineActionCall(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.OrderService.AddLine
    ): org.sample.aggregate.AggregateSampleComponent.OrderService.AddLineActionCall =
      AddLineActionCall(core, action)
  }

  object LoadOrderAggregateActionCall {
    def apply(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.OrderService.LoadOrderAggregate
    ): org.sample.aggregate.AggregateSampleComponent.OrderService.LoadOrderAggregateActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: org.sample.aggregate.AggregateSampleComponent.OrderService.LoadOrderAggregate
    ) extends org.sample.aggregate.AggregateSampleComponent.OrderService.LoadOrderAggregateActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          id <- exec_pure(_entity_id_c(action.request.toRecord).TAKE)
          r <- aggregate_load_option[org.sample.aggregate.entity.aggregate.Order](id)
        } yield OperationResponse.create(r.map(_.toRecord()))
      }
    }
  }

  object SearchOrderAggregateActionCall {
    def apply(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.OrderService.SearchOrderAggregate
    ): org.sample.aggregate.AggregateSampleComponent.OrderService.SearchOrderAggregateActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: org.sample.aggregate.AggregateSampleComponent.OrderService.SearchOrderAggregate
    ) extends org.sample.aggregate.AggregateSampleComponent.OrderService.SearchOrderAggregateActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        val query = Query.fromRecord(_search_record(action.request.toRecord))
        val queryRecord = _search_record(action.request.toRecord)
        for {
          r <- entity_search[org.sample.aggregate.entity.Order](
            org.sample.aggregate.entity.query.Order.collectionId,
            Query.fromRecord(Record.empty)
          )
          filtered = r.data.filter(_matches_order_query(_, queryRecord))
          data <- filtered.toList.traverse(x => aggregate_load_option[org.sample.aggregate.entity.aggregate.Order](x.id)).map(_.flatten.toVector)
        } yield OperationResponse.create(
          org.goldenport.cncf.directive.SearchResult(
            query,
            data = data,
            totalCount = Some(data.size),
            offset = None,
            limit = None,
            fetchedCount = data.size
          )
        )
      }
    }
  }

  object AddLineActionCall {
    def apply(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.OrderService.AddLine
    ): org.sample.aggregate.AggregateSampleComponent.OrderService.AddLineActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: org.sample.aggregate.AggregateSampleComponent.OrderService.AddLine
    ) extends org.sample.aggregate.AggregateSampleComponent.OrderService.AddLineActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          orderId <- exec_pure(_order_id_c(action.record).TAKE)
          behavior <- exec_from(resolve_aggregate_behavior().map(_.asInstanceOf[AggregateBehavior[Record]]))
          _ <- exec_from(invoke_aggregate_behavior(behavior, _normalized_record(action.record)))
          aggregate <- _load_order_aggregate_record(orderId)
        } yield OperationResponse.RecordResponse(aggregate)
      }

      private def _load_order_aggregate_record(id: EntityId): ExecUowM[Record] =
        exec_from(
          aggregate_load_c[org.sample.aggregate.entity.aggregate.Order]("order", id).map(_.toRecord())
        )
    }
  }

  class AggregateEntityServiceFactory extends org.sample.aggregate.AggregateSampleComponent.EntityServiceFactory {
    override def createCreateOrderRecordActionCall(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderRecordCommand
    ): org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderRecordActionCall =
      CreateOrderRecordActionCall(core, action)

    override def createCreateOrderLineRecordActionCall(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderLineRecordCommand
    ): org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderLineRecordActionCall =
      CreateOrderLineRecordActionCall(core, action)
  }

  object CreateOrderRecordActionCall {
    def apply(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderRecordCommand
    ): org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderRecordActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderRecordCommand
    ) extends org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderRecordActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          created <- entity_create(action.record)
        } yield OperationResponse(created.toRecord)
      }
    }
  }

  object CreateOrderLineRecordActionCall {
    def apply(
      core: ActionCall.Core,
      action: org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderLineRecordCommand
    ): org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderLineRecordActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderLineRecordCommand
    ) extends org.sample.aggregate.AggregateSampleComponent.EntityService.CreateOrderLineRecordActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          created <- entity_create(action.record)
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
        orderId <- exec_pure(_order_id_c(target).TAKE)
        lineName <- exec_pure(_line_name_c(target).TAKE)
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

  private def _validate_quantity_positive(quantity: Int): Consequence[Unit] =
    if (quantity > 0) Consequence.unit
    else Consequence.failure(s"${_quantity_positive_invariant_name}: quantity must be > 0")

  private def _order_id_c(record: Record): Consequence[EntityId] = {
    record.getAs[EntityId]("orderId")
      .orElse(record.getAs[EntityId]("order-id"))
      .map(Consequence.success)
      .getOrElse(Consequence.failRecordNotFound("orderId", record))
  }

  private def _entity_id_c(record: Record): Consequence[EntityId] = {
    record.getAs[EntityId]("id")
      .orElse(record.getAs[EntityId]("orderId"))
      .orElse(record.getAs[EntityId]("order-id"))
      .map(Consequence.success)
      .getOrElse(Consequence.failRecordNotFound("id", record))
  }

  private def _normalized_record(record: Record): Record =
    _line_name_alias(_order_id_alias(record))

  private def _order_id_alias(record: Record): Record =
    record.getAny("order-id").map(v => record ++ Record.data("orderId" -> v)).getOrElse(record)

  private def _line_name_alias(record: Record): Record =
    record.getAny("line-name").map(v => record ++ Record.data("lineName" -> v)).getOrElse(record)

  private def _search_record(record: Record): Record =
    Record(record.fields.filterNot(_.key == "textus"))

  private def _line_name_c(record: Record): Consequence[String] =
    record.getAs[String]("lineName")
      .orElse(record.getAs[String]("line-name"))
      .map(Consequence.success)
      .getOrElse(Consequence.failRecordNotFound("lineName", record))

  private def _matches_order_query(
    order: org.sample.aggregate.entity.Order,
    query: Record
  ): Boolean = {
    val record = order.toRecord()
    val nameok = query.getString("name").forall(v => record.getString("name").contains(v))
    val statusok = query.getString("status").forall(v => record.getString("status").contains(v))
    nameok && statusok
  }
}
