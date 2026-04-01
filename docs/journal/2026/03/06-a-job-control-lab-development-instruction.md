# 06.a-job-control-lab Development Instruction

## Goal

Create the first dedicated lab for CNCF job control after `06-job`.

This lab should make the external control surface explicit:

- cancel
- suspend
- resume

## Position

- `06-job`
  - shows job creation, status/result read, and timeline/debug observation
- `06.a-job-control-lab`
  - shows how a running or controllable job can be managed after submission through builtin job-control APIs
- lower-level demo
  - direct framework API usage such as `component.logic.controlJob(...)`
  - should live in a separate advanced lab, not in `05.a`

## First Completion Line

The lab is complete at the first line when all of these are true:

1. one job-backed command is created
2. one builtin route can cancel that job
3. one builtin route can suspend and resume that job
4. one builtin observation route shows lifecycle events
5. one builtin observation route shows job history

## Scope

Keep it small:

- local runtime only
- command-first
- no external infrastructure
- no distributed scheduler or broker
- no sample-local repository workaround

## Preferred Shape

- use the same model-driven direction as `06-job` when possible
- prefer a builtin `JobControl` component and its external routes
- do not treat direct framework API calls as the finished sample shape
- if a local demo runner is still needed during transition, treat it as prototype-only

## What To Show

The learner should be able to see:

1. a submitted job id
2. a control action against that job
3. a lifecycle event that proves the control action took effect
4. a job history entry that proves the control action took effect

## Required README Content

The README must explain:

- what command creates the job
- what builtin route performs cancel
- what builtin route performs suspend
- what builtin route performs resume
- what builtin route proves lifecycle event observation
- what builtin route proves job history observation
- how this differs from `06-job`
- how this differs from the lower-level framework-facing demo lab

## Checklist Direction

Use a separate phase checklist for `06.a-job-control-lab`.

Do not expand `06-job` itself beyond its current completion line.

Do not use the current `JobControlDemo` as the final authority for `05.a`.
