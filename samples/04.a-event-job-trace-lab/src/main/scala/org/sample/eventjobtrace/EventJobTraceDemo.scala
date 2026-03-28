package org.sample.eventjobtrace

import org.goldenport.Consequence
import org.goldenport.configuration.{Configuration, ConfigurationTrace, ResolvedConfiguration}
import org.goldenport.protocol.{Property, Request}
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.Action
import org.goldenport.cncf.cli.RunMode
import org.goldenport.cncf.component.{Component, ComponentCreate, ComponentFactory, ComponentOrigin}
import org.goldenport.cncf.context.ExecutionContext
import org.goldenport.cncf.event.EventStore
import org.goldenport.cncf.job.JobId
import org.goldenport.cncf.path.AliasResolver
import org.goldenport.cncf.subsystem.Subsystem
import org.goldenport.record.Record

object EventJobTraceDemo {
  private val emptyConfiguration =
    ResolvedConfiguration(
      Configuration.empty,
      ConfigurationTrace.empty
    )

  def main(args: Array[String]): Unit = {
    val subsystem = Subsystem(
      name = "event-job-trace-demo",
      configuration = emptyConfiguration,
      aliasResolver = AliasResolver.empty,
      runMode = RunMode.Command
    )
    val generatedFactory = new org.sample.eventdriven.EventDrivenComponent.Factory
    val initialized = generatedFactory.create(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    val component = ComponentFactory().bootstrap(initialized.head)
    val _ = subsystem.add(Vector(component))

    val emitResponse = _execute(
      component,
      Request.of(
        component = "EventDriven",
        service = "Event",
        operation = "emitEvent",
        properties = List(
          Property("name", "alpha", None),
          Property("title", "Alpha", None)
        )
      )
    )
    val jobId = emitResponse match {
      case Consequence.Success(OperationResponse.Scalar(jobIdValue)) =>
        JobId.parse(jobIdValue.toString) match {
          case Consequence.Success(parsedJobId) =>
            val _ = component.logic.awaitJobResult(parsedJobId)
            parsedJobId
          case Consequence.Failure(conclusion) =>
            throw new IllegalStateException(conclusion.show)
        }
      case Consequence.Success(response) =>
        throw new IllegalStateException(s"job id expected: ${response.show}")
      case Consequence.Failure(c) =>
        throw new IllegalStateException(c.show)
    }

    val effect = _execute(
      component,
      Request.of(
        component = "EventDriven",
        service = "Event",
        operation = "loadEffect"
      )
    )

    val job = component.jobEngine.query(jobId).getOrElse {
      throw new IllegalStateException(s"job not found: ${jobId.value}")
    }
    val timeline = component.jobEngine.queryTimeline(jobId).getOrElse {
      throw new IllegalStateException(s"job timeline not found: ${jobId.value}")
    }
    val eventNames = component.eventStore match {
      case Some(store) =>
        store.query(EventStore.Query()).TAKE.map(_.name)
      case None =>
        Vector.empty[String]
    }

    effect match {
      case Consequence.Success(OperationResponse.RecordResponse(record)) =>
        println(
          s"""{"event":"item.changed","reaction":"recordEffect","job-status":"${job.status}","job-history":"${timeline.events.map(_.kind)}","event-names":"${eventNames}","effect":${record.toJsonString}}"""
        )
      case Consequence.Success(response) =>
        println(response.show)
      case Consequence.Failure(c) =>
        throw new IllegalStateException(c.show)
    }
  }

  private def _execute(
    component: Component,
    request: Request
  ): Consequence[OperationResponse] =
    component.logic.makeOperationRequest(request).flatMap {
      case action: Action =>
        component.logic.executeAction(action, ExecutionContext.create())
      case m =>
        Consequence.failure(s"OperationRequest must be Action: ${m.show}")
    }
}
