# 05.b-event-job-server-client-lab Implementation Record

## Summary

`05.b-event-job-server-client-lab` was normalized as the practical server/client event flow sample.

The sample now focuses on:

- starting the local CNCF server
- emitting one event-backed client request
- waiting for the routed reaction to complete
- reading the visible effect back from another client request

## What Changed

- rewrote the README as a shell-first server/client sample
- replaced the old helper wrappers with direct `bin/cncf` shell scripts
- added `run-client-await.sh`
- added matching `cozy` scripted verification for the same practical server/client flow
- updated `run-demo.sh` so it now performs:
  - server start
  - emit
  - await
  - load
- corrected the sample build name to `05-b`

## Verified Commands

- `../../bin/setup cozy`
- `sbt --batch clean compile`
- `bash ../../bin/cncf --discover=classes command help event-driven.event.emit-event`
- `bash run-server.sh`
- `bash run-client-emit.sh`
- `bash run-client-await.sh <job-id>`
- `bash run-client-load.sh`
- `bash run-demo.sh`
- `sh check-event-job-server-client.sh`

## Observed Output

The server starts successfully and binds on `:8080`.

The client emit returns a job id:

- `cncf-job-job-...`

The await step returns:

- `outcome = Routed`
- `dispatched_count = 1`

The final load step returns:

- `event_name = item.changed`
- `name = alpha`
- `title = Alpha`

The matching `cozy` scripted fixture confirms:

- generated server start
- emit returns a job id
- await returns `Routed`
- load returns the visible effect payload
- final scripted result `EVENT_JOB_SERVER_CLIENT_OK`

## Main Point

`05.b-event-job-server-client-lab` is the smallest practical event/job service sample.

It shows the operational flow a user actually runs:

- start server
- send event-producing client request
- wait for the routed reaction
- read the visible effect back through another client request
