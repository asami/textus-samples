# 02.e-crud-explicit-sync-lab

## Overview

This sample follows the same model-driven CRUD direction as `02-crud`, but it
shows explicit synchronous execution as a runtime option.

This is not hidden sync:

- the caller explicitly requests sync with a runtime option
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
- service: `Item`
- command target:
  - `Crud.Item.createItem`

## How To Use

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Help:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.item.create-item"
```

Explicit sync create:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes --textus.runtime.command.execution-mode sync-direct-no-job command crud.item.create-item --name alpha --title Alpha"
```

Server/client confirmation:

```bash
bash run-server.sh
bash run-client-create.sh
bash run-client-search.sh
```

## Runtime Difference

- Normal CRUD/job-backed command use remains the default elsewhere.
- `03.b-test-sync-command-lab` uses a runtime override while keeping the job-shaped interface.
- This lab uses an explicit runtime option to request synchronous execution and returns the result immediately.

## Relationship To 02-crud

`02-crud` is the base model-driven CRUD sample.

`02.e-crud-explicit-sync-lab` keeps the same CRUD direction, but shows:

- explicit synchronous runtime execution
- immediate result instead of a job id
- a follow-up load/search confirmation route

## Observed Surface

- component: `Crud`
- service: `Crud.Item`
- command target: `Crud.Item.createItem`
- explicit sync runtime option: `--textus.runtime.command.execution-mode sync-direct-no-job`
- runtime result: immediate item record
- CLI selector examples: `crud`, `crud.item`, `crud.item.create-item`
