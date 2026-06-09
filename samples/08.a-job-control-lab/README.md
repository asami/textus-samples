# 06.a-job-control-lab

`08.a-job-control-lab` is the control-oriented companion to `08-job`.

- [06-job] focuses on observing submitted jobs
- `08.a-job-control-lab` focuses on controlling a running job

This sample shows how CNCF exposes job control as a shell-first operational surface:

- submit a control-ready job
- suspend the job
- resume the job
- cancel another job
- inspect job history
- inspect job-related events

The important distinction is that this lab is not about ordinary async commands in general. It is about the subset of jobs that must remain controllable through `job-control.job-admin.*`.

## CNCF Approach

In CNCF, asynchronous execution is the default command model because it supports:

- scalability through decoupled write execution
- operational traceability through job management
- eventual consistency between command completion and read-side observation

But once asynchronous execution becomes a managed runtime concern, control APIs become equally important.

This sample shows that second step:

- `08-job`
  - observe jobs
- `08.a-job-control-lab`
  - control jobs

## Intended Use Case

Use this pattern when you need to:

- pause a long-running job for operator intervention
- resume a paused job after validation
- cancel a running job safely
- inspect job history after a control action
- correlate job lifecycle with event-side traces

Typical cases are:

- back-office operations
- admin consoles
- workflow supervision
- controlled batch execution
- support and troubleshooting tools

## Files

- `src/main/cozy/job-control-lab.cml`
- `src/main/scala/org/sample/jobcontrol/impl/JobControlLabComponentFactory.scala`
- `build.sbt`
- `run.sh`

## Setup

### Prepare the cozy command

```bash
$ cd samples/08.a-job-control-lab
$ ../../bin/setup cozy
```

This prepares the local `cozy` launcher used by `sbt-cozy`.

### Build the generated sample

```bash
$ cd samples/08.a-job-control-lab
$ sbt --batch clean compile
```

This generates the sample component from `job-control-lab.cml` and compiles the sample-specific impl factory.

## Run The Whole Scenario

```bash
$ cd samples/08.a-job-control-lab
$ bash run.sh
```

`run.sh` is the batch form of the walkthrough below. Read `Command Walkthrough` first if you want to understand each step.

## Command Walkthrough

The commands below use these common tokens:

- `cncf`
  - the standard CNCF CLI entry point
  - in this repo it is invoked as `cncf dev`
- `--project-dev .` auto activation
  - loads generated sample classes from the local `target` tree
- `server`
  - starts the sample runtime as an HTTP server
- `client`
  - calls the running server through the client path
- `command`
  - executes a local one-shot command without starting the server/client pair

### 1. Start the server

```bash
$ cncf dev server --project-dev .
```

This starts the runtime that will host the controllable jobs.

Parameters:
- `server`
  - starts the runtime in server mode for this sample

### 2. Inspect the control-ready submit operation

```bash
$ cncf dev command --project-dev . help job-control-lab.item.create-item
```

This shows the sample-specific submit entry point that returns a controllable `job_id`.

Parameters:
- `command`
  - runs a one-shot local command for help inspection
- `help`
  - requests help for the selector that follows
- `job-control-lab.item.create-item`
  - selects the control-ready job submission operation

### 3. Submit a controllable job

```bash
$ cncf dev client --project-dev . job-control-lab.item.create-item --name quick --title Quick
```

Example result:

```yaml
job_id: cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
```

Parameters:
- `client`
  - sends the request to the running server
- `job-control-lab.item.create-item`
  - submits a job that remains controllable through `job-control.job-admin.*`
- `--name quick`
  - sample name carried into the created item payload
- `--title Quick`
  - sample title carried into the created item payload

### 4. Suspend the running job

```bash
$ cncf dev client --project-dev . job-control.job-admin.suspend-job --id cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76 --privilege content_admin
```

Example result:

```yaml
job_id: cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
status: Suspended
async: true
response: ""
```

Parameters:
- `client`
  - sends the control request to the running server
- `job-control.job-admin.suspend-job`
  - requests suspension of the target job
- `--id ...`
  - the job id returned by the previous submit step
- `--privilege content_admin`
  - grants the admin capability required by `job_admin`

### 5. Inspect the current job status

```bash
$ cncf dev client --project-dev . job-control.job.get-job-status --id cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
```

This confirms that the job moved through `Suspended` and can later return to `Running`.

Parameters:
- `client`
  - sends the read request to the running server
- `job-control.job.get-job-status`
  - loads the current runtime status of the target job
- `--id ...`
  - the target job id

### 6. Resume the suspended job

```bash
$ cncf dev client --project-dev . job-control.job-admin.resume-job --id cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76 --privilege content_admin
```

Example result:

```yaml
job_id: cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
status: Running
async: true
response: ""
```

Parameters:
- `client`
  - sends the control request to the running server
- `job-control.job-admin.resume-job`
  - resumes a previously suspended job
- `--id ...`
  - the target job id
- `--privilege content_admin`
  - the required admin privilege

### 7. Await the final result

```bash
$ cncf dev client --project-dev . job-control.job.await-job-result --id cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
```

Example result:

```yaml
id: major-minor-entity-item-1775523977850-3mtkL2XHjL3IKjAThusWOb
```

Parameters:
- `client`
  - sends the request to the running server
- `job-control.job.await-job-result`
  - waits until the controlled job finishes
- `--id ...`
  - the target job id

### 8. Load the job history

```bash
$ cncf dev client --project-dev . job-control.job.load-job-history --id cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
```

Example result:

```yaml
job_id: cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
events:
  - kind: job.submitted
  - kind: job.running
  - kind: task.running
  - kind: job.suspended
  - kind: job.resumed
  - kind: task.succeeded
  - kind: job.succeeded
```

Parameters:
- `client`
  - sends the request to the running server
- `job-control.job.load-job-history`
  - loads the job-side lifecycle history
- `--id ...`
  - the target job id

### 9. Load job-related events

```bash
$ cncf dev command --project-dev . event.event-admin.load-job-events --id cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76 --privilege content_admin
```

Example result:

```yaml
job_id: cncf-job-job-1775523947840-3JgfELQBaYtc3yhMeTZs76
events: []
```

Parameters:
- `command`
  - runs a local one-shot admin query
- `event.event-admin.load-job-events`
  - loads event-side traces correlated with the target job id
- `--id ...`
  - the target job id
- `--privilege content_admin`
  - the admin privilege required by `event_admin`

### 10. Submit and cancel another job

```bash
$ cncf dev client --project-dev . job-control-lab.item.create-item --name cancel --title Cancel
$ cncf dev client --project-dev . job-control.job-admin.cancel-job --id cncf-job-job-1775524097115-1otiOMA68eDfhpzSal9CAi --privilege content_admin
```

Example cancel result:

```yaml
job_id: cncf-job-job-1775524097115-1otiOMA68eDfhpzSal9CAi
status: Cancelled
async: true
response: ""
```

This completes the second control branch and gives you a `job.cancelled` history path distinct from the suspend/resume branch.

## Difference From 06-job

- [06-job] focuses on job observation
  - submit
  - await
  - get-result
  - get-status
  - load-history
- `08.a-job-control-lab` focuses on job control
  - suspend
  - resume
  - cancel
  - control-aware history inspection
  - event-side inspection
