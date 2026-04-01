# 04-cqrs

## Overview

This sample shows a visible CQRS split in CNCF:

- command side: `createItem` is job-backed and asynchronous in shape
- query side: `loadItem` and `searchItemRecord` are immediate and read-oriented

It uses the same model-driven Cozy/CML direction as `02-crud`.

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
- command-side operation:
  - `createItem`
- query-side operation:
  - `loadItem`
  - `searchItemRecord`

## How To Use

Generation/build commands:

```bash
sbt cozyGenerate
sbt clean compile
```

Runtime help can be inspected through `CncfMain` with class discovery:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help cqrs"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help cqrs.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help cqrs.item.create-item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help cqrs.entity"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help cqrs.entity.load-item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help cqrs.entity.search-item-record"
```

Runtime examples:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command cqrs.item.create-item --name beta --title Beta"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command cqrs.entity.load-item --id org-sample-entity-item-20260327000000-aaa111"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command cqrs.entity.search-item-record --name alpha"
```

## Runtime Difference

- `createItem` goes through the command side and is job-backed, so the visible result is a job-oriented response.
- `loadItem` goes through the query side and returns the current item immediately.
- `searchItemRecord` goes through the query side and returns matching records immediately.

## Relationship To 02-crud

`02-crud` shows the base generated CRUD surface.

`04-cqrs` adds a visible runtime split:

- the write path is command-oriented and job-backed
- the read path is query-oriented and immediate

That is the point of this sample.

## Observed Surface

- component: `Cqrs`
- service: `Cqrs.Item`
- command target: `Cqrs.Item.createItem`
- query target: `Cqrs.entity.loadItem`
- search target: `Cqrs.entity.searchItemRecord`
- CLI selector examples: `cqrs`, `cqrs.item`, `cqrs.item.create-item`, `cqrs.entity`, `cqrs.entity.load-item`, `cqrs.entity.search-item-record`
- seeded item id: `org-sample-entity-item-20260327000000-aaa111`
