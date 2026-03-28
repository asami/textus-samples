# 02.c-crud-sqlite-lab

## Overview

This sample follows the same model-driven CRUD method as `02-crud`, but the
point of this lab is SQLite-backed persistence.

It is intentionally small:

- define the CRUD model in CML
- let CNCF generate the CRUD surface
- point CNCF at a SQLite datastore path

## Requirements

- `cozy` is required
- `src/main/cozy/crud.cml` uses the same Dox-style model input as `textus-user-account`
- the sample is model-driven rather than hand-written CRUD repository logic
- the backing store is SQLite, configured through `cncf.datastore.sqlite.path`
- the sample also uses the standard CNCF `entity.d` seed-import path so the
  SQLite-backed read path can be observed immediately
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

## SQLite Path

The sample uses a file-backed SQLite datastore so create and read operations can be observed across separate commands.

Default path:

```bash
target/cncf.d/02c-crud-sqlite-lab.sqlite
```

## How To Use

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Create an item in SQLite:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite crud.entity.create-item --name alpha --title Alpha"
```

Load or search against the same SQLite-backed data:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite crud.entity.load-item --id alpha"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite crud.entity.search-item-record --name alpha"
```

Help can be inspected through `CncfMain` with class discovery:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity.create-item"
```

## Difference From 02-crud

`02-crud` shows the base model-driven CRUD surface.

This lab keeps that same model and adds one thing:

- SQLite is the backing store, wired through `cncf.datastore.sqlite.path`
- `entity.d` preloads data so the SQLite-backed read path can be observed

It is not a server/client lab.
It is not a handwritten repository lab.
It is a persistence-variation lab.
