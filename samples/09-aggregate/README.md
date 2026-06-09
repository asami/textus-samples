# 09-aggregate

## Overview

This sample is the first aggregate-oriented sample in the series.
It shows how CNCF exposes an aggregate as a shell-facing runtime surface rather than as a same-JVM demo program.

The main line here is:

- create an `Order`
- append an `OrderLine` through an aggregate command
- load the aggregate as one joined result

## Aggregate Basics

An aggregate is a consistency boundary.
It groups one root entity and one or more member entities that should be updated and observed as one business unit.

In this sample line:

- `Order` is the aggregate root
- `OrderLine` is an aggregate member
- the persisted truth remains entity-oriented
- the runtime read result is aggregate-oriented

This matters because application code often needs both of these views:

- entity-oriented persistence and storage control
- aggregate-oriented command and read semantics

The `09-*` line is meant to separate those concerns clearly.
It shows how CNCF can keep entities as the persistence backbone while still exposing aggregate-shaped runtime operations.

## Intended Use Case

Use this sample when you want to explain:

- why an aggregate is different from a plain entity record
- how an aggregate command updates aggregate state through delegated application logic
- how CNCF can expose aggregate load as a shell-facing operation
- how root/member entities remain persisted separately while the read result is aggregate-shaped
- how aggregate search may need a separate visibility-focused line

This sample uses the application-join aggregate pattern.
The aggregate is assembled from multiple persisted entities at runtime.

## Sample Line Guide

The aggregate sample family is split so each sample can focus on one point.

- [09-aggregate](../09-aggregate/README.md)
  - first aggregate line
  - focus on `create -> await -> add-line -> load`
  - this is the entry point for understanding aggregate-shaped shell operations
- [09.a-aggregate-single-record-lab](../09.a-aggregate-single-record-lab)
  - focus on the single-record aggregate encoding pattern
  - use this when the aggregate is persisted as one encoded document or record
- [09.b-aggregate-relation-boundary-model](../09.b-aggregate-relation-boundary-model)
  - focus on relation kind and boundary semantics
  - use this to understand why some members are internal and others must remain external
- [09.c-aggregate-external-update-semantics](../09.c-aggregate-external-update-semantics)
  - focus on updates that cross aggregate boundaries
  - use this to understand when a change should remain outside the current aggregate transaction/consistency boundary

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

This generates the Scala sources from `src/main/cozy/order-aggregate.cml` and compiles the sample.

## Run The Whole Scenario

```bash
$ bash run.sh
```

This script is the batch form of the walkthrough below.
It starts a temporary server, creates one order, appends one line, waits for both jobs, and loads the resulting aggregate.
It does not currently include aggregate search in the main scenario.

## Command Walkthrough

The commands below use these common conventions:

- `cncf`
  - the standard CNCF CLI entry point
  - in this repository it is invoked directly through the installed `cncf` launcher
  - after a normal installation it is typically available as `cncf`
- `--project-dev .` auto activation
  - tells CNCF to discover generated components from the compiled class directory
- `server`
  - starts the CNCF server runtime for this sample
- `client`
  - runs one client-side request against the running server
- `command`
  - runs one-shot command execution without starting the HTTP server
- `help`
  - prints the generated operation contract surface

### 1. Start the server

```bash
$ cncf dev server --project-dev .
```

This starts the sample runtime and loads `AggregateSample` through the preferred impl factory.

Parameters:
- `server`
  - starts the long-running CNCF server process used by the later client commands

### 2. Inspect the aggregate command surface

```bash
$ cncf dev command --project-dev . help aggregate-sample.order.add-line
```

This shows the aggregate command surface that appends one member line to an existing order.

Parameters:
- `command`
  - uses one-shot CNCF command execution for help output
- `help`
  - asks CNCF to print the generated operation contract instead of executing it
- `aggregate-sample.order.add-line`
  - selects the aggregate command that mutates the `Order` aggregate

### 3. Create the root order

```bash
$ cncf dev client --project-dev . aggregate-sample.entity.create-order-record --name alpha --status Draft
```

This creates the root `Order` entity.
Because the operation is job-backed, the immediate return value is a job id.

Parameters:
- `client`
  - sends one request to the running CNCF server
- `aggregate-sample.entity.create-order-record`
  - selects the root entity creation command
- `--name alpha`
  - sets the order name
- `--status Draft`
  - sets the initial root status

Example result:

```text
cncf-job-job-1775531118508-7ejVSMHaZhCHd4nrtg7uPx
```

### 4. Await the create result

```bash
$ cncf dev client --project-dev . job-control.job.await-job-result --id cncf-job-job-1775531118508-7ejVSMHaZhCHd4nrtg7uPx
```

This resolves the job and returns the created order id.

Parameters:
- `client`
  - sends one request to the running CNCF server
- `job-control.job.await-job-result`
  - waits for completion and returns the final job payload
- `--id <job-id>`
  - the job id returned by `create-order-record`

Example result:

```json
{"id":"major-minor-entity-order-1775531118546-EwawSPXnjIymHzu8DVknz"}
```

### 5. Append one aggregate member

```bash
$ cncf dev client --project-dev . aggregate-sample.order.add-line --orderId major-minor-entity-order-1775531118546-EwawSPXnjIymHzu8DVknz --lineName pen --quantity 2
```

This executes the aggregate command.
The application-provided aggregate behavior validates the request and creates the `OrderLine` member.

Parameters:
- `client`
  - sends one request to the running CNCF server
- `aggregate-sample.order.add-line`
  - selects the aggregate command
- `--orderId <order-id>`
  - identifies the aggregate root to update
- `--lineName pen`
  - sets the member line name
- `--quantity 2`
  - sets the member quantity

Example result:

```text
cncf-job-job-1775531146663-5EuFzAH6P2xv3mhEoG9y3j
```

### 6. Load the aggregate

```bash
$ cncf dev client --project-dev . aggregate-sample.order.load-order-aggregate --id major-minor-entity-order-1775531118546-EwawSPXnjIymHzu8DVknz
```

This loads the aggregate-shaped result.
The returned payload contains the root order plus the attached member lines.

Parameters:
- `client`
  - sends one request to the running CNCF server
- `aggregate-sample.order.load-order-aggregate`
  - selects the aggregate load query
- `--id <order-id>`
  - identifies the root order to assemble and load

Example result:

```json
{"id":"major-minor-entity-order-1775531118546-EwawSPXnjIymHzu8DVknz","name":"alpha","status":"Draft","lines":[{"id":"major-minor-entity-order_line-1775531146677-5N2sdVDtUTvtsIJua6wwF4","order_id":"major-minor-entity-order-1775531118546-EwawSPXnjIymHzu8DVknz","name":"pen","quantity":2}]}
```

## Current Scope

The stable first line for this sample is:

- `create-order-record`
- `await-job-result`
- `add-line`
- `load-order-aggregate`

`search-order-aggregate` is outside the current stable line.
It should be revisited together with aggregate visibility semantics.

## Files

- `src/main/cozy/order-aggregate.cml`
- `src/main/scala/org/sample/aggregate/impl/AggregateSampleComponentFactory.scala`
- `build.sbt`
- `run.sh`
