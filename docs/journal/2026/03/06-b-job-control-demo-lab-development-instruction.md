# 06.b-job-control-demo-lab Development Instruction

## Goal

Create the advanced lab that demonstrates direct framework-facing job-control APIs.

## Position

- `06.a-job-control-lab`
  - builtin `JobControl` component as the mainline sample
- `06.b-job-control-demo-lab`
  - direct framework API demo
  - lower-level exploration for:
    - `component.logic.submitJob(...)`
    - `component.logic.controlJob(...)`
    - `component.jobEngine.query(...)`
    - `component.eventStore.query(...)`

## First Completion Line

The lab is complete at the first line when:

1. one generated command is turned into a controllable job through direct framework API usage
2. cancel, suspend, and resume are all demonstrated
3. lifecycle events are read directly from the event store
4. job history is read directly from the job query/timeline API

## Scope

- local runtime only
- framework-facing demo
- allowed to use a thin Scala runner
- no external infrastructure
- no distributed scheduler or broker

## Note

This lab is the right home for the current `JobControlDemo` style.

It should not be treated as the mainline `05.a` sample shape.
