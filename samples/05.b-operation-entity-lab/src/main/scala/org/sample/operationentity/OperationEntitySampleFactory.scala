package org.sample.operationentity

import cats.implicits.*
import org.goldenport.Consequence
import org.goldenport.datatype.Name
import org.goldenport.protocol.operation.OperationResponse
import org.goldenport.cncf.action.ActionCall
import org.goldenport.cncf.unitofwork.ExecUowM
import org.simplemodeling.model.datatype.EntityId

final class OperationEntitySampleFactory extends OperationEntitySampleComponent.Factory {
  override val PersonApp: OperationEntitySampleComponent.PersonAppServiceFactory =
    new PersonAppServiceFactory

  class PersonAppServiceFactory extends OperationEntitySampleComponent.PersonAppServiceFactory {
    override def createGetPersonCardActionCall(
      core: ActionCall.Core,
      action: OperationEntitySampleComponent.PersonAppService.GetPersonCardQuery
    ): OperationEntitySampleComponent.PersonAppService.GetPersonCardActionCall =
      GetPersonCardActionCall(core, action)
  }

  object GetPersonCardActionCall {
    def apply(
      core: ActionCall.Core,
      action: OperationEntitySampleComponent.PersonAppService.GetPersonCardQuery
    ): OperationEntitySampleComponent.PersonAppService.GetPersonCardActionCall =
      Instance(core, action)

    final case class Instance(
      core: ActionCall.Core,
      override val action: OperationEntitySampleComponent.PersonAppService.GetPersonCardQuery
    ) extends OperationEntitySampleComponent.PersonAppService.GetPersonCardActionCall {
      protected def build_Program: ExecUowM[OperationResponse] = {
        for {
          personId <- exec_from(_person_id_c)
          name <- exec_pure(Name.parse("Alice").TAKE)
          person <- exec_pure(org.sample.operationentity.entity.Person(personId, name))
          card <- exec_pure(domain.value.PersonCard.create(person.name))
        } yield OperationResponse.RecordResponse(card.toRecord())
      }

      private def _person_id_c: Consequence[EntityId] =
        action.record.getAsC[EntityId]("personId")
          .flatMap(_.map(Consequence.success).getOrElse(action.record.getAsC[EntityId]("person_id").flatMap(_.map(Consequence.success).getOrElse(action.record.getAsC[EntityId]("person-id").flatMap(_.map(Consequence.success).getOrElse(Consequence.failure(s"personId not found: ${action.record}")))))))

    }
  }
}
