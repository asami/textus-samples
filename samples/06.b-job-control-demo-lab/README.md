# 06.b-job-control-demo-lab

## Overview

This is the advanced, direct-framework companion to `06.a-job-control-lab`.

It is intentionally **not** the builtin external-API sample.
Instead, it demonstrates the CNCF runtime by calling the framework APIs directly:

- `component.logic.submitJob(...)`
- `component.logic.controlJob(...)`
- `component.jobEngine.query(...)`
- `component.jobEngine.queryTimeline(...)`
- `component.eventStore.query(...)`

## What It Shows

- one submitted job
- one suspend/resume sequence
- one cancel sequence
- one direct job history observation route
- one direct lifecycle event observation route

## How To Run

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Run the demo:

```bash
bash run.sh
```

The demo prints one JSON line containing:

- the suspended/resumed job id and final status
- the cancelled job id and final status
- the job history kinds that show the control effect
- the lifecycle event names that show the control effect

## Difference From 05.a

`06.a-job-control-lab` is the builtin-component sample.

`06.b-job-control-demo-lab` is the direct framework demo:

- it uses direct framework APIs
- it is meant for lower-level exploration
- it is not the mainline builtin API lab

## Status

This sample is the direct-framework companion lab.

## Verified Run

The direct demo is verified with:

```bash
bash run.sh
```

The run prints one JSON line that shows:

- suspend/resume ends in `Succeeded`
- cancel ends in `Cancelled`
- job history is observed through `component.jobEngine.queryTimeline(...)`
- lifecycle events are observed through `component.eventStore.query(...)`
