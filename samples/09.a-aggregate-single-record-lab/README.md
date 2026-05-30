# 07.a-aggregate-single-record-lab

## Overview

This sample explains the single-record aggregate pattern as the companion to [07-aggregate](../09-aggregate/README.md).

The contrast is:

- [07-aggregate](../09-aggregate/README.md)
  - application-join aggregate
  - root and members are persisted separately
  - aggregate is assembled at runtime
- `07.a`
  - single-record aggregate
  - the root record embeds member values directly
  - aggregate restore does not need runtime join

## Aggregate Basics In This Pattern

In the single-record pattern:

- `Order` is still the aggregate root
- `OrderLine` is not an independent entity
- `OrderLine` is a value object embedded in the root record
- persistence and aggregate restore both happen through one record

This is often the simpler shape when the member lifecycle is fully owned by the root.

## Intended Use Case

Use this sample when you want to explain:

- when an aggregate can be stored as one record
- how embedded value objects differ from aggregate member entities
- how the generated CNCF surface still exposes aggregate and entity services
- how this pattern differs from the application-join aggregate shown in `09-aggregate`

The runtime assertion that record roundtrip and datastore roundtrip preserve embedded values is kept in `cozy` scripted, not in the sample path.

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

This generates the Scala sources from `src/main/cozy/order-single-record-aggregate.cml` and compiles the sample.

## Run The Whole Scenario

```bash
$ bash run.sh
```

This script is the batch form of the walkthrough below.
It shows the generated component surface and metadata for the single-record aggregate pattern.

## Command Walkthrough

The commands below use these common conventions:

- `cncf`
  - the standard CNCF CLI entry point
  - in this repository it is invoked directly through the installed `cncf` launcher
  - after a normal installation it is typically available as `cncf`
- `--component-dev-dir .`
  - tells CNCF to discover generated components from the compiled class directory
- `command`
  - runs one-shot CNCF command execution
- `help`
  - prints the generated contract surface

### 1. Inspect the component surface

```bash
$ cncf dev command --project . --component-dev-dir . help single-record-sample
```

This shows that the generated component exposes aggregate, entity, meta, system, and view services.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `help`
  - asks CNCF to print the generated component surface
- `single-record-sample`
  - selects the generated component

### 2. Inspect the aggregate load operation

```bash
$ cncf dev command --project . --component-dev-dir . help single-record-sample.aggregate.load-order
```

This shows the aggregate-oriented load surface for the single-record pattern.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `help`
  - asks CNCF to print the generated operation contract
- `single-record-sample.aggregate.load-order`
  - selects the aggregate load operation

### 3. Inspect component metadata

```bash
$ cncf dev command --project . --component-dev-dir . single-record-sample.meta.describe --format yaml
```

This shows the generated component metadata.
It confirms that the sample still has both an aggregate service and an entity service, even though the persistence shape is one record.

Parameters:
- `command`
  - uses one-shot CNCF command execution
- `single-record-sample.meta.describe`
  - selects the metadata operation for the component
- `--format yaml`
  - requests YAML output

Example result excerpt:

```yaml
name: SingleRecordSample
services:
- name: aggregate
- name: entity
aggregates:
- name: order
views:
- name: order
```

## What To Focus On

When reading this sample, focus on these points:

- the aggregate root is still explicit
- the aggregate service still exists as a user-facing surface
- the persistence shape is simpler because `OrderLine` is embedded
- the single-record roundtrip proof is intentionally moved out of the sample path into scripted verification

## Files

- `src/main/cozy/order-single-record-aggregate.cml`
- `build.sbt`
- `run.sh`
