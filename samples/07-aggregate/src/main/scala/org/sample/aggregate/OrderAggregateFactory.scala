package org.sample.aggregate

import cats.implicits.*
import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.{ActionCall, AggregateBehavior}
import org.goldenport.cncf.directive.Query
import org.goldenport.cncf.entity.EntityStore
import org.goldenport.cncf.entity.aggregate.AggregateDefinition
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
        exec_from(
          aggregate_load_c[org.sample.aggregate.entity.aggregate.Order]("order", id).map(_.toRecord())
        )
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
  private def _validate_quantity_positive(quantity: Int): Consequence[Unit] =
    if (quantity > 0)
      Consequence.unit
    else
      Consequence.failure(s"${_quantity_positive_invariant_name}: quantity must be > 0")
}
