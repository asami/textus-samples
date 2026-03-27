# 05-job

## Overview

This sample is the first dedicated job-management sample after `04-event-driven`.

Its purpose is to make the CNCF job surface explicit:

- one command creates a job
- one route reads job status or result
- one route reads timeline or debug information for that same job

This sample is the base for later tracing work.
It should make job observation clear before introducing an event-to-job trace lab.

## Target Runtime Story

The learner should be able to do this:

1. execute one command that returns a job id
2. query the job status
3. query the job result or job timeline/debug information

## Scope

For the first implementation, keep the sample small:

- command-first
- local runtime only
- no external infrastructure
- no distributed queue or broker setup

## Relationship To Earlier Samples

- `03-cqrs`
  - already shows a job-backed command
  - but job management itself is not the main focus
- `04-event-driven`
  - already shows an emitted event and a follow-up action
  - but not a dedicated job-tracing workflow
- `05-job`
  - focuses directly on job submission, status, result, and timeline/debug observation

## How To Run

Build/generate:

```bash
sbt cozyGenerate
sbt clean compile
```

Run the demo:

```bash
./run.sh
```

The demo does this in one JVM:

1. create a generated action for `JobSample.Item.createItem`
2. submit it through the component job path
3. capture the returned job id
4. wait for job completion
5. query the same job through `jobEngine.query(jobId)`
6. print status, result-summary, task status, timeline kinds, and debug summary

Help checks:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-sample"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-sample.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-sample.item.create-item"
```

## Expected Learnings

- how a command becomes a job-backed execution
- how to retrieve job status
- how to inspect job result
- how to inspect timeline or debug information for the same job

## Model

- component: `job-sample`
- service: `item`
- command target: `job-sample.item.create-item`
- implementation directive: `IMPLEMENTATION = echo-record`
- sample runner: `org.sample.job.JobFlowDemo`

## Expected Output Shape

The demo prints one JSON line such as:

```json
{
  "job-id": "cncf-job-...",
  "status": "Succeeded",
  "result-success": true,
  "task-statuses": "[Succeeded]",
  "timeline-kinds": "[job.submitted, job.running, task.started, task.succeeded, job.succeeded]",
  "debug-request-summary": "JobSample.Item.createItem"
}
```

## Status

This sample is implemented at the first completion line.

The active work order is:

- [05-job Development Instruction](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/05-job-development-instruction.md)
