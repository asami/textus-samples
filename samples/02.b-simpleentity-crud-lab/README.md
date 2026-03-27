# 02.b-simpleentity-crud-lab

## Overview

This sample follows the same method as `textus-user-account`.

- define the model in CML
- use `cozy`
- let CNCF tooling expose the CRUD surface

It is the `SimpleEntity` follow-up to `02-crud`.
The CNCF dependency version is controlled by `../../versions/cncf-version.conf`, with `CNCF_VERSION` as an override.

## Requirements

- `cozy` is required
- `src/main/cozy/crud.cml` uses the same Dox-style model input as `textus-user-account`
- the sample is model-driven rather than hand-written CRUD repository logic
- the generated `SimpleEntity` attribute groups are the main point
- framework/runtime parameters use the `textus.*` namespace
- `cncf.*` remains accepted as a compatibility alias
- query control parameters use the `query.*` namespace
- unprefixed parameters are reserved for domain attributes

## Model

- entity: `Item` based on `SimpleEntity`
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
```

Runtime help can be inspected through `CncfMain` with class discovery:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help simple-entity-crud-lab"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help simple-entity-crud-lab.item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help simple-entity-crud-lab.item.create-item"
```

When a framework/runtime parameter is needed, use the `textus.*` namespace.
For example:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --textus.format yaml help simple-entity-crud-lab.item.create-item"
```

Observed runtime surface:

- component: `SimpleEntityCrudLab`
- generated service: `Item`
- generated operation: `SimpleEntityCrudLab.Item.createItem`
- CLI selector examples: `simple-entity-crud-lab`, `simple-entity-crud-lab.item`, `simple-entity-crud-lab.item.create-item`
- framework services: `meta`, `system`
- the generated Scala under `target/scala-3.3.7/src_managed/main` exposes the `SimpleEntity`-shaped CRUD surface through `ItemService`, `AggregateService`, `ViewService`, and the `entity/*` value types

## Relationship To 02-crud And 02.a

`02.b-simpleentity-crud-lab` is the `SimpleEntity` variation of the base
model-driven CRUD flow.

It keeps:

- CML/Cozy generation
- `--discover=classes` runtime inspection
- parameter namespace usage (`textus.*`, `query.*`, domain attributes)

Compared with the earlier steps:

- `02-crud` shows the base generated CRUD surface
- `02.a-crud-seed-import-lab` adds descriptor-first runtime metadata and
  seed-driven `load` / `search`
- `02.b-simpleentity-crud-lab` focuses on the `SimpleEntity` attribute groups
  and the generated CRUD surface that comes with them

## Design Goal

This sample should show that the CRUD API surface comes from the model and CNCF tooling.

It should not mainly show:

- a custom repository
- a custom TSV store
- hand-written CRUD business logic
- a second model layer that hides the `SimpleEntity` attribute groups

It should mainly show:

- CML model definition
- entity service
- aggregate service
- command/API usage produced from that model
