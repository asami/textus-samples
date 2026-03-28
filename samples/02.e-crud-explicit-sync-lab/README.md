# 02.e-crud-explicit-sync-lab

## Overview

This sample follows the same model-driven CRUD direction as `02-crud`, but it
shows explicit synchronous execution as a runtime option in a server/client runtime.

This is not hidden sync:

- the create route explicitly requests sync execution
- the create command returns the actual result immediately
- the result is not a job id

## Requirements

- `cozy` is required
- `src/main/cozy/crud.cml` is the model source
- the sample is model-driven rather than hand-written CRUD repository logic
- runtime help and execution use `CncfMain --discover=classes`
- framework/runtime parameters use the `textus.*` namespace
- `cncf.*` remains accepted as a compatibility alias
- query control parameters use the `query.*` namespace
- unprefixed parameters are reserved for domain attributes

## Model

- entity: `Item`
- service: `entity`
- command target:
  - `crud.entity.create-item`

## How To Use

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Help:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity.create-item"
```

Start the server:

```bash
bash run-server.sh
```

Client create:

```bash
bash run-client-create.sh
```

Client confirmation with the returned id:

```bash
bash run-client-search.sh <entity-id>
```

End-to-end demo:

```bash
bash run-demo.sh
```

## Runtime Difference

- Normal CRUD/job-backed command use remains the default elsewhere.
- `03.b-test-sync-command-lab` uses a runtime override while keeping the job-shaped interface.
- This lab uses an explicit runtime option on the create route to request synchronous execution and returns the result immediately.

## Relationship To 02-crud

`02-crud` is the base model-driven CRUD sample.

`02.e-crud-explicit-sync-lab` keeps the same CRUD direction, but shows:

- explicit synchronous runtime execution
- immediate result instead of a job id
- a same-server-session follow-up load confirmation route

## Observed Surface

- component: `Crud`
- service: `Crud.entity`
- command target: `Crud.entity.createItem`
- explicit sync runtime option: `textus.runtime.command.execution-mode=sync-direct-no-job`
- runtime result: immediate item record
- CLI selector examples: `crud`, `crud.entity`, `crud.entity.create-item`

## Notes

The mainline confirmation path for this lab is:

1. server start in normal server mode
2. client `crud.entity.create-item` with `textus.runtime.command.execution-mode=sync-direct-no-job`
3. immediate `id` in the returned record
4. same-server-session `crud.entity.load-item`

`run-demo.sh` starts the server, captures the immediate create result, and uses that `id` for the follow-up load.
