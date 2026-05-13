# 07.c-aggregate-external-update-semantics

## Overview

This sample continues the aggregate line after:

- [07-aggregate](../09-aggregate/README.md)
- [07.a-aggregate-single-record-lab](../09.a-aggregate-single-record-lab/README.md)
- [07.b-aggregate-relation-boundary-model](../09.b-aggregate-relation-boundary-model/README.md)

The focus here is the update side of:

- `aggregation + external`

The key point is that an external aggregated structure can still carry stronger update semantics than a plain external association.

## Aggregate Basics In This Pattern

`07.b` separated:

- relation kind
- aggregate boundary
- join direction

`07.c` keeps that same model and asks a different question:

- when the root aggregate is updated, which external structures follow that semantic line?

In this sample:

- `Order`
  - root aggregate
- `ShipmentOrder`
  - `aggregation + external`
- `User`
  - `association + external`

The intended semantic is:

- `cancelOrder` updates the root `Order`
- the same aggregate-side behavior also updates external `ShipmentOrder`
- the external `User` remains outside that follow-up update

## Intended Use Case

Use this sample when you want to explain:

- that aggregate semantics are not limited to internal members
- that an external aggregated structure can still follow aggregate update semantics
- that a plain external association does not automatically follow those semantics
- how that distinction appears in the generated command contract

The runnable proof that this semantic line actually updates:

- the root `Order`
- the external aggregated `ShipmentOrder`
- but not the associated `User`

is kept in `cozy` scripted, not in the user-facing sample path.

## Setup

### Prepare the cozy command

```bash
$ ../../bin/setup cozy
```

This prepares the local `cozy` launcher used by `sbt-cozy`.

### Build the generated sample

```bash
$ sbt --batch clean compile
```

This generates the Scala sources from `src/main/cozy/order-external-update.cml` and compiles the sample.

## Run The Whole Scenario

```bash
$ bash run.sh
```

This script is the batch form of the walkthrough below.
It focuses on the generated command surface and metadata for external update semantics.

## Command Walkthrough

The commands below use these common conventions:

- `cncf`
  - the standard CNCF CLI entry point
  - in this repository it is invoked as `../../bin/cncf`
  - after a normal installation it is typically available as `cncf`
- `--discover=classes`
  - tells CNCF to discover generated components from the compiled class directory
- `command`
  - runs one-shot CNCF command execution
- `help`
  - prints the generated contract surface

### 1. Inspect the component surface

```bash
$ bash ../../bin/cncf --discover=classes command help aggregate-external-update-sample
```

This shows that the generated component exposes:

- a dedicated `Order` service
- aggregate, entity, meta, system, and view services

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `help`
  - asks CNCF to print the generated component surface
- `aggregate-external-update-sample`
  - selects the generated component

### 2. Inspect the aggregate-facing command

```bash
$ bash ../../bin/cncf --discover=classes command help aggregate-external-update-sample.order.cancel-order
```

This shows the command contract that represents the aggregate-side update semantic.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `help`
  - asks CNCF to print the generated operation contract
- `aggregate-external-update-sample.order.cancel-order`
  - selects the aggregate-facing cancel command

### 3. Inspect component metadata

```bash
$ bash ../../bin/cncf --discover=classes command aggregate-external-update-sample.meta.describe --format yaml
```

This shows the generated metadata.
It confirms that the component exposes:

- one command definition `cancelOrder`
- one aggregate load definition `loadOrderAggregate`
- aggregate-oriented services on top of the model

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `aggregate-external-update-sample.meta.describe`
  - selects the metadata operation for the component
- `--format yaml`
  - requests YAML output

Example result excerpt:

```yaml
services:
- name: Order
- name: aggregate
- name: entity
operation_definitions:
- name: cancelOrder
  kind: COMMAND
- name: loadOrderAggregate
  kind: QUERY
```

## What To Focus On

When reading this sample, focus on these points:

- external aggregated structures can participate in aggregate update semantics
- plain external associations do not automatically follow that same update line
- the aggregate-facing command is the user-facing contract for that distinction
- the runtime proof is intentionally moved to scripted verification

## Files

- `src/main/cozy/order-external-update.cml`
- `src/main/scala/org/sample/aggregateexternalupdate/OrderExternalUpdateFactory.scala`
- `src/main/scala/org/sample/aggregateexternalupdate/ExternalEntityAliases.scala`
- `build.sbt`
- `run.sh`
