# 02.d-crud-server-memory-lab implementation record

## Summary

`02.d-crud-server-memory-lab` was reworked as the server/client runtime variant of the `02` CRUD line.

The old hand-written runtime Scala was removed from the sample path.
The sample now uses the same generated `sbt-cozy` line as the other `02` samples and focuses on:

- server start
- client create
- `job-control.job.await-job-result`
- later client load from the same server-held memory state

## What Changed

- replaced the old plain sbt build with `sbt-cozy`
- added [plugins.sbt](/Users/asami/src/dev2026/cncf-samples/samples/02.d-crud-server-memory-lab/project/plugins.sbt)
- moved `GetItem` and `ListItems` to `# QUERY` in [crud.cml](/Users/asami/src/dev2026/cncf-samples/samples/02.d-crud-server-memory-lab/src/main/cozy/crud.cml)
- removed the old hand-written runtime Scala under `src/main/scala`
- rewrote the shell scripts around `bin/cncf`
- added `run-client-await.sh` so the job result step is explicit
- rewrote the README in shell-first form

## Verified Commands

From [02.d-crud-server-memory-lab](/Users/asami/src/dev2026/cncf-samples/samples/02.d-crud-server-memory-lab):

- `bash ../../bin/cncf --discover=classes command help crud`
- `bash ../../bin/cncf --discover=classes command help crud.entity`
- `bash ../../bin/cncf --discover=classes command help crud.entity.create-item`
- `bash ../../bin/cncf --discover=classes command help job-control.job.await-job-result`
- `bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml`
- `bash run-server.sh`
- `bash run-client-create.sh`
- `bash run-client-await.sh <job-id>`
- `bash run-client-load.sh <entity-id>`
- `bash run.sh`

## Observed Runtime Shape

Observed behavior:

- `run-client-create.sh` returns a `cncf-job-...` id
- `run-client-await.sh` returns a JSON object containing the created entity id
- `run-client-load.sh` returns the created record through the same running server

This confirms that the sample is showing:

- the normal asynchronous command shape
- the normal server/client runtime path
- memory-backed state visibility while the same server process is alive

## Position In The 02 Line

- `02-crud`
  - generated CRUD surface inspection
- `02.a`
  - seed import verification
- `02.b`
  - `SimpleEntity` CRUD variation
- `02.c`
  - SQLite persistence across separate commands
- `02.d`
  - server/client memory-backed runtime with job result confirmation

## Scripted Verification

The same line is now covered in [`cozy` scripted](/Users/asami/src/dev2025/cozy/src/sbt-test/cozy/crud-server-memory):

- direct `cozy.Cozy modeler-scala --save=out.d`
- generated `out.d` compile
- command help for `crud.entity.create-item`
- command help for `job-control.job.await-job-result`
- `crud.meta.describe --format yaml`
- server start
- client create returning a job id
- `await-job-result` returning the created entity id
- later client load returning the created record

Final scripted result:

- `CRUD_SERVER_MEMORY_OK`
