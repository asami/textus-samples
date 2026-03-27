package org.sample.jobcontrol

import org.goldenport.Consequence
import org.goldenport.configuration.{Configuration, ConfigurationTrace, ResolvedConfiguration}
import org.goldenport.protocol.{Argument, Property, Request}
import org.goldenport.cncf.component.{Component, ComponentCreate, ComponentFactory, ComponentOrigin}
import org.goldenport.cncf.context.ExecutionContext
import org.goldenport.cncf.job.{ActionId, ActionTask, JobControlCommand, JobId, JobRunMode, JobSubmitOption}
import org.goldenport.cncf.subsystem.{DefaultSubsystemFactory, Subsystem}
import org.goldenport.protocol.Response
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.Action
import org.goldenport.record.Record
import org.goldenport.record.io.RecordDecoder

object JobControlDemo {
  private val emptyConfiguration =
    ResolvedConfiguration(
      Configuration.empty,
      ConfigurationTrace.empty
    )

  def main(args: Array[String]): Unit = {
    val subsystem = DefaultSubsystemFactory.default(
      mode = Some("command")
    )
    val generatedFactory = new JobControlLabComponent.Factory
    val initialized = generatedFactory.create(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    val component = ComponentFactory().bootstrap(initialized.head)
    val _ = subsystem.add(Vector(component))

    val ctx = ExecutionContext.create(org.goldenport.cncf.context.SecurityContext.Privilege.ApplicationContentManager)

    val suspended = _run_suspend_resume(subsystem, component, ctx)
    val cancelled = _run_cancel(subsystem, component, ctx)
    val suspendedJobId = _jobId(suspended)
    val cancelledJobId = _jobId(cancelled)
    val suspendedEvents = _jobEvents(subsystem, suspendedJobId)
    val cancelledEvents = _jobEvents(subsystem, cancelledJobId)

    println(
      s"""{"suspend-job-id":"${suspendedJobId.value}","suspend-status":"${_status(suspended)}","suspend-history":"${_historyKinds(subsystem, suspendedJobId)}","suspend-events":"${_eventNames(suspendedEvents)}","cancel-job-id":"${cancelledJobId.value}","cancel-status":"${_status(cancelled)}","cancel-history":"${_historyKinds(subsystem, cancelledJobId)}","cancel-events":"${_eventNames(cancelledEvents)}"}"""
    )
  }

  private def _run_suspend_resume(
    subsystem: Subsystem,
    component: Component,
    ctx: ExecutionContext
  ): Record = {
    val jobId = _submit(component, ctx, "suspend-resume")
    _await_status(subsystem, jobId, "Running")
    _control(subsystem, jobId, JobControlCommand.Suspend)
    _await_status(subsystem, jobId, "Suspended")
    _control(subsystem, jobId, JobControlCommand.Resume)
    _await_status(subsystem, jobId, "Succeeded")
    _loadJob(subsystem, jobId).getOrElse {
      throw new IllegalStateException(s"job query not found: ${jobId.value}")
    }
  }

  private def _run_cancel(
    subsystem: Subsystem,
    component: Component,
    ctx: ExecutionContext
  ): Record = {
    val jobId = _submit(component, ctx, "cancel")
    _await_status(subsystem, jobId, "Running")
    _control(subsystem, jobId, JobControlCommand.Cancel)
    _await_status(subsystem, jobId, "Cancelled")
    _loadJob(subsystem, jobId).getOrElse {
      throw new IllegalStateException(s"job query not found: ${jobId.value}")
    }
  }

  private def _submit(
    component: Component,
    ctx: ExecutionContext,
    summary: String
  ): JobId = {
    val request = Request.of(
      component = "JobControlLab",
      service = "Item",
      operation = "createItem",
      properties = List(
        Property("name", summary, None),
        Property("title", summary.capitalize, None)
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
      ctx,
      JobSubmitOption(
        runMode = JobRunMode.Async,
        requestSummary = Some(summary)
      )
    )
  }

  private def _control(
    subsystem: Subsystem,
    jobId: JobId,
    command: JobControlCommand
  ): Unit = {
    val request = Request.of(
      component = "job_control",
      service = "job_admin",
      operation = command match {
        case JobControlCommand.Cancel => "cancel_job"
        case JobControlCommand.Suspend => "suspend_job"
        case JobControlCommand.Resume => "resume_job"
        case JobControlCommand.Retry => "resume_job"
      },
      arguments = List(Argument("id", jobId.value)),
      properties = List(
        Property("cncf.security.privilege", "content_admin", None)
      )
    )
    subsystem.execute(request).TAKE
  }

  private def _loadJob(
    subsystem: Subsystem,
    jobId: JobId
  ): Option[Record] =
    _record(
      subsystem,
      component = "job_control",
      service = "job",
      operation = "load_job",
      arguments = List(Argument("id", jobId.value))
    )

  private def _historyKinds(
    subsystem: Subsystem,
    jobId: JobId
  ): String = {
    val history = _record(
      subsystem,
      component = "job_control",
      service = "job",
      operation = "load_job_history",
      arguments = List(Argument("id", jobId.value))
    ).getOrElse {
      throw new IllegalStateException(s"job history not found: ${jobId.value}")
    }
    _recordVector(history, "events").map(_.getString("kind").getOrElse("")).mkString("[", ", ", "]")
  }

  private def _await_status(
    subsystem: Subsystem,
    jobId: JobId,
    expected: String,
    timeoutMillis: Long = 3000L,
    pollMillis: Long = 10L
  ): Unit = {
    val deadline = System.currentTimeMillis() + timeoutMillis
    var current = _loadJob(subsystem, jobId).flatMap(_.getString("status"))
    while (current != Some(expected) && System.currentTimeMillis() < deadline) {
      Thread.sleep(pollMillis)
      current = _loadJob(subsystem, jobId).flatMap(_.getString("status"))
    }
    if (current != Some(expected))
      throw new IllegalStateException(s"job status not reached: ${jobId.value} expected=$expected actual=${current.getOrElse("<none>")}")
  }

  private def _jobEvents(
    subsystem: Subsystem,
    jobId: JobId
  ): Vector[String] = {
    val record = _record(
      subsystem,
      component = "event",
      service = "event_admin",
      operation = "load_job_events",
      arguments = List(Argument("id", jobId.value))
    ).getOrElse {
      throw new IllegalStateException(s"job events not found: ${jobId.value}")
    }
    _recordVector(record, "events").map(_.getString("name").getOrElse(""))
  }

  private def _eventNames(events: Vector[String]): String =
    events.mkString("[", ", ", "]")

  private def _jobId(record: Record): JobId =
    JobId.parse(record.getString("job-id").getOrElse(sys.error("job-id is required"))).TAKE

  private def _status(record: Record): String =
    record.getString("status").getOrElse("")

  private def _record(
    subsystem: Subsystem,
    component: String,
    service: String,
    operation: String,
    arguments: List[Argument] = Nil,
    properties: List[Property] = Nil
  ): Option[Record] = {
    val request = Request.of(
      component = component,
      service = service,
      operation = operation,
      arguments = arguments,
      properties = properties
    )
    subsystem.execute(request).flatMap {
      case Response.Json(value) =>
        RecordDecoder().json(value)
      case Response.Yaml(value) =>
        RecordDecoder().yaml(value)
      case other =>
        Consequence.failure(s"record response is required: ${other.show}")
    }.toOption
  }

  private def _recordVector(record: Record, key: String): Vector[Record] =
    record.asMap.get(key) match {
      case Some(xs: Seq[?]) =>
        xs.collect { case r: Record => r }.toVector
      case Some(r: Record) =>
        Vector(r)
      case _ =>
        Vector.empty
    }
}
