# 26-02-d-crud-server-memory-lab Implementation Record

## Outcome

`02.d-crud-server-memory-lab` now works as the server/client + memory-backed CRUD variation.

## Verified Runtime Shape

- server:
  - `bash run-server.sh`
- client create-style route:
  - `bash run-client-create.sh`
  - actual operation: `crud.entity.create-item`
- client job-result route:
  - `client job-control.job.await-job-result --id <job-id>`
- client confirmation route:
  - `bash run-client-load.sh <entity-id>`
  - actual operation: `crud.entity.load-item`

## Confirmed Behavior

- the server starts and keeps one in-memory runtime state
- the client receives the server-side job id from `create-item`
- the client can read the created entity id from `job-control.job.await-job-result`
- the client can load the same item back from the same server session

## CNCF Fixes That Unblocked This Lab

- stabilized in-memory datastore collection lookup for entity collections
- aligned generated `EntityId` creation with the same collection identity used by `DataStore`
- made `load-item` fall back from resident `EntitySpace` to direct store lookup when the working set misses
- made client mode return the server response directly instead of wrapping transport in a client-side job
- added `job-control.job.get-job-result` and `job-control.job.await-job-result`
- `await-job-result` reads the actual command result from the server-side job after completion

## Notes

- this lab keeps the normal job-backed create behavior
- the client-visible confirmation path is:
1. `crud.entity.create-item`
2. `job-control.job.await-job-result`
3. `crud.entity.load-item`
- `search-item-record` after create still reflects the normal visibility policy:
  draft content is not shown by default to a non-manager search path
- for that reason, the client-visible confirmation route is `load-item`, not `search-item-record`
