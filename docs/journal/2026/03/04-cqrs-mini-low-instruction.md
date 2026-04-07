# 04-cqrs Mini-Low Instruction

Status: `Active Instruction`

This file is the handoff instruction for a smaller model.
Do not rewrite this file into a result note or completion report.

The status authority is:

- [`docs/phase/samples/06-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/06-cqrs.md)

## Read First

Read these files first:

- [`docs/journal/2026/03/04-cqrs-development-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/04-cqrs-development-instruction.md)
- [`docs/phase/samples/06-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/06-cqrs.md)
- [`samples/04-crud/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md)
- [`samples/04.a-crud-seed-import-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04.a-crud-seed-import-lab/README.md)
- [`samples/04.b-simpleentity-crud-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04.b-simpleentity-crud-lab/README.md)
- [`samples/06-cqrs/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/06-cqrs/README.md)

## Goal

Implement `04-cqrs` as the first sample that clearly separates:

- a command-side write path
- a query-side read path

The difference must be visible in runtime behavior.

## Required Outcome

You must produce all of the following:

1. one write command for `Item`
2. one read query for `Item`
3. a visible runtime difference between them
4. README examples that match the real commands
5. an honest phase checklist update

## Fixed Rules

- Use the same model-driven Cozy/CML direction as `02-crud`.
- Prefer one small example over a broad feature set.
- Keep the entity name as `Item` unless there is a strong repo-local reason not to.
- Use `CncfMain --discover=classes` for runtime checks unless the codebase proves another path is required.
- If framework/runtime parameters are needed, use `cncf.*`.
- If query control parameters are needed, use `query.*`.
- Leave unprefixed parameters for domain attributes.

## Work Steps

Follow these steps in order:

1. read the files listed above
2. inspect the current `samples/06-cqrs` scaffold
3. define one write command and one read query
4. implement the smallest CQRS shape that makes the runtime split visible
5. run build/runtime checks
6. update the README to match actual commands
7. update the phase checklist only for items actually confirmed

## Minimum Runtime Checks

At minimum, verify:

- build succeeds
- help for the component succeeds
- help for the command-side target succeeds
- help for the query-side target succeeds
- the write path executes
- the read path executes

## Do Not

- Do not turn `04-cqrs` into an event-sourcing sample.
- Do not add distributed/event-driven concerns.
- Do not add hand-written repository logic as the main approach.
- Do not copy `02-crud` without making the command/query runtime split visible.
- Do not mark the phase checklist `DONE` unless both sides are actually confirmed.

## Report Back

Report only:

- what files you changed
- what command-side target was implemented
- what query-side target was implemented
- what runtime difference is visible
- what checks succeeded
- what remains unfinished, if anything
