# 06-job Development Instruction

## Goal

Create the first minimal sample focused on CNCF job management.

This sample should explain and demonstrate:

- one command that creates a job
- one route to read job status or result
- one route to inspect job timeline or debug information

It is acceptable for this sample to stay smaller than the later event-trace lab.
The point of `06-job` is to establish the job-management surface first.

## Why This Sample Exists

`04-cqrs` already shows a job-backed command.
`05-event-driven` already shows an event-triggered reaction.

What is still missing is a sample dedicated to:

- job submission
- job state observation
- job timeline / causation observation

This sample should provide that base before adding a later lab that traces event-triggered follow-up actions from the job side.

## Completion Line

The first completion line for `06-job` is:

1. one command creates a job-backed execution
2. one read path shows the job status or job result
3. one read path shows timeline/debug information for the same job

## Scope

Keep the first version small.

Preferred scope:

- model-driven sample, if the existing `04-cqrs` pattern is enough
- command-first usage
- explicit job query commands in the README

Avoid:

- distributed execution
- docker-based infrastructure
- retry orchestration in the first version
- external broker or queue setup

## Recommended Runtime Story

The sample should let the learner do this:

1. run one command that returns a job id
2. use that job id to query job status
3. use that job id to query job result and/or timeline

If the runtime already provides separate surfaces for:

- status
- result
- timeline
- debug

use those directly.

## Relationship To Later Labs

This sample is the base job-management sample.

A later lab may extend it to show:

- event-triggered follow-up actions
- causation chain
- parent/child or related-job tracing

That later lab should not be forced into the first `06-job` completion line.

## Deliverables

- `samples/06-job/README.md`
- `docs/phase/samples/06-job.md`
- sample implementation files under `samples/06-job/`
- implementation record once real runtime verification exists

## Acceptance Criteria

- build succeeds
- one command returns a job-shaped response
- one job query route succeeds
- one timeline/debug route succeeds
- README matches the actual commands and observed output
