# 06-job Implementation Record

## Summary

`08-job` was reworked from an internal same-JVM demo into a shell-first job-management sample.

The old `JobFlowDemo.scala` path directly constructed runtime internals and queried the job engine from Scala.

That behavior was appropriate as a test, but not as a user-facing sample.

The sample now demonstrates the standard CNCF shell flow:

1. inspect the job-backed command
2. inspect the `job-control` surface
3. start the server
4. submit the command and capture the job id
5. await completion
6. read result, status, and history through `job-control`

## Verified Commands

```bash
bash ../../bin/cncf --discover=classes command help job-sample
bash ../../bin/cncf --discover=classes command help job-sample.item.create-item
bash ../../bin/cncf --discover=classes command help job-control.job
bash ../../bin/cncf --discover=classes command help job-control.job.await-job-result
bash ../../bin/cncf --discover=classes command help job-control.job.get-job-result
bash ../../bin/cncf --discover=classes command help job-control.job.get-job-status
bash ../../bin/cncf --discover=classes command help job-control.job.load-job-history
bash ../../bin/cncf --discover=classes command job-sample.meta.describe --format yaml
bash ../../bin/cncf --discover=classes server
bash ../../bin/cncf --discover=classes client job-sample.item.create-item --name alpha --title Alpha
bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id <job-id>
bash ../../bin/cncf --discover=classes client job-control.job.get-job-result --id <job-id>
bash ../../bin/cncf --discover=classes client job-control.job.get-job-status --id <job-id>
bash ../../bin/cncf --discover=classes client job-control.job.load-job-history --id <job-id>
```

## Observed Result Shape

- submit:
  - returns `cncf-job-...`
- await result:
  - returns the stored result payload
- get result:
  - returns the same stored payload
- get status:
  - returns summarized operational state including `status`, `result_success`, `tasks`, `timeline`, and `debug_request_summary`
- load history:
  - returns ordered timeline events such as `job.submitted`, `job.running`, `task.running`, `task.succeeded`, and `job.succeeded`

## Notes

- the user-facing sample no longer depends on direct `Subsystem` construction
- same-JVM job engine probing should move to `cozy` scripted if it is still needed as an assertion
