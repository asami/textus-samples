# CML Command Execution Directive Direction

## Status

Design note.

## Problem

`04.a-designed-sync-command-lab` currently proves that a command can return an
immediate result, but the synchronous behavior is implemented in hand-written
Scala code.

That is not the right long-term direction.

If a command is synchronous by design, that fact must belong to the model.

Otherwise the sync behavior is:

- hidden in implementation code
- hard to inspect from the model
- hard to generate consistently
- easy to confuse with test-only sync execution

## Direction

Execution style for commands should be declared at the CML operation level.

This is separate from:

- command vs query responsibility
- test-only execution overrides
- runtime parameter overrides

The model should be able to say:

- this operation is a mutable command
- this command is synchronous by design

## Required Distinction

There are two different concepts:

1. Designed sync command
- part of the application contract
- should be visible in CML
- should be preserved by generation/runtime

2. Test-only sync execution
- execution override for local/test/debug use
- should stay outside the model
- should be controlled by runtime/config

These must not be merged.

## Proposed Model Shape

At the CML operation definition level, add an execution directive.

Minimal conceptual shape:

- `TYPE = COMMAND`
- `EXECUTION = sync`

or, if the repo prefers the older wording:

- `TYPE = COMMAND`
- `DIRECTIVE = sync`

The important point is not the exact keyword.
The important point is that the model explicitly carries the execution contract.

## Expected Semantics

If an operation is:

- `TYPE = COMMAND`
- `EXECUTION = sync`

then runtime behavior should be:

- still treated as a command
- still understood as mutable
- returns the result immediately
- does not default to job-id response

If an operation is:

- `TYPE = COMMAND`
- `EXECUTION = async`

or no explicit sync directive is present, then runtime behavior should remain
the standard job-backed command path used by `04-cqrs`.

## Generator Responsibility

The generator should:

- read the execution directive from CML
- emit operation/runtime metadata that preserves it
- avoid requiring hand-written override components just to get sync behavior

## CNCF Responsibility

The runtime should:

- read generated operation execution metadata
- choose the correct command execution path
- keep the result shape aligned with the execution directive

## 03.a Implication

`04.a-designed-sync-command-lab` should only be considered complete when:

- the command sync behavior is expressed in CML
- the generated/runtime surface preserves that behavior
- the hand-written `DesignedSyncComponent` hook is no longer needed

Until then, the current sample is only a prototype.
