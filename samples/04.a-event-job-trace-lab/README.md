# 04.a-event-job-trace-lab

## Overview

This is the bridge lab between `04-event-driven` and `05-job`.

It is still event-driven, but it adds a job/history/event trace so the learner can inspect the reaction path.

This is not a distributed messaging lab and not a job-management sample.

## What It Shows

- one action emits an event
- one event-triggered reaction is observed
- one job/history/event route shows the progression

## Model

- component: `event-driven`
- service: `Event`
- emitting command: `emitEvent`
- reaction action: `recordEffect`
- observation query: `loadEffect`
- emitted event: `item.changed`

## How It Works

- `emitEvent` emits `item.changed`
- CNCF event reception dispatches `recordEffect`
- the runner observes the result through:
  - `component.jobEngine.query(...)`
  - `component.jobEngine.queryTimeline(...)`
  - `component.eventStore.query(...)`
  - `loadEffect`

## How It Differs From `04-event-driven`

- `04-event-driven` focuses on event emission and visible post-event effect
- `04.a-event-job-trace-lab` focuses on tracing the same reaction through job/history/event observation

## How To Run

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Run the demo:

```bash
bash run-demo.sh
```

The demo prints one JSON line containing:

- the emitted event name
- the triggered reaction
- the job status
- the job history kinds
- the event names observed from the event store
- the visible effect payload

Observed trace:

- `emitEvent` runs first and emits `item.changed`
- `recordEffect` is triggered by CNCF event reception
- the job history shows `job.submitted`, `job.running`, `task.running`, `task.succeeded`, `job.succeeded`
- the event store query shows the event names and the recorded visible effect payload

## Status

This lab is the first event-job trace bridge sample.
