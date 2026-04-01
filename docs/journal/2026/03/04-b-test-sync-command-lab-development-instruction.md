# 04.b-test-sync-command-lab Development Instruction

Status: `Active Instruction`

This is the active development work-order document for `04.b-test-sync-command-lab`.
Do not rewrite this file into a result note or completion report.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/04.b-test-sync-command-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04.b-test-sync-command-lab.md)

## Purpose

Build `03.b` as the lab that shows a command whose normal application contract is async/job-backed,
but whose execution can be forced into a synchronous path for test/local/debug use.

This lab must stay separate from `03.a`.

It must show:

- the command semantics remain mutable and command-oriented
- the command is not synchronous by design
- the synchronous behavior comes from runtime override, config, or execution mode
- the override is useful for tests, local verification, or debugging

## Scope

Keep `03.b` narrowly focused.

Do not redesign the command contract itself.
Do not change the model so the command becomes designed-sync.
Do not turn this into event sourcing, distributed execution, or persistence comparison.

This lab should answer one question:

- how can CNCF run an otherwise async/job-backed command synchronously for tests or local verification?

## Expected Shape

Use `04-cqrs` as the base.

The command target should remain the same command-style target as in `04-cqrs`.

The lab must compare at least these two execution shapes:

- default command execution
  - returns a job id or job-shaped response
- test/local sync override
  - returns the command result immediately

The override must come from runtime/config/parameter behavior, not from CML execution metadata.

## Required Outcome

The lab must provide:

- one command target that is async/job-backed by default
- one explicit way to force synchronous execution for test/local use
- one runtime example for each mode
- README text that explains why this is test-time sync, not design-time sync

## Relationship To 04-cqrs

`04-cqrs` shows the default CQRS shape:

- write path is job-backed
- read path is immediate

`03.b` must keep that default shape intact.

It must add one new point:

- runtime execution mode can be overridden for test/local/debug purposes

## Relationship To 03.a

`03.a` is about command semantics.

`03.b` is about runtime execution override.

Therefore `03.b` is not the place for:

- `EXECUTION=sync` in CML
- operation directive changes
- command contracts that are intentionally synchronous in production

`03.b` is specifically about:

- production async, test sync
- local verification
- debug convenience
- immediate observation of a command result without changing the command's design intent

## README Requirements

The README must explicitly explain:

- which command target is used
- what the default execution result looks like
- what the overridden synchronous execution result looks like
- what mechanism enables the override
- why this is different from `03.a`
- which use cases fit this lab

If runtime parameters are used, document:

- `cncf.*` for framework/runtime parameters
- `query.*` for query controls
- unprefixed parameters for domain attributes

## Pass Conditions

This task is complete only if all of the following are true:

- the default command path remains async/job-backed
- the same command can be executed synchronously through an explicit runtime override
- the README clearly distinguishes `03.a` and `03.b`
- help/runtime confirmation succeeds
- the phase checklist is updated honestly
