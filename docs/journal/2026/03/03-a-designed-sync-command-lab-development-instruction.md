# 03.a-designed-sync-command-lab Development Instruction

Status: `Active Instruction`

This is the active development work-order document for `03.a-designed-sync-command-lab`.
Do not rewrite this file into a result note or completion report.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/03.a-designed-sync-command-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/03.a-designed-sync-command-lab.md)

## Purpose

Build `03.a` as the first lab that shows a command which is designed to run synchronously.

This lab must stay separate from test-only synchronous execution.

It must show:

- a command is still mutable
- the command is intentionally designed for synchronous execution
- the immediate result is part of the application design, not only a test override

## Scope

Keep `03.a` narrowly focused.

Do not add test-only sync override behavior here.
Do not mix this lab with config-driven execution-mode switching.
Do not turn it into an event-sourcing or distributed sample.

This lab should answer one question:

- how does CNCF express a command that is designed to execute synchronously as part of the application contract?

## Expected Shape

Use the same model-driven direction as `03-cqrs`.

The synchronous behavior must be expressed in the model/CML layer.
Do not treat a hand-written Scala override as the final answer.

Recommended conceptual structure:

- one component
- one mutable command target
- that command is explicitly synchronous by design in CML
- one immediate result that proves the caller receives the outcome directly

## Required Outcome

The lab must provide:

- one designed-sync command target
- one runtime example that returns an immediate command result
- README text that explains why this is design-time sync, not test-time sync

## Relationship To 03-cqrs

`03-cqrs` shows the default CQRS shape:

- write path is job-backed
- read path is immediate

`03.a` must add a different point:

- some commands are intentionally synchronous by design
- that decision belongs to the command definition / command directive side

## Relationship To Future 03.b

`03.a` is not the place for:

- running an async command synchronously only for tests
- config-based execution override
- local/debug synchronization of an otherwise async command

Those belong to a separate follow-up lab.

## README Requirements

The README must explicitly explain:

- what the command target is
- why it is synchronous by design
- which kinds of use cases fit this style
- how it differs from `03-cqrs`
- that test-only sync execution is out of scope here

## Pass Conditions

This task is complete only if all of the following are true:

- the designed-sync command is implemented
- the synchronous behavior is expressed in CML/model metadata
- help/runtime confirmation succeeds
- the command returns an immediate result
- the README matches the real commands
- the phase checklist is updated honestly
