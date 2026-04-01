# 06.b-job-control-demo-lab Implementation Record

## What Was Built

- `06.b-job-control-demo-lab` was added as the advanced direct-framework companion to `06.a-job-control-lab`.
- The runner uses direct framework APIs rather than builtin external API routes.

## Direct APIs Demonstrated

- `component.logic.submitJob(...)`
- `component.logic.controlJob(...)`
- `component.jobEngine.query(...)`
- `component.jobEngine.queryTimeline(...)`
- `component.eventStore.query(...)`

## Verification

- `sbt cozyGenerate` succeeded.
- `sbt compile` succeeded.
- `bash run.sh` succeeded.
- The demo observed:
  - suspend/resume
  - cancel
  - job history
  - lifecycle events

## Observed Results

- Suspend/resume job status ended as `Succeeded`.
- Cancel job status ended as `Cancelled`.
- Job history included the expected lifecycle transitions.
- Event observation included the expected job lifecycle events.
