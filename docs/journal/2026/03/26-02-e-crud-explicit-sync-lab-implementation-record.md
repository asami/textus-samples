# 26-02-e-crud-explicit-sync-lab Implementation Record

## Summary

Implemented `02.e-crud-explicit-sync-lab` as the CRUD sample for explicit synchronous runtime execution.

## Confirmed behavior

- The sample keeps the same model-driven CRUD direction as `02-crud`.
- The explicit sync path is requested by runtime option, not by hidden sync behavior.
- The client/server route returns the actual create result immediately instead of a job id.
- The follow-up confirmation route loads the created entity in the same running server session.

## Confirmed commands

- `bash run-server.sh`
- `bash run-client-create.sh`
- `bash run-client-search.sh <entity-id>`
- `bash run-demo.sh`

## Mainline flow

1. Start the server with `--textus.runtime.command.execution-mode sync-direct-no-job`.
2. Call `crud.entity.create-item` through the client path.
3. Read the returned `id` from the immediate result.
4. Call `crud.entity.load-item` with that `id`.

## Notes

- This lab is for explicit sync execution.
- It is different from hidden sync and from job-backed confirmation through `job-control`.
