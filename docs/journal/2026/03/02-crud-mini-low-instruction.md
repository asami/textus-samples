# 02-crud Mini-Low Instruction

Status: `Active Instruction`

This instruction is written for a smaller model.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md)

## Goal

Redo `02-crud` so it follows the same method as `textus-user-account`.

Target idea:

- define the model in CML
- use `cozy`
- entity service appears
- aggregate service appears
- CRUD-style usage comes from the model

## Important

`cozy` command must already be installed.

Do not work on `02-crud` unless `cozy` is available.

## Read First

Read these files first:

1. [`samples/04-crud/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md)
2. [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md)
3. [`docs/journal/2026/03/02-crud-redesign-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/02-crud-redesign-instruction.md)
4. `textus-user-account` in `/Users/asami/src/dev2026/textus-user-account`

## Do

1. Identify the minimal CML-driven CRUD pattern in `textus-user-account`.
2. Apply the same method to `02-crud`.
3. Make `02-crud` about CML-driven entity service and aggregate service.
4. Update `samples/04-crud/README.md`.
5. Update `docs/phase/samples/04-crud.md`.

## Do Not

- do not improve the current hand-written repository sample
- do not keep TSV storage as the main idea
- do not keep custom CRUD logic as the main idea
- do not mark the checklist `DONE` unless the redesign is really implemented

## Required README Content

The README must say:

- `cozy` is required
- the sample follows the `textus-user-account` method
- CRUD surface comes from CML + CNCF tooling
- entity service and aggregate service are the main point

## Stop Condition

Stop after:

- the sample is redefined correctly
- the README is updated
- the checklist is updated honestly

Do not continue into extra refactoring.
