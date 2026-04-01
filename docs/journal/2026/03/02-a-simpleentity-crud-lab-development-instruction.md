# 02.b-simpleentity-crud-lab Development Instruction

Status: `Active Instruction`

Reviewed and updated on 2026-03-26.

## Purpose

Create a follow-up lab after `02-crud` that demonstrates CRUD generation with `SimpleEntity`.

`02-crud` already shows the minimum CML-driven CRUD flow.
`02.b-simpleentity-crud-lab` should show what changes when the model is explicitly based on `SimpleEntity`.

## Position In The Sequence

- `02-crud`
  - minimum model-driven CRUD
- `02.b-simpleentity-crud-lab`
  - `SimpleEntity`-based CRUD
- `04-cqrs`
  - next structural step after CRUD

## Main Point

This lab should make the `SimpleEntity` shape visible.

It should help the reader observe:

- that `SimpleEntity` can be declared in CML
- that Cozy/CNCF generate CRUD-oriented surfaces from it
- that generated value types include the standard `SimpleEntity` attribute groups
- how this differs from the smaller `02-crud` sample

## Required Direction

Use the same overall method as:

- `/Users/asami/src/dev2026/cncf-samples/samples/02-crud`
- `/Users/asami/src/dev2026/textus-user-account`

That means:

- Dox-style CML under `src/main/cozy`
- `cozy`-driven generation
- no hand-written CRUD repository logic as the main idea

## Model Expectation

The lab should define an entity that clearly uses `SimpleEntity`.

The first target is intentionally small:

- one component
- one main entity
- generated CRUD surfaces visible through help/runtime inspection

Use a domain that stays simple and structural.
Do not introduce business complexity that belongs to later samples.

## What To Show

At minimum, the lab should show:

- generated component help
- generated service help
- generated operation help
- generated value/entity types that reflect `SimpleEntity`

The comparison with `02-crud` should be explicit in the README.

## Do Not

- do not fall back to hand-written CRUD logic
- do not use the old compact CML form
- do not add database-specific logic here
- do not turn this sample into CQRS, event-driven, or subsystem work

## User-Facing Note

README should clearly say:

- `cozy` command must already be installed
- this sample is a `SimpleEntity` variation of `02-crud`
- the goal is to inspect the generated shape, not to add custom repository logic

## Done Condition

This lab can be treated as ready for verification when:

- Dox-style CML exists
- `cozyGenerate` succeeds
- `compile` succeeds
- the generated `SimpleEntity`-based CRUD surface can be inspected
- the README explains the difference from `02-crud`
