# 03.a-designed-sync-command-lab

## Overview

This sample shows a command which is synchronous by design in CNCF.

It differs from `03-cqrs` in one important way:

- `03-cqrs` shows a job-backed command side
- `03.a-designed-sync-command-lab` makes one command return its result immediately by design

It starts from the same Cozy/CML source shape as `03-cqrs`, but changes one
operation contract in the model: `createItem` is marked with `EXECUTION=sync`.

## Requirements

- `cozy` is required
- `src/main/cozy/cqrs.cml` is the model source
- the designed-sync behavior is defined in the CML operation metadata
- runtime help and execution use `CncfMain --discover=classes`
- framework/runtime parameters use the `cncf.*` namespace
- query control parameters use the `query.*` namespace
- unprefixed parameters are reserved for domain attributes

## Model

- entity: `Item`
- service: `Item`
- command target:
  - `DesignedSync.Item.createItem`

## How To Use

Generation/build commands:

```bash
sbt cozyGenerate
sbt clean compile
```

Runtime help can be inspected through `CncfMain`.

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help DesignedSync"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help DesignedSync.Item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help DesignedSync.Item.createItem"
```

Runtime examples:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command DesignedSync.Item.createItem --name beta --title Beta"
```

## Runtime Difference

- `createItem` returns the created item immediately.
- The result is not a job id.
- The synchronous behavior comes from the CML execution directive, not from a test-only override or a config switch.

## Relationship To 02-crud

`03-cqrs` shows the job-backed command / immediate query split.

`03.a-designed-sync-command-lab` shows a different point:

- a command can be synchronous by design
- the caller receives the result directly
- the application contract, not a test override, chooses the sync behavior

## Observed Surface

- component: `DesignedSync`
- service: `DesignedSync.Item`
- command target: `DesignedSync.Item.createItem`
- runtime result: immediate item record
- modeled directive: `EXECUTION=sync`
