package org.sample.job

import org.goldenport.Consequence
import org.goldenport.configuration.{Configuration, ConfigurationTrace, ResolvedConfiguration}
import org.goldenport.protocol.{Property, Request}
import org.goldenport.cncf.cli.RunMode
import org.goldenport.cncf.component.{Component, ComponentCreate, ComponentFactory, ComponentOrigin}
import org.goldenport.cncf.context.ExecutionContext
import org.goldenport.cncf.job.{ActionId, ActionTask, JobEngine, JobId, JobQueryReadModel, JobResult, JobRunMode, JobSubmitOption}
import org.goldenport.cncf.path.AliasResolver
import org.goldenport.cncf.subsystem.Subsystem

object JobFlowDemo {
  private val emptyConfiguration =
    ResolvedConfiguration(
      Configuration.empty,
      ConfigurationTrace.empty
    )

  def main(args: Array[String]): Unit = {
    val subsystem = Subsystem(
      name = "job-flow-demo",
      configuration = emptyConfiguration,
      aliasResolver = AliasResolver.empty,
      runMode = RunMode.Command
    )
    val generatedFactory = new JobSampleComponent.Factory
    val initialized = generatedFactory.create(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    val component = ComponentFactory().bootstrap(initialized.head)
    val _ = subsystem.add(Vector(component))

    val jobId = _submit(component, _request()) match {
      case Consequence.Success(s) => s
      case Consequence.Failure(c) => throw new IllegalStateException(c.show)
    }

    val _ = _await_result(component.jobEngine, jobId).getOrElse {
      throw new IllegalStateException(s"job result not found: ${jobId.value}")
    }

    val query = _await_query(component.jobEngine, jobId).getOrElse {
      throw new IllegalStateException(s"job query not found: ${jobId.value}")
    }

    println(_render(query))
  }

  private def _submit(
    component: Component,
    request: Request
  ): Consequence[JobId] = {
    val ctx = ExecutionContext.create()
    val action = component.logic.makeOperationRequest(request).flatMap {
      case action: org.goldenport.cncf.action.Action =>
        Consequence.success(action)
      case other =>
        Consequence.failure(s"OperationRequest must be Action: ${other.show}")
    }
    action.map { a =>
      val task = ActionTask(ActionId.generate(), a, component.actionEngine, Some(component))
      component.logic.submitJob(
        List(task),
        ctx,
        JobSubmitOption(
          runMode = JobRunMode.Sync,
          requestSummary = Some("JobSample.Item.createItem")
        )
      )
    }
  }

  private def _request(): Request =
    Request.of(
      component = "JobSample",
      service = "Item",
      operation = "createItem",
      properties = List(
        Property("name", "alpha", None),
        Property("title", "Alpha", None)
      )
    )

  private def _render(
    read: JobQueryReadModel
  ): String = {
    val timelineKinds = read.timeline.events.map(_.kind).mkString("[", ", ", "]")
    val taskStatuses = read.tasks.tasks.map(_.status.toString).mkString("[", ", ", "]")
    s"""{"job-id":"${read.jobId.value}","status":"${read.status}","result-success":${read.resultSummary.success},"task-statuses":"${taskStatuses}","timeline-kinds":"${timelineKinds}","debug-request-summary":"${read.debug.requestSummary.getOrElse("")}"}"""
  }

  private def _await_query(
    engine: JobEngine,
    jobId: JobId,
    timeoutMillis: Long = 3000L,
    pollMillis: Long = 10L
  ): Option[JobQueryReadModel] = {
    val deadline = System.currentTimeMillis() + timeoutMillis
    var result: Option[JobQueryReadModel] = None
    while (result.isEmpty && System.currentTimeMillis() < deadline) {
      result = engine.query(jobId)
      if (result.isEmpty)
        Thread.sleep(pollMillis)
    }
    result
  }

  private def _await_result(
    engine: JobEngine,
    jobId: JobId,
    timeoutMillis: Long = 3000L,
    pollMillis: Long = 10L
  ): Option[JobResult] = {
    val deadline = System.currentTimeMillis() + timeoutMillis
    var result: Option[JobResult] = None
    while (result.isEmpty && System.currentTimeMillis() < deadline) {
      result = engine.getResult(jobId)
      if (result.isEmpty)
        Thread.sleep(pollMillis)
    }
    result
  }
}
