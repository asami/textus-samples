# 04.a-event-job-trace-lab Development Instruction

## Goal

Add a follow-up lab after `04-event-driven` that uses the job surface to inspect how an event-triggered reaction progressed.

This lab is intentionally a bridge between:

- `04-event-driven`
- `05-job`
- `05.a-job-control-lab`

## What It Should Show

The lab should show one concrete flow:

1. one operation emits an event
2. CNCF event reception starts a follow-up action
3. the reaction is visible through job-oriented observation

The point is not only that the effect happened.

The point is that the user can inspect the reaction path through job/history/event observation.

## Mainline Story

`04-event-driven` proves:

- event emission
- event reception
- visible effect

`04.a-event-job-trace-lab` should prove:

- the same style of event-driven reaction can be inspected from the job side

This is the observability-oriented follow-up to `04-event-driven`.

## Required Runtime Shape

The lab should aim to demonstrate:

- one emitted event
- one triggered action
- one job or job-like execution trace that can be queried
- one history or event observation path that shows the sequence

## Preferred Observation Routes

Prefer builtin external routes if already available:

- builtin `job-control`
- builtin `event`

If the exact reaction is easier to show with a small local demo runner, keep that runner thin and use it only to orchestrate existing framework capabilities.

## Do Not

- Do not turn this into a distributed messaging lab
- Do not add custom repository logic
- Do not redesign event delivery for this sample
- Do not bypass the sample purpose by showing only a final effect without trace/observation

## Completion Line

This lab is complete when all of the following are true:

- one event-producing operation is executed
- one event-triggered reaction is observed
- one job/history/event route shows the progression
- the README explains what to run and what proves the trace
