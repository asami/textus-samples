# 02-crud Redesign Instruction

Status: `Active Instruction`

This is the active redesign work-order document for `02-crud`.
Do not rewrite this file into a result note or completion report.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md)

## Purpose

Redefine `02-crud` so it demonstrates CNCF CRUD through CML-driven services.

The sample must show this idea:

- define the model in CML
- entity service is available
- aggregate service is available
- CRUD-style usage is available without hand-writing the CRUD logic

This sample must not be a hand-written repository demo.

## Critical Correction

The current hand-written `CrudComponent.scala` direction is wrong for this sample goal.

`02-crud` must not mainly demonstrate:

- a custom repository
- a custom TSV store
- hand-written CRUD business logic

`02-crud` must mainly demonstrate:

- model definition
- generated or framework-provided entity / aggregate service behavior

## Mini-Low Rules

If a smaller model is assigned this work, follow only this process:

1. read this instruction
2. read the current phase checklist
3. read `textus-user-account` and identify the minimal CML-driven CRUD pattern
4. replace the hand-written CRUD idea with the CML-driven entity/aggregate idea
5. update the README and checklist
6. stop

Important:

- do not improve the current hand-written CRUD implementation
- do not add more custom repository code
- do not keep the sample as "custom CRUD on CNCF"

## What The Sample Must Show

The finished sample must make these points visible:

1. `Item` is defined in CML
2. entity service appears from that model
3. aggregate service appears from that model
4. the user can call the resulting CRUD-style surface

The sample must make it obvious that the CRUD capability comes from CNCF + model definition, not from hand-written storage logic.

## Concrete Expectations

The redesign must aim for:

- one minimal CML model
- one entity concept such as `Item`
- command examples that show the generated/runtime-provided CRUD surface
- README text that explicitly says the service comes from the model

## Files To Rework

At minimum, rework these:

- [`samples/04-crud/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md)
- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md)
- the actual sample implementation files under [`samples/04-crud`](/Users/asami/src/dev2026/cncf-samples/samples/04-crud)

## README Requirements

The README must explicitly say all of the following:

- this sample is CML-driven
- entity service and aggregate service come from the model
- the sample is an introduction to the CRUD API surface
- hand-written CRUD logic is not the point
- persistence variations will be studied in later labs or samples

## Pass Conditions

This task is complete only if all of the following are true:

- `02-crud` no longer reads as a hand-written CRUD repository demo
- the sample is clearly based on CML
- entity service and aggregate service are the central concept
- the README explains the model-driven CRUD idea clearly
- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md) is updated honestly

## Notes For Execution

Prefer one minimal, correct CML-driven example over a richer but hand-written CRUD sample.
If there is a choice, remove custom logic rather than adding more custom logic.
