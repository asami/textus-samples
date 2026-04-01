# 06.a-job-control-lab Implementation Record

## Summary

Implemented the first minimal job-control lab after `06-job`, with lifecycle event observation and per-job history observation.

## Facts

- The sample directory is [`samples/06.a-job-control-lab`](/Users/asami/src/dev2026/cncf-samples/samples/06.a-job-control-lab).
- The model source is [`samples/06.a-job-control-lab/src/main/cozy/job-control-lab.cml`](/Users/asami/src/dev2026/cncf-samples/samples/06.a-job-control-lab/src/main/cozy/job-control-lab.cml).
- The demo runner is [`samples/06.a-job-control-lab/src/main/scala/org/sample/jobcontrol/JobControlDemo.scala`](/Users/asami/src/dev2026/cncf-samples/samples/06.a-job-control-lab/src/main/scala/org/sample/jobcontrol/JobControlDemo.scala).
- The demo route is `bash run.sh` in [`samples/06.a-job-control-lab`](/Users/asami/src/dev2026/cncf-samples/samples/06.a-job-control-lab/README.md).
- The generated command uses `IMPLEMENTATION = blocking-task`.
- The runner submits two jobs through the generated command path:
- one job is suspended and resumed
- one job is cancelled
- The observation routes are:
- builtin `job_control.job.load_job_history` for per-job history
- builtin `event.event_admin.load_job_events` for lifecycle events

## Verification Facts

- `sbt cozyGenerate` succeeded.
- `sbt clean compile` succeeded.
- `command help job-control-lab` succeeded.
- `command help job-control-lab.item` succeeded.
- `command help job-control-lab.item.create-item` succeeded.
- `bash run.sh` succeeded.
- The runner printed a JSON line showing:
- a suspended/resumed job with final status `Succeeded`
- suspended/resumed job history kinds:
  `[job.submitted, job.running, task.running, job.suspended, job.resumed, task.succeeded, job.succeeded]`
- suspended/resumed lifecycle events:
  `[job.submitted, job.running, job.suspended, job.resumed, job.succeeded]`
- a cancelled job with final status `Cancelled`
- cancelled job history kinds:
  `[job.submitted, job.running, task.running, job.cancelled]`
- cancelled lifecycle events:
  `[job.submitted, job.running, job.cancelled]`

## Notes

- The lab remains local and command-first.
- It uses the builtin `JobControl` surface for control and observation.
- Lifecycle event observation is backed by builtin `event`.
- Per-job history observation is backed by builtin `load_job_history`.
