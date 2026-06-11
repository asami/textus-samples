package org.sample.componentmethodexecution

import org.goldenport.record.Record
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.ActionCall
import org.goldenport.cncf.unitofwork.ExecUowM

class ComponentMethodExecutionSampleFactory
    extends ComponentMethodExecutionSampleComponent.Factory {

  override val Greeting: ComponentMethodExecutionSampleComponent.GreetingServiceFactory =
    new GreetingServiceFactory

  class GreetingServiceFactory extends ComponentMethodExecutionSampleComponent.GreetingServiceFactory {
    override def createComposeGreetingActionCall(
      core: ActionCall.Core,
      action: ComponentMethodExecutionSampleComponent.GreetingService.GreetingQuery
    ): ComponentMethodExecutionSampleComponent.GreetingService.ComposeGreetingActionCall =
      ComposeGreetingActionCall(core, action)
  }

  object ComposeGreetingActionCall {
    def apply(
      core: ActionCall.Core,
      action: ComponentMethodExecutionSampleComponent.GreetingService.GreetingQuery
    ): ComponentMethodExecutionSampleComponent.GreetingService.ComposeGreetingActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: ComponentMethodExecutionSampleComponent.GreetingService.GreetingQuery
    ) extends ComponentMethodExecutionSampleComponent.GreetingService.ComposeGreetingActionCall {
      protected def build_Program: ExecUowM[OperationResponse] =
        exec_pure(
          OperationResponse.RecordResponse(
            Record.data(
              "message" -> s"Hello, ${action.record.getString("name").getOrElse("anonymous")}"
            )
          )
        )
    }
  }
}

final class ComponentMethodExecutionSampleComponentFactory extends ComponentMethodExecutionSampleFactory
