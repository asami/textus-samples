# 04-cqrs

## Overview

`06-cqrs` is the first sample that shows a visible CQRS split in CNCF.

It makes both sides explicit:

- command side
  - accepts a state-changing request through the generated command surface
- query side
  - reads the current state immediately

This sample uses the generated CNCF surface directly.
It does not need a sample-specific factory.

## CQRS In CNCF

CQRS separates two different concerns:

- commands
  - change system state
- queries
  - read current state

The point is not only API naming.
It is an architectural split between:

- write-side execution
- read-side projection and access

In cloud architecture, this split matters because the write side often needs:

- asynchronous execution
- job control
- eventual consistency
- independent scaling from the read side

CNCF approaches CQRS by making that split visible in the runtime surface itself.

The basic approach is:

- model command-side operations explicitly
- treat command execution policy as part of the modeled command contract
- expose query-side selectors separately
- let the read side observe the state after command completion

This sample covers the first visible step of that approach:

- a modeled command-side contract
- a concrete write-side selector
- generated entity write/read selectors executed in one runtime
- a query-side read after the write

It does not yet try to show:

- aggregate orchestration
- event routing
- projection rebuilding
- distributed CQRS deployment

## Position

- `02-*`
  - CRUD surface and storage variants
- `03-*`
  - operation contracts and operation-to-entity connection
- `06-cqrs`
  - command/query split as an execution model

## Intended Use Case

Use this sample when you want to confirm:

- how CNCF exposes the command side and the query side separately
- that write requests are command-oriented in shape
- that read requests are immediate and query-oriented
- how a single model can provide both command and query selectors

Typical use cases are:

- teaching the visible runtime shape of CQRS
- comparing command-side write and query-side read behavior
- comparing write-side flow and read-side flow in one sample
- showing that the query side can read the state produced by the command side

## Files

- `src/main/cozy/cqrs.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/06-cqrs
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev --component-dev-dir .` will use later.

```bash
$ cd samples/06-cqrs
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/06-cqrs
$ bash run.sh
```

`run.sh` is only a convenience batch runner.

It is the batch form of the walkthrough below.

The main learning path is still the explicit shell sequence in `Command Walkthrough`.

## Command Walkthrough

This sample uses:

```bash
bash cncf dev command --project . --component-dev-dir . ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked directly through the installed `cncf` launcher
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- `--component-dev-dir .`:
  - use the locally compiled generated classes under `target/`
  - this is the local sample-friendly way to run the generated component without first packaging and installing a separate artifact
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Command-Side Help

```bash
$ cncf dev command --project . --component-dev-dir . help cqrs.item.create-item
```

This confirms the modeled command-side operation contract.

Output example:

```yaml
type: operation
name: createItem
service: Item
selector:
  cli: cqrs.item.create-item
returns:
  - CreateItemResult
```

### Entity Write Surface Help

```bash
$ cncf dev command --project . --component-dev-dir . help cqrs.entity.create-item-record
```

This shows the concrete entity write selector used in the executable flow.

Output example:

```yaml
type: operation
name: createItemRecord
service: entity
selector:
  cli: cqrs.entity.create-item-record
returns:
  - unit
```

### Metadata Describe

```bash
$ cncf dev command --project . --component-dev-dir . cqrs.meta.describe --format yaml
```

Output example:

```yaml
services:
- name: Item
  runtime_name: item
operation_definitions:
- name: createItem
  kind: COMMAND
  input_type: CreateItem
  output_type: CreateItemResult
  input_value_kind: COMMAND_VALUE
- name: getItem
  kind: QUERY
  input_type: GetItem
  output_type: ItemResult
  input_value_kind: QUERY_VALUE
```

This is the conceptual CQRS split in the model.

### Execute The Write Side

The executable flow uses `CqrsSampleRunner` so the write and read happen in one embedded CNCF runtime. This keeps the in-memory sample state visible to the read side without packaging or starting an HTTP server.

```bash
$ sbt --batch "runMain org.sample.cqrs.CqrsSampleRunner org-sample-entity-item-1775457600000-gamma111"
```

Output example:

```json
{"created":"id: org-sample-entity-item-1775457600000-gamma111\n","loaded":"id: org-sample-entity-item-1775457600000-gamma111\nname: gamma\ntitle: Gamma\n"}
```

### Execute The Read Side

The runner immediately reads the same entity after the write side completes.

This is the point of the sample:

- the write side is command-oriented
- the read side is immediate
- both sides come from the same modeled component
- the read side can observe the state produced by the write side

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- aggregate-specific semantics
- event routing
- distributed execution
- custom handwritten runtime logic
- seed import

Those concerns belong to later samples.
