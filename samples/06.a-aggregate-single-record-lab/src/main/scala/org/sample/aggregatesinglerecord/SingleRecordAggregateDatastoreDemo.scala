package org.sample.aggregatesinglerecord

import cats.~>
import io.circe.Json
import io.circe.syntax._
import domain.value.OrderLine
import org.goldenport.Consequence
import org.goldenport.cncf.context.{CorrelationId, DataStoreContext, EntityStoreContext, ExecutionContext, ObservabilityContext, RuntimeContext, ScopeContext, ScopeKind, TraceId}
import org.goldenport.cncf.datastore.DataStoreSpace
import org.goldenport.cncf.entity.{EntityStore, EntityStoreSpace}
import org.goldenport.cncf.entity.EntityPersistentCreate
import org.goldenport.cncf.http.FakeHttpDriver
import org.goldenport.cncf.unitofwork.{UnitOfWork, UnitOfWorkOp}
import org.goldenport.datatype.Name
import org.goldenport.record.Record
import org.sample.aggregatesinglerecord.entity.Order
import org.sample.aggregatesinglerecord.entity.Order.given
import org.simplemodeling.model.datatype.EntityId

object SingleRecordAggregateDatastoreDemo {
  def main(args: Array[String]): Unit = {
    val line1 = OrderLine.create(Name("Widget"), 2)
    val line2 = OrderLine.create(Name("Cable"), 1)
    val order = Order.Builder()
      .withId(EntityId.parse("major-minor-entity-order-20260330000000-bbb222").TAKE)
      .withName(Name("Datastore"))
      .withStatus("Active")
      .withLines(Vector(line1, line2))
      .build()

    val datastorespace = DataStoreSpace.default()
    val entitystorespace = new EntityStoreSpace().addEntityStore(EntityStore.standard())
    given ExecutionContext = _execution_context(datastorespace, entitystorespace)
    given EntityPersistentCreate[Order] = new EntityPersistentCreate[Order] {
      def id(e: Order): Option[EntityId] = Some(e.id)
      def toRecord(e: Order): Record = e.toDataStore()
      def collection(e: Order) = summon[org.goldenport.cncf.entity.EntityPersistent[Order]].id(e).collection
    }

    val _ = entitystorespace.create(UnitOfWorkOp.EntityStoreCreate(order, summon)).TAKE
    val loaded = entitystorespace.load(UnitOfWorkOp.EntityStoreLoad(order.id, summon)).TAKE.getOrElse {
      sys.error("saved order not found")
    }

    val payload = Json.obj(
      "pattern" -> "single-record-aggregate-datastore".asJson,
      "saved" -> _record_json(order.toRecord()),
      "loaded" -> _record_json(loaded.toRecord()),
      "line-count" -> loaded.lines.size.asJson
    )
    println(payload.noSpaces)
  }

  private def _execution_context(
    datastorespace: DataStoreSpace,
    entitystorespace: EntityStoreSpace
  ): ExecutionContext = {
    val observability = ObservabilityContext(
      traceId = TraceId("test", "single_record_aggregate_datastore"),
      spanId = None,
      correlationId = Some(CorrelationId("test", "single_record_aggregate_datastore"))
    )
    val driver = FakeHttpDriver.okText("nop")
    lazy val context: ExecutionContext = ExecutionContext.create(runtime)
    lazy val runtime: RuntimeContext = new RuntimeContext(
      core = ScopeContext.Core(
        kind = ScopeKind.Runtime,
        name = "single-record-aggregate-datastore-runtime",
        parent = None,
        observabilityContext = observability,
        httpDriverOption = Some(driver),
        datastore = Some(DataStoreContext(datastorespace)),
        entitystore = Some(EntityStoreContext(entitystorespace))
      ),
      unitOfWorkSupplier = () => new UnitOfWork(context),
      unitOfWorkInterpreterFn = new (UnitOfWorkOp ~> Consequence) {
        def apply[A](fa: UnitOfWorkOp[A]): Consequence[A] = {
          val _ = fa
          throw new UnsupportedOperationException("unitOfWorkInterpreter is not used in datastore demo")
        }
      },
      commitAction = uow => { val _ = uow.commit(); () },
      abortAction = uow => { val _ = uow.rollback(); () },
      disposeAction = _ => (),
      token = "single-record-aggregate-datastore-runtime-context"
    )
    context
  }

  private def _record_json(p: Record): Json =
    Json.obj(p.asMap.iterator.map { case (k, v) => k -> _json(v) }.toSeq: _*)

  private def _json(v: Any): Json = v match {
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
  }
}
