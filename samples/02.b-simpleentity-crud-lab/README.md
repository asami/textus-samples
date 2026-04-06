# 02.b-simpleentity-crud-lab

## Overview

`02.b-simpleentity-crud-lab` is the `SimpleEntity` variant of the base CRUD line.

It keeps the same model-driven CRUD surface as `02-crud`, but the entity model is based on `SimpleEntity`.

This sample stays on the inspection line.
Its main purpose is to show how the generated CRUD surface looks when common `SimpleEntity` attributes are inherited by the model.

In practical enterprise application development, this line is often closer to the default case than the plain `02-crud` line.

## Position

Compared with the earlier CRUD samples:

- `02-crud`
  - shows the base generated CRUD surface
- `02.a-crud-seed-import-lab`
  - adds descriptor-first metadata and imported seed verification
- `02.b-simpleentity-crud-lab`
  - focuses on the `SimpleEntity` inheritance variant of the same generated CRUD surface

## Intended Use Case

Use this sample when you want to confirm:

- how a `SimpleEntity`-based model still generates the same component/service/operation shape
- how the generated selectors look for the `SimpleEntity` variant
- how the runtime metadata still exposes the same CRUD line through CNCF

Typical enterprise-oriented use cases are:

- starting from an entity base class that already carries common lifecycle and audit-related fields
- keeping CRUD modeling simple while still assuming organizational defaults for business records
- using a shared entity shape that can later connect to publication, security, resource, and audit concerns without redesigning the model
- starting from a model shape that can absorb non-functional requirements and quality attributes without forcing a later entity redesign

## Files

- [crud.cml](/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/src/main/cozy/crud.cml)
  - the source model
- [build.sbt](/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/build.sbt)
  - enables `sbt-cozy` generation for the sample
- [run.sh](/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/run.sh)
  - batch wrapper for the documented shell commands

## How To Run

```bash
$ cd samples/02.b-simpleentity-crud-lab
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Component Help

This command shows the generated component surface.

```bash
$ bash ../../bin/cncf --discover=classes command help simple-entity-crud-lab
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `simple-entity-crud-lab`
  - selects the generated component

Output example:

```yaml
type: component
name: SimpleEntityCrudLab
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

### Service Help

This command shows the generated service surface.

```bash
$ bash ../../bin/cncf --discover=classes command help simple-entity-crud-lab.item
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `simple-entity-crud-lab.item`
  - selects the generated `Item` service

Output example:

```yaml
type: service
name: Item
component: SimpleEntityCrudLab
children:
  - createItem
  - getItem
  - listItems
operations:
  - createItem
  - getItem
  - listItems
```

### Operation Help

This command shows one generated operation.

```bash
$ bash ../../bin/cncf --discover=classes command help simple-entity-crud-lab.item.create-item
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `simple-entity-crud-lab.item.create-item`
  - selects the generated operation in CLI form

Output example:

```yaml
type: operation
name: createItem
component: SimpleEntityCrudLab
service: Item
selector:
  canonical: SimpleEntityCrudLab.Item.createItem
  cli: simple-entity-crud-lab.item.create-item
  rest: /simple-entity-crud-lab/item/create-item
returns:
  - CreateItemResult
```

### Metadata Describe

This command shows the generated component metadata.

```bash
$ bash ../../bin/cncf --discover=classes command simple-entity-crud-lab.meta.describe --format yaml
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `simple-entity-crud-lab.meta.describe`
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

## SimpleEntity Note

`SimpleEntity` matters because many production systems do not model business records as isolated bare objects.

They usually need shared attributes and policies such as:

- stable identity
- lifecycle state
- publication-related fields
- security-related fields
- resource and media-related fields
- audit-related fields

In that kind of application, starting from `SimpleEntity` is often the practical default.
It gives the model a reusable enterprise-shaped base without forcing the sample to jump immediately into storage or workflow details.

This is not only about common attributes.
It is also about non-functional requirements and quality attributes that become important in real systems, such as:

- traceability
- auditability
- security reviewability
- operational consistency
- maintainability of shared record policies
- extensibility when lifecycle or publication rules are added later

Without a shared base such as `SimpleEntity`, those concerns tend to be reintroduced ad hoc in each entity model.
This sample exists to show the simpler starting point.

It also matters that CNCF can make practical use of these shared attributes when the application grows.
Once those fields are present in a consistent shape, CNCF-side capabilities can rely on them as needed for:

- metadata exposure
- view generation
- search and filtering behavior
- lifecycle-oriented handling
- future policy-driven extensions around publication, security, and auditing

This sample is not about seed import or stateful CRUD verification.

Its role is narrower:

- keep the generated CRUD surface visible
- keep the shell-first command flow visible
- show the `SimpleEntity` inheritance variant without adding runtime noise

## Summary

Use `02.b-simpleentity-crud-lab` as the `SimpleEntity` variant of the base `02-crud` line.
