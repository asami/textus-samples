# 04-cqrs Development Instruction

Status: `Active Instruction`

This is the active development work-order document for `04-cqrs`.
Do not rewrite this file into a result note or completion report.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/06-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/06-cqrs.md)

## Purpose

Build `04-cqrs` as the first sample that makes command and query responsibilities visibly different.

This sample must demonstrate:

- one entity type
- a command side that is job-backed or asynchronous in shape
- a query side that is immediate and read-oriented
- the minimum CNCF flow needed to show CQRS behavior

## Scope

Keep `04-cqrs` deliberately narrow.

Do not add event-driven distribution here.
Do not turn this into an event-sourcing sample.
Do not make persistence technology the main point.

This sample should answer one question:

- how does CNCF present a command side and a query side as distinct runtime surfaces?

## Required Runtime Shape

`04-cqrs` must show a clear split between:

- command side
  - job-backed or asynchronous in execution shape
- query side
  - synchronous and immediate

The simplest acceptable demonstration is:

- one command that schedules or runs through job integration
- one query that returns current read-side information immediately

## Required Operations

The sample must provide these minimum user-facing operations:

- one write command, such as `createItem`
- one read query, such as `getItem`

It is acceptable to add `listItems` if it helps explain the read side,
but the core requirement is one write and one read path with visibly different runtime behavior.

## Expected Behavior

The sample should define one simple entity, called `Item`.

Minimum behavior:

- `createItem`
  - is executed through the command side
  - is described as job-backed or asynchronous
- `getItem`
  - is executed through the query side
  - returns current state immediately

The sample must be easy to run locally and easy to inspect through command/help output.

## Recommended Shape

Use the same model-driven direction as `02-crud`.

Recommended conceptual structure:

- CML/Cozy model as the source
- generated component/runtime surface
- command side exposed through a job-aware route
- query side exposed through a direct read route

Prefer one small coherent CQRS example over a broad set of operations.

## Relationship To Earlier Samples

`04-cqrs` should build directly on the lessons of:

- `02-crud`
  - base generated CRUD surface
- `02.a-crud-seed-import-lab`
  - descriptor-first runtime metadata and seeded read verification
- `02.b-simpleentity-crud-lab`
  - `SimpleEntity`-shaped generated CRUD surface

But `04-cqrs` must add a new point:

- command and query are no longer just different names
- they are different runtime paths

## Mini-Low Rules

If a smaller model is assigned this work, follow only this process:

1. read the sample README
2. read the phase checklist
3. implement one write command and one read query
4. make the write side job-backed
5. make the read side immediate
6. update the README to match actual commands
7. stop

Do not add extra architecture.
Do not jump to event sourcing/distribution.
Do not add unrelated persistence concerns.

## Files To Update

At minimum, update these files:

- [`samples/06-cqrs/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/06-cqrs/README.md)
- [`docs/phase/samples/06-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/06-cqrs.md)
- the actual sample implementation files under [`samples/06-cqrs`](/Users/asami/src/dev2026/cncf-samples/samples/06-cqrs)

## README Requirements

The README must explicitly show:

- the entity name
- the command-side operation
- the query-side operation
- example commands for both paths
- what makes the command side different from the query side
- why this sample is different from `02-crud`

If the sample uses namespaced runtime parameters, document:

- `cncf.*` for framework/runtime parameters
- `query.*` for query controls
- unprefixed parameters for domain attributes

## Pass Conditions

This task is complete only if all of the following are true:

- the write command works
- the read query works
- the runtime difference between command side and query side is visible
- the README matches the real commands
- [`docs/phase/samples/06-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/06-cqrs.md) is updated honestly

## Notes For Execution

Make the difference in runtime behavior obvious.
Avoid hiding CQRS behind too much supporting detail.
