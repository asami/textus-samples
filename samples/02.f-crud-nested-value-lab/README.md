# 02.f-crud-nested-value-lab

## Overview

This lab is the CRUD sample for nested value object persistence.

It stays in the plain CRUD line:

- entity CRUD remains the focus
- one entity attribute is a value object
- that value object contains another value object
- load/restore proves the nested value shape survives persistence

It is not an aggregate lab.

## Model Story

Recommended shape:

- `Person`
- `Address`
- `CountryCode`

Typical nesting:

- `Person.address: Address`
- `Address.country: CountryCode`

## Verified Demos

Model restore demo:

```bash
bash run.sh
```

Datastore roundtrip demo:

```bash
bash run-datastore.sh
```

CRUD command surface:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud-nested-value-sample.entity.create-person"
bash run-create.sh
bash run-load.sh <person-id>
```

The current first line confirms:

- one `Person` record contains embedded nested value data
- `Person.createC(record)` restores the nested structure
- datastore save/load restores the nested structure
- `create-person` returns an entity id immediately in explicit sync mode
- `load-person` restores nested value object data from SQLite in a later process

## What This Lab Should Show

1. create or save one entity with nested value object data
2. load the entity back
3. confirm the nested value object structure is restored
4. keep the persistence shape as one entity record with embedded nested value data

## Relation To Other Samples

- `02-crud` shows the base CRUD line
- `06.a-aggregate-single-record-lab` shows a single-record aggregate pattern
- `02.f-crud-nested-value-lab` stays in plain CRUD and focuses on nested value object persistence
