# 06.a-job-control-lab Builtin Direction

## Decision

`06.a-job-control-lab` should be based on a builtin `JobControl` component, not on direct framework API calls from a demo runner.

## Why

The current `JobControlDemo` is useful for framework-facing exploration, but it is not the right mainline sample shape for `05.a`.

`05.a` should teach the external job-control surface:

- submit-originated job control
- cancel
- suspend
- resume
- lifecycle event observation
- job history observation

That means the control and observation routes should be exposed as builtin API, not only as direct calls such as:

- `component.logic.controlJob(...)`
- `component.jobEngine.query(...)`
- `component.eventStore.query(...)`

The remaining event observation route should move to a builtin `event` component with:

- `event`
- `event-admin`

## Target Shape

### Mainline sample

`06.a-job-control-lab`

- uses builtin `JobControl` component
- uses external command/API routes
- demonstrates application-facing and admin-facing job control surfaces

### Advanced lab

The current `JobControlDemo` style should be moved to a separate advanced lab.

It is useful as a lower-level framework demo because it shows:

- direct `submitJob`
- direct `controlJob`
- direct `jobEngine.query`
- direct `eventStore.query`

But that belongs in a framework-facing lab, not in the main `05.a` sample.

## Service Direction

Builtin `JobControl` should eventually expose at least two services:

- application-facing job service
  - status
  - result
  - history read
- admin-facing job-control service
  - cancel
  - suspend
  - resume
  - timeline/history/event read

Builtin `event` should expose at least two services:

- application-facing `event`
- admin-facing `event-admin`

## Consequence For 05.a

`05.a` should be treated as not-final until builtin `JobControl` exists and the sample is rewritten to use it.

The current demo can remain as a temporary prototype, but it should not be the authority for sample completion.
