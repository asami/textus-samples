# 02-crud

## Overview

`04-crud` is the first model-driven CRUD sample in the CNCF sample line.

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
- how to inspect the generated surface through `cncf`

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

- `src/main/cozy/crud.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/04-crud
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf` will use later.

```bash
$ cd samples/04-crud
$ sbt --batch clean compile
```

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

```bash
$ cd samples/04-crud
$ bash run.sh
```

`run.sh` is only a convenience batch runner. It exists so you can replay the documented command sequence after you understand it.


## Command Walkthrough

This sample uses:

```bash
cncf command ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked directly through the installed `cncf` launcher
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- current-directory project auto activation:
  - use the locally compiled generated classes under `target/`
  - this is the local sample-friendly way to run the generated component without first packaging and installing a separate artifact
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Component Help

This command shows the generated component surface.

```bash
$ cncf command help crud
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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
$ cncf command help crud.item
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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
$ cncf command help crud.item.create-item
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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
$ cncf command crud.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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

`04-crud` stops at generated CRUD surface inspection.

`04.a-crud-seed-import-lab` extends this line with:

- descriptor-first runtime metadata
- seed data import
- runtime `load` / `search` verification

## Summary

Use `04-crud` as the first checkpoint for the `02-*` line:

- the model generates the CRUD surface
- `cncf` exposes that surface directly
- later labs add data, runtime behavior, and storage-specific concerns
