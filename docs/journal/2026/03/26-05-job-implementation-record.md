# 05-job Implementation Record

## Status

Completed

## Summary

The first completion line for `05-job` is implemented.

The sample now demonstrates:

- one command-shaped request that creates a job
- one route that reads job status/result
- one route that reads job timeline/debug information

## Files

- `samples/05-job/src/main/cozy/job.cml`
- `samples/05-job/build.sbt`
- `samples/05-job/project/plugins.sbt`
- `samples/05-job/run.sh`
- `samples/05-job/src/main/scala/org/sample/job/JobFlowDemo.scala`
- `samples/05-job/README.md`
- `docs/phase/samples/05-job.md`

## Notes

`JobSample.Item.createItem` now uses `IMPLEMENTATION = echo-record`.

That means the generated command body is executable and the demo can stay on the
generated action path while focusing on job management itself:

- submit
- await result
- query read model
- inspect timeline/debug summary

## Verification

Build/help:

- `sbt --no-server --batch clean compile`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help job-sample.item.create-item"`

Demo:

- `./run.sh`

Observed runtime output:

- returned one job id such as `cncf-job-job-...`
- status: `Succeeded`
- result-success: `true`
- task-statuses: `[Succeeded]`
- timeline-kinds: `[job.submitted, job.running, task.running, task.succeeded, job.succeeded]`
- debug-request-summary: `JobSample.Item.createItem`
