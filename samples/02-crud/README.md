# 02-crud

## Overview

`02-crud` is the first model-driven CRUD sample in the CNCF sample line.

It shows the base generated surface that comes from:

- one CML model
- `cozy` code generation
- CNCF command discovery

This sample stays on the inspection line.
It does not preload seed data and it does not focus on stateful runtime verification.
Those lines come later in `02.a` and after.

## Position

This sample is the base CRUD/reference point for the `02-*` family.

It is meant to show:

- how one entity model becomes a component
- how one generated service exposes CRUD-oriented operations
- how to inspect the generated surface through `bin/cncf`

It is not meant to show:

- seed import
- server/client interaction
- storage-specific behavior

## Intended Use Case

Use this sample when you want to confirm the first generated CRUD surface before moving to later labs.

Typical use cases are:

- checking the generated component name, service name, and operation names
- confirming the CLI selector shape for a generated CRUD service
- inspecting the metadata that CNCF exposes from the model

## Files

- [crud.cml](/Users/asami/src/dev2026/cncf-samples/samples/02-crud/src/main/cozy/crud.cml)
  - the source model
- [build.sbt](/Users/asami/src/dev2026/cncf-samples/samples/02-crud/build.sbt)
  - enables `sbt-cozy` generation for the sample
- [run.sh](/Users/asami/src/dev2026/cncf-samples/samples/02-crud/run.sh)
  - batch wrapper for the documented shell commands

## How To Run

```bash
$ cd samples/02-crud
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Component Help

This command shows the generated component surface.

```bash
$ bash ../../bin/cncf --discover=classes command help crud
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud`
  - selects the generated component

Output example:

```yaml
type: component
name: Crud
children:
  - Item
  - aggregate
  - entity
  - meta
  - system
  - view
operationDefinitions:
  - createItem
  - getItem
  - listItems
```

This confirms that the model generated:

- one component: `Crud`
- one domain service: `Item`
- one basic CRUD-oriented operation set

### Service Help

This command shows the generated service surface.

```bash
$ bash ../../bin/cncf --discover=classes command help crud.item
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud.item`
  - selects the generated `Item` service under the `Crud` component

Output example:

```yaml
type: service
name: Item
component: Crud
children:
  - createItem
  - getItem
  - listItems
operations:
  - createItem
  - getItem
  - listItems
```

This is the first point where the generated service contract becomes visible.

### Operation Help

This command shows one generated operation.

```bash
$ bash ../../bin/cncf --discover=classes command help crud.item.create-item
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud.item.create-item`
  - selects the generated operation in CLI form

Output example:

```yaml
type: operation
name: createItem
component: Crud
service: Item
selector:
  canonical: Crud.Item.createItem
  cli: crud.item.create-item
  rest: /crud/item/create-item
returns:
  - CreateItemResult
```

This confirms:

- the canonical selector
- the CLI selector
- the REST path shape
- the generated return type

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
  - invokes the generated metadata service
- `--format yaml`
  - asks for structured YAML output

Output example:

```yaml
services:
- type: service
  name: Item
  runtime_name: item
aggregates:
- name: item
  entity_name: item
views:
- name: item
  entity_name: item
operation_definitions:
- name: createItem
  kind: COMMAND
- name: getItem
  kind: QUERY
- name: listItems
  kind: QUERY
```

This is useful when you want to confirm the generated runtime contract without reading generated Scala directly.

## Relationship To 02.a

`02-crud` stops at generated CRUD surface inspection.

`02.a-crud-seed-import-lab` extends this line with:

- descriptor-first runtime metadata
- seed data import
- runtime `load` / `search` verification

## Summary

Use `02-crud` as the first checkpoint for the `02-*` line:

- the model generates the CRUD surface
- `bin/cncf` exposes that surface directly
- later labs add data, runtime behavior, and storage-specific concerns
