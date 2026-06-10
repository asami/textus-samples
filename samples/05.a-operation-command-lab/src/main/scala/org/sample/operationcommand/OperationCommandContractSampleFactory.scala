package org.sample.operationcommand

import org.goldenport.record.Record
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.ActionCall
import org.goldenport.cncf.unitofwork.ExecUowM

class OperationCommandContractSampleFactory extends OperationCommandContractSampleComponent.Factory {
  override val Greeting: OperationCommandContractSampleComponent.GreetingServiceFactory =
    new GreetingServiceFactory

  class GreetingServiceFactory extends OperationCommandContractSampleComponent.GreetingServiceFactory {
    override def createSubmitGreetingActionCall(
      core: ActionCall.Core,
      action: OperationCommandContractSampleComponent.GreetingService.GreetingCommand
    ): OperationCommandContractSampleComponent.GreetingService.SubmitGreetingActionCall =
      SubmitGreetingActionCall(core, action)
  }

  object SubmitGreetingActionCall {
    def apply(
      core: ActionCall.Core,
      action: OperationCommandContractSampleComponent.GreetingService.GreetingCommand
    ): OperationCommandContractSampleComponent.GreetingService.SubmitGreetingActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: OperationCommandContractSampleComponent.GreetingService.GreetingCommand
    ) extends OperationCommandContractSampleComponent.GreetingService.SubmitGreetingActionCall {
      protected def build_Program: ExecUowM[OperationResponse] =
        exec_pure(
          OperationResponse.RecordResponse(
            Record.data(
              "status" -> "accepted",
              "name" -> action.record.getString("name").getOrElse("anonymous")
            )
          )
        )
    }
  }
}

final class OperationCommandContractSampleComponentFactory extends OperationCommandContractSampleFactory
