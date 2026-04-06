# 02.e CRUD Explicit Sync Lab Implementation Record

## Summary

`02.e-crud-explicit-sync-lab` was reworked as the explicit synchronous execution variant of the `02` CRUD line.

The old hand-written runtime Scala path was removed.
The sample now uses the generated `sbt-cozy` line and a shell-first server/client flow.

## What Changed

- switched the sample build to `sbt-cozy`
- removed the hand-written runtime Scala files from the sample path
- moved `GetItem` and `ListItems` into the `# QUERY` section
- rewrote the shell commands to use `bin/cncf`
- documented the explicit sync runtime parameter

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help crud`
- `bash ../../bin/cncf --discover=classes command help crud.entity`
- `bash ../../bin/cncf --discover=classes command help crud.entity.create-item`
- `bash ../../bin/cncf --discover=classes command help crud.entity.load-item`
- `bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml`
- `bash run-demo.sh`

## Observed Runtime Shape

- the server process is started in normal CNCF server mode
- the client create route uses `--textus.runtime.command.execution-mode sync-direct-no-job`
- the create result returns immediately with the created entity `id`, not a job id
- the returned `id` can be used immediately by `crud.entity.load-item`
- the follow-up load returns the full entity record

## Scripted

The corresponding `cozy` scripted fixture is `cozy/crud-explicit-sync`.
It passes with the final marker `CRUD_EXPLICIT_SYNC_OK`.
