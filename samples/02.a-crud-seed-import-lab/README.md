# 02.a-crud-seed-import-lab

## Overview

`02.a-crud-seed-import-lab` is the first runtime CRUD sample after `02-crud`.

It adds:

- descriptor-first runtime metadata under `car.d/meta`
- seed import under `entity.d`
- runtime verification through generated entity operations

This sample moves from surface inspection to imported-data verification.

## Position

`02-crud` shows the generated CRUD surface.

`02.a` extends that line by showing:

- how seed data is supplied to the generated component
- how imported records can be verified through CNCF commands
- how descriptor-first layout works in the sample directory

## Intended Use Case

Use this sample when you want to confirm that:

- seed data placed in `entity.d` is imported at runtime
- the generated entity service can `load` a known seeded record
- the generated entity search can filter imported records

## Files

- `src/main/cozy/crud.cml`
  - the source model
- `car.d/meta/component-descriptor.yaml`
  - descriptor-first runtime metadata
- `entity.d/crud.yaml`
  - imported seed data
- `run.sh`
  - batch wrapper for the documented shell commands

## How To Run

```bash
$ cd samples/02.a-crud-seed-import-lab
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Load Help

This command shows the generated entity load operation.

```bash
$ bash ../../bin/cncf --discover=classes command help crud.entity.load-item
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud.entity.load-item`
  - selects the generated entity load operation

Output example:

```yaml
type: operation
name: loadItem
component: Crud
service: entity
selector:
  cli: crud.entity.load-item
returns:
  - Option[Item]
```

This confirms that the generated entity service exposes a load route for the imported records.

### Search Help

This command shows the generated entity search operation.

```bash
$ bash ../../bin/cncf --discover=classes command help crud.entity.search-item-record
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud.entity.search-item-record`
  - selects the generated record-oriented entity search operation

Output example:

```yaml
type: operation
name: searchItemRecord
component: Crud
service: entity
selector:
  cli: crud.entity.search-item-record
returns:
  - SearchResult[Item]
```

### Load Seeded Item

This command loads one known seeded record by id.

```bash
$ bash ../../bin/cncf --discover=classes command crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `crud.entity.load-item`
  - invokes the generated entity load operation directly
- `--id`
  - the seeded entity id from `entity.d/crud.yaml`

Output example:

```yaml
id: major-minor-entity-item-20260327000000-aaa111
name_attributes:
  name: alpha
  title: Alpha
```

This confirms that the seed import ran and that the imported `alpha` item is available through the generated entity load route.

### Search Seeded Item

This command searches the imported records by domain attribute.

```bash
$ bash ../../bin/cncf --discover=classes command crud.entity.search-item-record --name alpha
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `crud.entity.search-item-record`
  - invokes the generated entity search operation directly
- `--name alpha`
  - filters the imported records by the domain attribute `name`

Output example:

```yaml
query:
  condition:
    name: alpha
data:
- id: major-minor-entity-item-20260327000000-aaa111
  name_attributes:
    name: alpha
    title: Alpha
total_count: 1
fetched_count: 1
```

This confirms that:

- the seed import produced searchable records
- the domain filter is preserved in `query.condition`
- the `alpha` record is the only match

### Metadata Describe

This command shows the generated component metadata.

```bash
$ bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `crud.meta.describe`
  - invokes the metadata service for the generated component
- `--format yaml`
  - requests YAML output

Output example:

```yaml
services:
- type: service
  name: entity
  runtime_name: entity
aggregates:
- name: item
  entity_name: item
views:
- name: item
  entity_name: item
operation_definitions:
- name: createItem
- name: getItem
- name: listItems
```

## Relationship To 02-crud

`02-crud` stops at generated surface inspection.

`02.a` adds:

- runtime descriptor layout
- imported seed data
- load/search confirmation against imported records

## Summary

Use `02.a-crud-seed-import-lab` as the first CRUD sample that verifies actual imported data through CNCF commands.
