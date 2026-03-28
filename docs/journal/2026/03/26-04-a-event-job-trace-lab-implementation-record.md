# 04.a-event-job-trace-lab Implementation Record

## What Was Built

- `04.a-event-job-trace-lab` was added as the bridge sample between `04-event-driven` and `05-job`.
- The sample reuses the `event-driven` model and adds direct job/history/event inspection in the runner.

## Observed Flow

- `emitEvent` emits `item.changed`.
- CNCF event reception triggers `recordEffect`.
- The runner observes the progression through:
  - `component.jobEngine.query(...)`
  - `component.jobEngine.queryTimeline(...)`
  - `component.eventStore.query(...)`
  - `loadEffect`

## Verification

- `sbt cozyGenerate` succeeded.
- `sbt compile` succeeded.
- `bash run-demo.sh` succeeded.

## Observed Results

- The emitted event was `item.changed`.
- The triggered reaction was `recordEffect`.
- The job status ended as `Succeeded`.
- The job history showed the expected progression.
- The event store showed the event names and visible effect payload.
