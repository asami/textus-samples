# 06.a-job-control-lab

## Overview

This lab is the first small job-control lab after `06-job`.

It stays command-first and local, but it focuses on control actions after a job has started:

- submit a job
- suspend the job
- resume the job
- cancel the job
- observe the resulting lifecycle events and job history changes

The sample keeps the model-driven Cozy/CML direction from `06-job` for help/runtime shape, while the runner demonstrates control behavior through the builtin `job_control` external API.

At the current stage:

- control routes use builtin `job_control`
- job/history reads use builtin `job_control`
- lifecycle event observation uses builtin `event`

## What It Shows

- one submitted job
- one suspend/resume sequence
- one cancel sequence
- one lifecycle event observation route
- one job history observation route

## How To Run

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Help checks:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-control-lab"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-control-lab.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-control-lab.item.create-item"
```

Run the demo:

```bash
bash run.sh
```

The demo prints one JSON line containing:

- the suspended/resumed job id and final status
- the cancelled job id and final status
- the lifecycle event names that show the control effect
- the job history kinds that show the control effect

## Control Flow

The demo route is `bash run.sh`.

Inside the runner it:

1. submits a job
2. suspends it while it is running
3. resumes it
4. submits a second job
5. cancels the second job
6. calls `job_control.job.load_job_history` to show the history effects
7. calls `event.event_admin.load_job_events` to show the lifecycle event effects

## Difference From 06-job

`06-job` focuses on submission, status/result, and timeline/debug observation.

`06.a-job-control-lab` adds explicit control of a running job through the builtin `JobControl` component:

- suspend
- resume
- cancel
- lifecycle event observation
- job history observation

It is not a redesign of job control and it does not add distributed infrastructure.
