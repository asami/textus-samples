# 08.b-simpleentity-view-lab

## Overview

This sample extends the `08-view` line with a `SimpleEntity`-based entity model.

Its purpose is to show that the default generated `ENTITY > VIEW` read surface still works
when the concrete entity inherits common view-facing fields from `SimpleEntity`.

## Position

- `08-view`
  - default generated view load/search
- `08.a-view-definition-lab`
  - named view definition
- `08.b-simpleentity-view-lab`
  - `SimpleEntity` inheritance on the default view line
- `08.c-view-cache-lab`
  - paged view search for UI lists

## Intended Use Case

This sample is intended for a model where the concrete entity reuses the standard
`SimpleEntity` field set and still needs the normal view read path.

Typical examples are:

- a detail screen that loads one view record from a `SimpleEntity`-based entity
- a finder/search screen that queries by a user-facing field such as `name`
- a project that wants inherited common fields without giving up the default generated view service

The point of this sample is not named view aliases or cache policy.
The point is to make the shell-visible shape of the default view line obvious when the source entity inherits common fields.

## First Completion Line

The first completion line is:

1. one `SimpleEntity`-based view sample model exists
2. one view load route works
3. one view search route works
4. the README explains how inherited `SimpleEntity` fields appear on the view line

## Status

Implemented to the first `SimpleEntity` view line.

## How To Run

### 1. Prepare The Cozy Command

Before running the sample, prepare the `cozy` launcher that sample generation uses.

```bash
$ cd samples/08.b-simpleentity-view-lab
$ ../../bin/setup cozy
```

### 2. Run The Whole Scenario

If you want the entire scenario in one shot, use:

```bash
$ cd samples/08.b-simpleentity-view-lab
$ bash run.sh
```

This executes the same command sequence described below.

## Command Walkthrough

The sections below explain the shell commands that `run.sh` executes.

### 1. Inspect The Available View Load Command

Start by asking CNCF how the generated view load operation is exposed on the CLI.

```bash
$ cd samples/08.b-simpleentity-view-lab

$ bash ../../bin/cncf --discover=classes \
  command help simple-entity-view-sample.view.load-person
```

Example output:

```text
type: operation
name: loadPerson
summary: Operation: view.loadPerson
component: SimpleEntityViewSample
service: view
selector:
  canonical: SimpleEntityViewSample.view.loadPerson
  cli: simple-entity-view-sample.view.load-person
  rest: /simple-entity-view-sample/view/load-person
  accepted:
    - SimpleEntityViewSample.view.loadPerson
arguments:
returns:
  - Option[Person]
usage:
  - command simple-entity-view-sample.view.load-person
```

This confirms:

- the component selector
- the `view` service selector
- the generated load operation
- that the inherited `SimpleEntity` line is still exposed as a normal CNCF shell command

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `help`
  - asks CNCF to describe the target command instead of executing it
- `simple-entity-view-sample.view.load-person`
  - identifies the specific operation to describe

### 2. Load One View Record By Id

Next, load one projected person record by id.

```bash
$ bash ../../bin/cncf --discover=classes \
  command simple-entity-view-sample.view.load-person \
  --id tokyo-sales-entity-person-1742198400000-abcd1234
```

Example output:

```text
name: Alice
title: Reader
id: tokyo-sales-entity-person-1742198400000-abcd1234
city: Tokyo
```

This demonstrates:

- the default generated `view` load route
- inherited `SimpleEntity` fields on the returned view record
- that the caller sees the projected read model rather than aggregate internals

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `simple-entity-view-sample.view.load-person`
  - selects the generated view load operation
- `--id ...`
  - supplies the identifier of the projected row to load

Return shape:

- `name`
  - inherited user-facing name field from `SimpleEntity`
- `title`
  - inherited title field from `SimpleEntity`
- `id`
  - the entity/view identifier
- `city`
  - the concrete field defined on `Person`

### 3. Search The View By Name

Finally, search the same view using a query-side condition.

```bash
$ bash ../../bin/cncf --discover=classes \
  command simple-entity-view-sample.view.search-person-record --name Alice
```

Example output:

```text
query:
  condition:
    name: Alice
data:
- name: Alice
  title: Reader
  id: tokyo-sales-entity-person-1742198400000-abcd1234
  city: Tokyo
total_count: 1
fetched_count: 1
```

This demonstrates:

- the default generated view search route
- inherited `SimpleEntity` fields on search results
- structured query output for a simple read-side condition

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `simple-entity-view-sample.view.search-person-record`
  - selects the generated view search operation
- `--name Alice`
  - supplies the query-side search condition

Return shape:

- `query`
  - echoes the structured search condition
- `query.condition.name`
  - shows the name filter used by the search
- `data`
  - lists the projected rows returned by the view search
- `data[*].name`
  - inherited `SimpleEntity` name field
- `data[*].title`
  - inherited `SimpleEntity` title field
- `data[*].id`
  - projected identifier
- `data[*].city`
  - concrete field defined on `Person`
- `total_count`
  - total rows included in the result
- `fetched_count`
  - number of rows returned in this response

### 4. Inspect The Component Metadata

You can also inspect the generated component metadata for this line.

```bash
$ bash ../../bin/cncf --discover=classes \
  command simple-entity-view-sample.meta.describe --format yaml
```

Example output:

```text
type: component
name: SimpleEntityViewSample
origin: builtin
summary: Component SimpleEntityViewSample
services:
- type: service
  name: aggregate
  runtime_name: aggregate
- type: service
  name: entity
  runtime_name: entity
- type: service
  name: meta
  runtime_name: meta
- type: service
  name: system
  runtime_name: system
- type: service
  name: view
  runtime_name: view
aggregates:
- name: person
  entity_name: person
views:
- name: person
  entity_name: person
  view_names: [
    ]
  queries: [
    ]
  source_events: [
    ]
  rebuildable: false
operation_definitions: [
  ]
```

This confirms that the sample keeps the default view line:

- one aggregate definition
- one default view definition
- no named view aliases
- no custom query aliases

## Why This Matters

This line confirms that the default generated `ENTITY > VIEW` surface is not limited to flat entities.

The structural point is:

- source persistence remains in `ENTITY`
- common fields come from `SimpleEntity`
- read access still goes through the generated `view` service
