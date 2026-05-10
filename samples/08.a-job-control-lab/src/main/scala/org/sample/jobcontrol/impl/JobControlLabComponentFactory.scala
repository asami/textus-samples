package org.sample.jobcontrol.impl

import cats.syntax.all.*
import org.goldenport.Consequence
import org.goldenport.cncf.action.{Action, ActionCall}
import org.goldenport.cncf.job.{ActionId, ActionTask, JobRunMode, JobSubmitOption}
import org.goldenport.cncf.unitofwork.ExecUowM
import org.goldenport.protocol.{Property, Request}
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.record.Record
import org.sample.jobcontrol.JobControlLabComponent

final class JobControlLabComponentFactory extends JobControlLabComponent.Factory {
  override val Item: JobControlLabComponent.ItemServiceFactory =
    new ItemServiceFactory

  override val entity: JobControlLabComponent.EntityServiceFactory =
    new EntityServiceFactory

  class ItemServiceFactory extends JobControlLabComponent.ItemServiceFactory {
    override def createCreateItemActionCall(
      core: ActionCall.Core,
      action: JobControlLabComponent.ItemService.CreateItem
    ): JobControlLabComponent.ItemService.CreateItemActionCall =
      SubmitControllableJobActionCall(core, action)
  }

  class EntityServiceFactory extends JobControlLabComponent.EntityServiceFactory {
    override def createCreateItemRecordActionCall(
      core: ActionCall.Core,
      action: JobControlLabComponent.EntityService.CreateItemRecordCommand
    ): JobControlLabComponent.EntityService.CreateItemRecordActionCall =
      SlowCreateItemRecordActionCall(core, action)
  }

  object SubmitControllableJobActionCall {
    def apply(
      core: ActionCall.Core,
      action: JobControlLabComponent.ItemService.CreateItem
    ): JobControlLabComponent.ItemService.CreateItemActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: JobControlLabComponent.ItemService.CreateItem
    ) extends JobControlLabComponent.ItemService.CreateItemActionCall {
      protected def build_Program: ExecUowM[OperationResponse] =
        for {
          jobid <- exec_from(_submit(core, action.request.toRecord))
        } yield {
          OperationResponse(Record.data("job_id" -> jobid.value))
        }
    }
  }

  object SlowCreateItemRecordActionCall {
    def apply(
      core: ActionCall.Core,
      action: JobControlLabComponent.EntityService.CreateItemRecordCommand
    ): JobControlLabComponent.EntityService.CreateItemRecordActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: JobControlLabComponent.EntityService.CreateItemRecordCommand
    ) extends JobControlLabComponent.EntityService.CreateItemRecordActionCall {
      protected def build_Program: ExecUowM[OperationResponse] =
        for {
          entity <- exec_pure(action.record)
          _ <- exec_pure(Thread.sleep(30000L))
          r <- entity_create(entity)
        } yield {
          OperationResponse(r.toRecord)
        }
    }
  }

  private def _submit(
    core: ActionCall.Core,
    record: Record
  ) = {
    val component = core.component.getOrElse {
      throw new IllegalStateException("component is required for job control lab")
    }
    val request = Request.of(
      component = "JobControlLab",
      service = "entity",
      operation = "createItemRecord",
      properties = List(
        Property("name", record.getString("name").getOrElse(""), None),
        Property("title", record.getString("title").getOrElse(""), None)
      )
    )
    val action = component.logic.makeOperationRequest(request).flatMap {
      case a: Action =>
        Consequence.success(a)
      case other =>
        Consequence.failure(s"OperationRequest must be Action: ${other.show}")
    }.TAKE
    val task = ActionTask(ActionId.generate(), action, component.actionEngine, Some(component))
    component.logic.submitJob(
      List(task),
      core.executionContext,
      JobSubmitOption(
        runMode = JobRunMode.Async,
        requestSummary = record.getString("name")
      )
    )
  }
}
