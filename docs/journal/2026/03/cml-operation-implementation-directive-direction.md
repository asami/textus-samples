# CML Operation Implementation Directive Direction

## Intent

`EXECUTION` is not enough for model-driven samples.

Many operations are typical enough that the implementation shape should be
selectable directly from CML, instead of falling back to hand-written hooks or
`uowmNotImplemented`.

The direction is to add an `IMPLEMENTATION` directive alongside `EXECUTION`.

## Separation

- `EXECUTION`
  - how the operation runs
  - examples: `sync`, default async/job-backed command path
- `IMPLEMENTATION`
  - how the operation body is generated
  - examples: `echo-record`, `entity-load`, `entity-search`

These are different concerns and should stay separate.

## First Minimal Implementation

The first supported implementation kind is:

- `echo-record`

Meaning:

- for a command operation
- generate a body that returns `action.request.toRecord`
- let the normal command/job lifecycle wrap that result

This is useful for:

- `05-job`
- minimal demos
- testing command/job paths without custom repository logic

## Candidate Implementation Kinds

- `echo-record`
- `entity-create`
- `entity-load`
- `entity-search`
- `event-emit`
- `event-effect-record`
- `event-effect-load`

## Current Status

Implemented:

- parse `IMPLEMENTATION` in CML
- propagate it through model/generator metadata
- support `IMPLEMENTATION = echo-record`
- support event-oriented directives through runtime built-ins:
  - `event-emit`
  - `event-effect-record`
  - `event-effect-load`
- support generator bodies for typical entity/view/query patterns:
  - `entity-create`
  - `entity-load`
  - `entity-search`
  - `aggregate-load`
  - `aggregate-search`
  - `view-load`
  - `view-search`

This is enough to make the generated `05-job` command body executable without a
sample-local handwritten action body, and to let `04-event-driven` bind its
event-oriented operation meanings from CML instead of handwritten hooks.
