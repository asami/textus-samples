package org.sample.aggregateexternalupdate

import cats.implicits.*
import org.goldenport.Consequence
import org.goldenport.record.Record
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.{ActionCall, AggregateBehavior}
import org.goldenport.cncf.directive.Query
import org.goldenport.cncf.unitofwork.ExecUowM
import org.simplemodeling.model.datatype.EntityId

final class OrderExternalUpdateFactory extends AggregateExternalUpdateSampleComponent.Factory {
  override val Order: AggregateExternalUpdateSampleComponent.OrderServiceFactory =
    new OrderExternalUpdateOrderServiceFactory

  override def create_aggregate_behavior(
    action: org.goldenport.cncf.action.Action,
    core: ActionCall.Core
  ): Option[AggregateBehavior[?]] =
    action.request.operation match {
      case "cancelOrder" => Some(CancelOrderAggregateBehavior(core))
      case _ => super.create_aggregate_behavior(action, core)
    }

  final class OrderExternalUpdateOrderServiceFactory
      extends AggregateExternalUpdateSampleComponent.OrderServiceFactory {
    override def createCancelOrderActionCall(
      core: ActionCall.Core,
      action: AggregateExternalUpdateSampleComponent.OrderService.CancelOrder
    ): AggregateExternalUpdateSampleComponent.OrderService.CancelOrderActionCall =
      CancelOrderActionCall(core, action)
  }

  object CancelOrderActionCall {
    def apply(
      core: ActionCall.Core,
      action: AggregateExternalUpdateSampleComponent.OrderService.CancelOrder
    ): AggregateExternalUpdateSampleComponent.OrderService.CancelOrderActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: AggregateExternalUpdateSampleComponent.OrderService.CancelOrder
    ) extends AggregateExternalUpdateSampleComponent.OrderService.CancelOrderActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          orderId <- exec_pure(_order_id(action.record).TAKE)
          behavior <- exec_from(resolve_aggregate_behavior().map(_.asInstanceOf[AggregateBehavior[Record]]))
          _ <- exec_from(invoke_aggregate_behavior(behavior, action.record))
          aggregate <- exec_from(
            aggregate_load_c[org.sample.aggregateexternalupdate.entity.aggregate.Order](orderId)
              .map(_.toRecord())
          )
        } yield OperationResponse.RecordResponse(aggregate)
      }
    }
  }

  object CancelOrderAggregateBehavior {
    def apply(core: ActionCall.Core): CancelOrderAggregateBehavior = Instance(core)

    final case class Instance(core: ActionCall.Core) extends CancelOrderAggregateBehavior
  }

  sealed trait CancelOrderAggregateBehavior extends AggregateBehavior[Record] {
    protected def build_Program(target: Record): ExecUowM[OperationResponse] = {
      for {
        orderId <- exec_pure(_order_id(target).TAKE)
        aggregate <- exec_from(
          aggregate_load_c[org.sample.aggregateexternalupdate.entity.aggregate.Order](orderId)
        )
        _ <- entity_update(
          org.sample.aggregateexternalupdate.entity.Order(
            orderId,
            aggregate.userId,
            aggregate.name,
            "Cancelled"
          )
        )
        _ <- aggregate.shipmentOrders.foldLeft(exec_pure(())) { (z, shipment) =>
          z.flatMap { _ =>
            val shipmentId = shipment.id.copy(
              collection = org.sample.aggregateexternalupdate.entity.ShipmentOrder.collectionId
            )
            entity_update(
              org.sample.aggregateexternalupdate.entity.ShipmentOrder(
                shipmentId,
                orderId,
                shipment.title,
                "Cancelled"
              )
            )
          }
        }
      } yield OperationResponse.void
    }
  }

  private def _order_id(record: Record): Consequence[EntityId] =
    Consequence.successOrRecordNotFound[EntityId]("orderId", record).map(
      _.copy(collection = org.sample.aggregateexternalupdate.entity.Order.collectionId)
    )
}
