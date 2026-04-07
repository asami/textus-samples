# 06.a-job-control-lab Implementation Record

## Summary

`08.a-job-control-lab` was reworked as the control-oriented companion to `08-job`.

The sample now explains and demonstrates:

- control-ready job submission
- job suspension
- job resume
- job cancellation
- job history inspection
- job-event inspection

The older same-JVM demo path was removed from the sample path.

## Sample Shape

The user-facing path is shell-first.

Important files:

- `src/main/cozy/job-control-lab.cml`
- `src/main/scala/org/sample/jobcontrol/impl/JobControlLabComponentFactory.scala`
- `run.sh`
- `README.md`

The sample-specific factory exposes a control-ready submit path. The visible `create-item` result is a `job_id`, and the inner work is delegated to a slower async entity-side job so that suspend, resume, and cancel can be observed.

## Verified Flow

The documented flow was run against the sample server:

1. start the server
2. inspect `job-control-lab.item.create-item`
3. submit a control-ready job
4. suspend the job
5. inspect job status
6. resume the job
7. await the final result
8. load job history
9. inspect job events through `event.event-admin.load-job-events`
10. submit and cancel another job

Observed results:

- submit returns `job_id`
- suspend returns `status: Suspended`
- resume returns `status: Running`
- await returns the created entity id
- history includes `job.suspended`, `job.resumed`, and `job.cancelled`
- event inspection works through the `command` path

## Notes

- `08-job` remains the observation sample
- `08.a-job-control-lab` is the control sample
- the sample required a runtime fix in CNCF so the impl factory remains the active factory during action dispatch
