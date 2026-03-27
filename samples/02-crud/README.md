# 02-crud

## Overview

This sample follows the same method as `textus-user-account`.

- define the model in CML
- use `cozy`
- let CNCF tooling expose the CRUD surface

It is intentionally simpler than later samples.

## Requirements

- `cozy` is required
- `src/main/cozy/crud.cml` uses the same Dox-style model input as `textus-user-account`
- the sample is model-driven rather than hand-written CRUD repository logic
- entity service and aggregate service are the main point
- framework/runtime parameters use the `cncf.*` namespace
- query control parameters use the `query.*` namespace
- unprefixed parameters are reserved for domain attributes

## Model

- entity: `Item`
- service: `Item`
- operations:
  - `createItem`
  - `getItem`
  - `listItems`

## How To Use

The CRUD surface comes from CML + CNCF tooling.

Even though the file extension is `.cml`, the content is written in the Dox-style structure expected by the Cozy modeler.

Generation/build commands:

```bash
sbt cozyGenerate
sbt clean compile
sbt cozyBuildCAR
sbt cozyBuildSAR
```

Runtime help can be inspected through `CncfMain` with class discovery:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.item.create-item"
```

When a framework/runtime parameter is needed, use the `textus.*` namespace.
`cncf.*` remains accepted as a compatibility alias.
For example:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.format yaml help crud.item.create-item"
```

Observed runtime surface:

- component: `Crud`
- service: `Crud.Item`
- operation: `Crud.Item.createItem`
- CLI selector examples: `crud`, `crud.item`, `crud.item.create-item`
- additional generated services: `aggregate`, `entity`, `view`
- framework services: `meta`, `system`

## Relationship To 02.a

`02-crud` is the base model-driven CRUD sample.

It shows:

- CML/Cozy generation
- generated component/service/operation surfaces
- `--discover=classes` runtime inspection
- parameter namespace usage (`cncf.*`, `query.*`, domain attributes)

The next lab, `02.a-crud-seed-import-lab`, builds on this base and adds:

- descriptor-first runtime metadata through `car.d/meta/component-descriptor.yaml`
- seed data import from `entity.d`
- runtime `load` / `search` verification against preloaded data

## Design Goal

This sample should show that the CRUD API surface comes from the model and CNCF tooling.

It should not mainly show:

- a custom repository
- a custom TSV store
- hand-written CRUD business logic

It should mainly show:

- CML model definition
- entity service
- aggregate service
- command/API usage produced from that model
