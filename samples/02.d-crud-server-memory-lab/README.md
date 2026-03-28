# 02.d-crud-server-memory-lab

## Overview

This sample follows the same method as `02-crud`, but the point of this lab is
the server/client runtime shape with memory-backed state.

It is intentionally small:

- define the CRUD model in CML
- let CNCF generate the CRUD surface
- start a local server
- call the CRUD surface from a client
- confirm the resulting state from the same server-backed memory state

## Requirements

- `cozy` is required
- `src/main/cozy/crud.cml` uses the same Dox-style model input as `textus-user-account`
- the sample is model-driven rather than hand-written CRUD repository logic
- the backing state is memory-backed runtime state
- framework/runtime parameters use the `cncf.*` namespace
- query control parameters use the `query.*` namespace
- unprefixed parameters are reserved for domain attributes

## Model

- entity: `Item`
- service: `Item`
- operations:
  - `createItem`
  - `loadItem`
  - `searchItemRecord`

## Memory Path

The sample uses the normal CNCF in-memory runtime path.

There is no SQLite file and no custom repository layer.

## How To Use

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Start the server:

```bash
bash run-server.sh
```

Create an item from the client:

```bash
bash run-client-create.sh
```

Read the created entity id from the job result:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain client http get /job-control/job/await-job-result id=<job-id>"
```

Load the created item from the client:

```bash
bash run-client-load.sh <entity-id>
```

End-to-end demo:

```bash
bash run-demo.sh
```

## Difference From 02-crud

`02-crud` shows the base model-driven CRUD surface.

This lab keeps that same model and adds one thing:

- server/client runtime shape with memory-backed state

It is not a SQLite lab.
It is not a handwritten repository lab.
It is a server/client runtime variation lab.

## Notes

This lab keeps the standard job-backed command behavior.

The confirmation path is:

1. `crud.entity.create-item`
2. `job-control.job.await-job-result`
3. `crud.entity.load-item`

This keeps the sample on the normal CNCF server/client path:

- the client receives the server-side job id
- the created entity id is read from the awaited job result
- the final load is done through the generated CRUD route
- the client itself stays a synchronous transport facade and returns the server response directly

`job-control.job.get-job-result` is also available when the caller wants a non-blocking result probe.

`search-item-record` after create still reflects the normal visibility policy:
draft content is not shown by default to a non-manager search path.
