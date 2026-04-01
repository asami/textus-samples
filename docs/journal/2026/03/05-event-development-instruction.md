# 04-event Development Instruction

Status: Active Instruction

## Purpose

Define the first completion line for `04-event`.

This sample should introduce the minimum event-oriented flow after:

- `02` CRUD
- `03` CQRS

The focus is not distributed messaging. The focus is:

- event definition
- event dispatch/reception
- observing how CNCF handles an event-driven path

## Scope

The first version of `04-event` should remain small.

It should show:

1. one event-producing action
2. one event reception path
3. one visible effect caused by the event

The visible effect may be:

- a record update
- a projection update
- a simple status/result change

## What 04-event Must Show

`04-event` should make these differences clear:

- `04-cqrs`
  - command and query paths are explicit request/response paths
- `04-event`
  - an event is emitted
  - another part of the runtime reacts to it

The sample must make the reaction observable.

## Recommended First Shape

Use a small item-oriented example.

Example direction:

- command creates or updates an item
- that command emits an event
- an event reception updates a simple read/projection/status surface

Keep the domain tiny. Do not broaden to full workflow or distributed bus logic.

## Constraints

- keep the sample model-driven when possible
- avoid hand-written repository logic
- avoid external infrastructure dependencies
- do not turn this into a distributed messaging sample
- do not require a database

## README Requirements

The README must explain:

- what event is emitted
- what receives it
- what observable result proves reception happened
- how this differs from plain CQRS

It should include:

- build/generate commands
- runtime help commands
- one command or trigger that emits the event
- one command or query that shows the post-event effect

## Completion Criteria

`04-event` can be considered done when:

- build succeeds
- event-related help paths resolve
- the event-producing path succeeds
- the event reception effect is observable
- README matches the actual runtime behavior

## Notes

- Do not introduce `03.b`-style execution-mode concerns here unless they are directly necessary.
- Keep `textus.*`, `query.*`, and unprefixed domain parameter rules consistent with the existing samples.
