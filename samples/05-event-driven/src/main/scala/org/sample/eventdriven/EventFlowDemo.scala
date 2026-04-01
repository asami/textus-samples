package org.sample.eventdriven

import org.goldenport.Consequence
import org.goldenport.configuration.{Configuration, ConfigurationTrace, ResolvedConfiguration}
import org.goldenport.protocol.{Property, Request}
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.Action
import org.goldenport.cncf.cli.RunMode
import org.goldenport.cncf.component.{Component, ComponentCreate, ComponentFactory, ComponentOrigin, ComponentLogic}
import org.goldenport.cncf.context.ExecutionContext
import org.goldenport.cncf.job.JobId
import org.goldenport.cncf.path.AliasResolver
import org.goldenport.cncf.subsystem.Subsystem

object EventFlowDemo {
  private val emptyConfiguration =
    ResolvedConfiguration(
      Configuration.empty,
      ConfigurationTrace.empty
    )

  def main(args: Array[String]): Unit = {
    val subsystem = Subsystem(
      name = "event-flow-demo",
      configuration = emptyConfiguration,
      aliasResolver = AliasResolver.empty,
      runMode = RunMode.Command
    )
    val generatedFactory = new EventDrivenComponent.Factory
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
    emitResponse match {
      case Consequence.Success(OperationResponse.Scalar(jobIdValue)) =>
        val jobId = jobIdValue.toString
        JobId.parse(jobId) match {
          case Consequence.Success(parsedJobId) =>
            val _ = component.logic.awaitJobResult(parsedJobId)
          case Consequence.Failure(conclusion) =>
            throw new IllegalStateException(conclusion.show)
        }
      case Consequence.Success(_) =>
        ()
      case Consequence.Failure(c) =>
        throw new IllegalStateException(c.show)
    }

    val loadResponse = _execute(
      component,
      Request.of(
        component = "EventDriven",
        service = "Event",
        operation = "loadEffect"
      )
    )
    loadResponse match {
      case Consequence.Success(OperationResponse.RecordResponse(record)) =>
        println(record.toJsonString)
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
