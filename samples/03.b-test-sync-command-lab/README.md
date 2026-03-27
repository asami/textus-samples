# 03.b-test-sync-command-lab

## Overview

This sample shows a command that stays async/job-backed by default, but can be
executed synchronously for test/local/debug use through runtime override.

It differs from earlier samples in two ways:

- `03-cqrs` keeps the default CQRS command shape only
- `03.a-designed-sync-command-lab` makes the command synchronous in the model
- `03.b-test-sync-command-lab` keeps the command async by design and only overrides execution at runtime

## Requirements

- `cozy` is required
- `src/main/cozy/cqrs.cml` is the model source
- the sample is model-driven rather than hand-written repository logic
- framework/runtime parameters use the `textus.*` namespace
- `cncf.*` remains accepted as a compatibility alias
- query control parameters use the `query.*` namespace
- unprefixed parameters are reserved for domain attributes

## Model

- entity: `Item`
- service: `Item`
- command target:
  - `TestSync.Item.createItem`

## How To Use

Generation/build commands:

```bash
sbt cozyGenerate
sbt clean compile
```

Runtime help:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help test-sync"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help test-sync.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help test-sync.item.create-item"
```

Default async/job-backed execution:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command test-sync.item.create-item --name beta --title Beta"
```

Test/local synchronous override:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes --textus.runtime.command.execution-mode sync-job-async-interface command test-sync.item.create-item --name beta --title Beta"
```

## Runtime Difference

- Default execution returns a job-shaped result such as `cncf-job-...`.
- Override execution also returns a job-shaped result such as `cncf-job-...`.
- The command target is the same in both cases.
- The override comes from runtime execution mode, not from CML `EXECUTION=sync`.
- The external interface is intentionally unchanged in `03.b`.
- The purpose of the override is to keep the job-oriented contract while making test/local execution wait for command completion internally.

## Relationship To 03.a

`03.a-designed-sync-command-lab` is about design-time sync:

- the model says the command is synchronous

`03.b-test-sync-command-lab` is about runtime override:

- the model keeps the command async/job-backed
- test/local/debug can force synchronous internal execution through runtime parameters
- the command still returns a job id because the external interface is preserved

## Observed Surface

- component: `TestSync`
- service: `TestSync.Item`
- command target: `TestSync.Item.createItem`
- default result: job-shaped response
- override result: job-shaped response with synchronous internal completion semantics
- CLI selector examples: `test-sync`, `test-sync.item`, `test-sync.item.create-item`
