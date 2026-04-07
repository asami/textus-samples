# 02-crud Development Instruction

Status: `Active Instruction`

This is the active development work-order document for `02-crud`.
Do not rewrite this file into a result note or completion report.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md)

## Purpose

Build `02-crud` as the first sample that moves beyond a single hello-style operation into a small but real data lifecycle.

This sample must demonstrate:

- one entity type
- minimal repository behavior
- command/query separation at a simple level
- a synchronous CRUD-oriented workflow

## Scope

Keep `02-crud` deliberately small.

Do not build a full domain model.
Do not introduce CQRS/event-driven/distributed concerns here.

This sample should answer one question:

- how does a minimal CNCF Component expose create/read/list behavior around one entity?

## Required Operations

The sample must provide these operations:

- `createItem`
- `getItem`
- `listItems`

These are the minimum required surface for `02-crud`.

Optional update/delete operations may be added later, but they are not required for the first completed version.

## Expected Behavior

The sample should define one simple entity, called `Item`.

Minimum behavior:

- `createItem`
  - accepts the minimum fields needed to create one item
  - returns the created item or created-id information
- `getItem`
  - retrieves one item by id
- `listItems`
  - returns all created items in insertion order or stable order

The sample must be easy to run locally and easy to inspect through command mode.

## Recommended Shape

Use a single Component with a simple in-memory repository first.

Recommended conceptual structure:

- entity model: `Item`
- repository: in-memory storage
- commands:
  - `createItem`
- queries:
  - `getItem`
  - `listItems`

This sample is about behavior and structure, not persistence technology.

## Mini-Low Rules

If a smaller model is assigned this work, follow only this process:

1. read the sample README
2. read the phase checklist
3. implement exactly the three required operations
4. keep storage in memory
5. update the README to match actual commands
6. stop

Do not add extra architecture.
Do not jump to CQRS/event sourcing/distribution.

## Files To Update

At minimum, update these files:

- [`samples/04-crud/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md)
- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md)
- the actual sample implementation files under [`samples/04-crud`](/Users/asami/src/dev2026/cncf-samples/samples/04-crud)

## README Requirements

The README must explicitly show:

- the entity name
- the three required operations
- example commands for:
  - create
  - get
  - list
- the fact that this sample uses simple in-memory storage
- the fact that this sample is intentionally simpler than later samples

## Pass Conditions

This task is complete only if all of the following are true:

- `createItem` works
- `getItem` works
- `listItems` works
- the sample is runnable locally
- the README matches the real commands
- [`docs/phase/samples/04-crud.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md) is updated honestly

## Notes For Execution

Prefer one small coherent example over a broad feature set.
Make the command surface easy to read.
