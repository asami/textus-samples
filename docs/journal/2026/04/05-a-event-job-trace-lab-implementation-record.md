# 05.a-event-job-trace-lab Implementation Record

## Summary

`05.a-event-job-trace-lab` was normalized as the shell-first bridge between the event sample and the job-control samples.

The sample now focuses on:

- emitting one event through a modeled command
- observing the routed reaction through `job-control`
- confirming the visible effect through the event-facing query

## What Changed

- removed the sample-local same-JVM trace demo main
- removed `run-demo.sh`
- rewrote the README as a shell-first event/job trace sample
- added matching `cozy` scripted verification for the same shell-visible trace flow
- updated `run.sh` to cover:
  - event help
  - job-control help
  - metadata describe
  - server/client emit
  - await-job-result
  - get-job-status
  - load-job-history
  - load-effect
- corrected the sample build name to `05-a`

## Verified Commands

- `../../bin/setup cozy`
- `sbt --batch clean compile`
- `bash ../../bin/cncf --discover=classes command help event-driven.event.emit-event`
- `bash ../../bin/cncf --discover=classes command help job-control.job.await-job-result`
- `bash ../../bin/cncf --discover=classes command help job-control.job.load-job-history`
- `bash ../../bin/cncf --discover=classes command event-driven.meta.describe --format yaml`
- `bash ../../bin/cncf --discover=classes server`
- `bash ../../bin/cncf --discover=classes client event-driven.event.emit-event --name alpha --title Alpha`
- `bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id <job-id>`
- `bash ../../bin/cncf --discover=classes client job-control.job.get-job-status --id <job-id>`
- `bash ../../bin/cncf --discover=classes client job-control.job.load-job-history --id <job-id>`
- `bash ../../bin/cncf --discover=classes client event-driven.event.load-effect`
- `bash run.sh`
- `sh check-event-job-trace.sh`

## Observed Output

The emitting command returns a job id:

- `cncf-job-job-...`

The await result confirms routed execution:

- `outcome = Routed`
- `dispatched_count = 1`

The job status shows:

- `status = Succeeded`
- `result_success = true`
- `debug_request_summary = EventDriven.Event.emitEvent`

The job history shows:

- `job.submitted`
- `job.running`
- `task.running`
- `task.succeeded`
- `job.succeeded`

The final effect query returns the visible post-event payload:

- `event_name = item.changed`
- `name = alpha`
- `title = Alpha`

The `cozy` scripted fixture confirms the same flow in generated form:

- emitted command returns a job id
- await result shows `outcome = Routed`
- job status shows `Succeeded`
- job history shows the expected five-step timeline
- load-effect returns `item.changed`, `alpha`, and `Alpha`
- final scripted result is `EVENT_JOB_TRACE_OK`

## Main Point

`05.a-event-job-trace-lab` is the first shell-facing trace sample that connects:

- event emission
- routed reaction
- job-control observation
- visible effect verification

It shows how CNCF makes an event-driven path observable without asking the user to construct runtime internals directly.
