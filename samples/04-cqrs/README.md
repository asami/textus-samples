# 04-cqrs

## Overview

`04-cqrs` is the first sample that shows a visible CQRS split in CNCF.

It makes both sides explicit:

- command side
  - accepts a state-changing request and returns a job id
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
- treat command execution as job-backed by default
- expose query-side selectors separately
- let the read side observe the state after command completion

This sample covers the first visible step of that approach:

- a modeled command-side contract
- a concrete write-side selector
- job submission and `await-job-result`
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
- `04-cqrs`
  - command/query split as an execution model

## Intended Use Case

Use this sample when you want to confirm:

- how CNCF exposes the command side and the query side separately
- that write requests are job-backed and asynchronous in shape
- that read requests are immediate and query-oriented
- how a single model can provide both command and query selectors

Typical use cases are:

- teaching the visible runtime shape of CQRS
- explaining why command completion is observed through job control
- comparing write-side flow and read-side flow in one sample
- showing that the query side can read the state produced by the command side

## Files

- `src/main/cozy/cqrs.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## How To Run

```bash
$ cd samples/04-cqrs
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Command-Side Help

```bash
$ bash ../../bin/cncf --discover=classes command help cqrs.item.create-item
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
$ bash ../../bin/cncf --discover=classes command help cqrs.entity.create-item-record
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
$ bash ../../bin/cncf --discover=classes command cqrs.meta.describe --format yaml
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

Start the server in one shell:

```bash
$ bash ../../bin/cncf --discover=classes server
```

Submit the write request from another shell:

```bash
$ bash ../../bin/cncf --discover=classes client cqrs.entity.create-item-record --id org-sample-entity-item-20260406000000-gamma111 --name gamma --title Gamma
```

Output example:

```text
cncf-job-job-1775464037872-4sq5QOCmaYVVX8Vx2vjT23
```

The write side returns a job id first.

Await the final result:

```bash
$ bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id cncf-job-job-1775464037872-4sq5QOCmaYVVX8Vx2vjT23
```

Output example:

```json
{"id":"org-sample-entity-item-20260406000000-gamma111"}
```

### Execute The Read Side

```bash
$ bash ../../bin/cncf --discover=classes client cqrs.entity.load-item --id org-sample-entity-item-20260406000000-gamma111
```

Output example:

```json
{"id":"org-sample-entity-item-20260406000000-gamma111","name":"gamma","title":"Gamma"}
```

This is the point of the sample:

- the write side is asynchronous and job-backed
- the read side is immediate
- both sides come from the same modeled component
- the read side can observe the state produced by the write side after the job completes

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- aggregate-specific semantics
- event routing
- distributed execution
- custom handwritten runtime logic
- seed import

Those concerns belong to later samples.
