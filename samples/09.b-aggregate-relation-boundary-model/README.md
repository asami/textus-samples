# 07.b-aggregate-relation-boundary-model

## Overview

This sample explains the modeling distinction between:

- relation kind
- aggregate boundary
- join direction

The key point is that these are different axes.

- relation kind says how strongly one model element is structurally related to another
- boundary says whether the element belongs inside the aggregate transaction boundary
- join says how aggregate assembly finds the related record at read time

## Aggregate Basics In This Pattern

`09-aggregate` showed the basic aggregate shape.

`07.a` contrasted application-join and single-record aggregate persistence.

`07.b` adds one more distinction:

- a related element can be external and still be stronger than a plain association
- an aggregate may need different read-assembly rules for different external relations

In this model:

- `OrderLine`
  - composition
  - internal boundary
- `ShipmentOrder`
  - aggregation
  - external boundary
  - reverse join
- `User`
  - association
  - external boundary
  - direct join

This is why `ShipmentOrder` and `User` are both external, but not equivalent.

## Intended Use Case

Use this sample when you want to explain:

- that relation kind and aggregate boundary are not the same thing
- that external relations can still have different semantic strength
- that aggregate assembly may use different join directions
- how those axes are expressed in CML and carried into generated component metadata

The runtime proof that one aggregate can be assembled with:

- embedded internal members
- reverse-joined external related records
- direct-joined external associated records

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

This generates the Scala sources from `src/main/cozy/order-relation-boundary.cml` and compiles the sample.

## Run The Whole Scenario

```bash
$ bash run.sh
```

This script is the batch form of the walkthrough below.
It focuses on the generated shell surface and metadata for the relation/boundary/join model.

## Command Walkthrough

The commands below use these common conventions:

- `cncf`
  - the standard CNCF CLI entry point
  - in this repository it is invoked directly through the installed `cncf` launcher
  - after a normal installation it is typically available as `cncf`
- `--project .` auto activation
  - tells CNCF to discover generated components from the compiled class directory
- `command`
  - runs one-shot CNCF command execution
- `help`
  - prints the generated contract surface

### 1. Inspect the component surface

```bash
$ cncf dev command --project . help aggregate-relation-boundary-sample
```

This shows that the generated component exposes aggregate, entity, meta, system, and view services.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `help`
  - asks CNCF to print the generated component surface
- `aggregate-relation-boundary-sample`
  - selects the generated component

### 2. Inspect the aggregate service

```bash
$ cncf dev command --project . help aggregate-relation-boundary-sample.aggregate
```

This shows the generated aggregate-oriented operations.
The important point here is that aggregate reads are exposed separately from entity CRUD, because aggregate assembly has to respect relation kind, boundary, and join semantics.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `help`
  - asks CNCF to print the generated service surface
- `aggregate-relation-boundary-sample.aggregate`
  - selects the aggregate service

### 3. Inspect component metadata

```bash
$ cncf dev command --project . aggregate-relation-boundary-sample.meta.describe --format yaml
```

This shows the generated component metadata.
It confirms that the sample exposes aggregate-oriented services and aggregate definitions derived from the model.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `aggregate-relation-boundary-sample.meta.describe`
  - selects the metadata operation for the component
- `--format yaml`
  - requests YAML output

Example result excerpt:

```yaml
name: AggregateRelationBoundarySample
services:
- name: aggregate
- name: entity
aggregates:
- name: order
- name: shipment_order
- name: user
operation_definitions:
- name: loadOrderAggregate
- name: searchOrderAggregate
```

## What To Focus On

When reading this sample, focus on these points:

- relation kind is a modeling axis
- boundary is a different modeling axis
- join direction is yet another axis used for aggregate assembly
- the generated aggregate service is the user-facing read surface for those modeling decisions

## Files

- `src/main/cozy/order-relation-boundary.cml`
- `src/main/scala/org/sample/aggregaterelationboundary/ExternalEntityAliases.scala`
- `build.sbt`
- `run.sh`
