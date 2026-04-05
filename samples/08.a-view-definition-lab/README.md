# 08.a-view-definition-lab

## Overview

This lab is the first extension line after `08-view`.

Its purpose is to show explicit named view-definition metadata, especially:

- named view aliases such as `summary` and `detail`
- explicit `VIEW` metadata beyond the default generated view
- custom `VIEW > QUERY` aliases
- metadata such as `EVENTS` and `REBUILDABLE`

## Position

- `08-view`
  - default generated view load/search
- `08.a-view-definition-lab`
  - named view definition

## Intended Use Case

This sample is intended for a read model that needs more than the default generated view.

Typical examples are:

- a list screen that uses a compact summary projection
- a detail screen that needs a projection-fixed load route
- a query alias that should be exposed as a named read path

The point of the sample is not a business rule.
The point is the shell-visible shape of:

- named view aliases
- projection-fixed `summary` and `detail` routes
- custom `VIEW > QUERY` aliases
- metadata that describes how those named views are exposed

The basic view/read-model line is introduced in [08-view](/Users/asami/src/dev2026/cncf-samples/samples/08-view/README.md).
This `08.a` sample assumes that baseline and focuses on explicit named view definition.

## First Completion Line

The first completion line is:

1. one named view model exists
2. one projection-fixed summary/detail generated type exists
3. one projection-fixed summary/detail load route works
4. one projection-fixed summary search route works
5. one custom `VIEW > QUERY` route works
6. one metadata projection shows explicit `VIEW` metadata
7. the README explains how this differs from the default `08-view` line

## Status

Implemented to the first named-view-definition line.

## How To Run

### 1. Prepare The Cozy Command

Before running the sample, prepare the `cozy` launcher that sample generation uses.

```bash
$ cd samples/08.a-view-definition-lab
$ ../../bin/setup cozy
```

### 2. Run The Whole Scenario

If you want the entire scenario in one shot, use:

```bash
$ cd samples/08.a-view-definition-lab
$ bash run.sh
```

This executes the same command sequence described below.

## Command Walkthrough

The sections below explain the shell commands that `run.sh` executes.

### 1. Inspect The Projection-Fixed Summary Load Command

Start by asking CNCF how the named summary load route is exposed on the CLI.

```bash
$ cd samples/08.a-view-definition-lab

$ bash ../../bin/cncf --discover=classes \
  command help named-view-sample.view.load-person-summary
```

Example output:

```text
type: operation
name: loadPersonSummary
summary: Operation: view.loadPersonSummary
component: NamedViewSample
service: view
selector:
  canonical: NamedViewSample.view.loadPersonSummary
  cli: named-view-sample.view.load-person-summary
  rest: /named-view-sample/view/load-person-summary
  accepted:
    - NamedViewSample.view.loadPersonSummary
arguments:
returns:
  - Option[Person]
usage:
  - command named-view-sample.view.load-person-summary
```

This confirms:

- the component selector
- the `view` service selector
- the projection-fixed summary route
- that named view definition becomes a shell-visible command path

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `help`
  - asks CNCF to describe the target command instead of executing the operation
- `named-view-sample.view.load-person-summary`
  - identifies the specific operation to describe
  - `named-view-sample`
    - the component selector
  - `view`
    - the service selector
  - `load-person-summary`
    - the operation selector

Return shape:

- `type`
  - identifies that the selector resolves to an operation
- `name`
  - shows the generated operation name
- `summary`
  - gives the short operation description
- `component`
  - shows the owning component
- `service`
  - shows that this operation belongs to the `view` service
- `selector`
  - shows canonical, CLI, and REST selectors for the same operation
- `arguments`
  - shows the accepted command arguments
- `returns`
  - shows the return type seen by the caller
- `usage`
  - shows the shell invocation shape

### 2. Load The Summary Projection By Id

Next, load one projected summary record by id.

```bash
$ bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.load-person-summary \
  --id tokyo-sales-entity-person-1742198400000-abcd1234
```

Example output:

```text
id: tokyo-sales-entity-person-1742198400000-abcd1234
name: Alice
city: Tokyo
title: Reader
```

This demonstrates:

- the `summary` projection is bound to its own load route
- the caller receives projection data through the named view path
- the sample can expose a projection-fixed route without custom handwritten repository code

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `named-view-sample.view.load-person-summary`
  - selects the generated summary load operation
- `--id ...`
  - supplies the view/entity identifier to load

Return shape:

- `id`
  - the identifier of the projected summary record
- `name`
  - the projected person name
- `city`
  - the projected city value
- `title`
  - the projected title value

### 3. Search The Summary Projection

Search the summary projection by city.

```bash
$ bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.search-person-summary-record --city Tokyo
```

Example output:

```text
query:
  condition:
    city: Tokyo
data:
- id: tokyo-sales-entity-person-1742198400000-abcd1234
  name: Alice
  city: Tokyo
  title: Reader
total_count: 1
fetched_count: 1
```

This demonstrates:

- a projection-fixed summary search route
- structured search output through the named view path
- that named summary views can be used as ordinary UI-facing search routes

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `named-view-sample.view.search-person-summary-record`
  - selects the generated summary search operation
- `--city Tokyo`
  - supplies the query-side search condition

Return shape:

- `query`
  - echoes the structured search condition that CNCF executed
- `query.condition.city`
  - shows the city filter that selected the projected row
- `data`
  - lists the projected summary rows returned by the search
- `data[*].id`
  - the identifier of each projected row
- `data[*].name`
  - the projected person name
- `data[*].city`
  - the projected city value
- `data[*].title`
  - the projected title value
- `total_count`
  - the total number of rows included in this search result
- `fetched_count`
  - the number of rows returned in this response

### 4. Run The Custom View Query Alias

Run the custom `VIEW > QUERY` alias that filters by city.

```bash
$ bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.search-person --view search_by_city --city Tokyo
```

Example output:

```text
query:
  condition:
    city: Tokyo
data:
- id: tokyo-sales-entity-person-1742198400000-abcd1234
  name: Alice
  city: Tokyo
  title: Reader
total_count: 1
fetched_count: 1
```

This demonstrates:

- named query aliases are part of the view surface
- `--view search_by_city` selects the explicit `VIEW > QUERY` metadata route
- the alias can be invoked as a shell-visible command without custom glue code

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `named-view-sample.view.search-person`
  - selects the generic named-view search route
- `--view search_by_city`
  - selects the configured query alias
- `--city Tokyo`
  - supplies the city value consumed by the alias expression

Return shape:

- `query`
  - echoes the structured search condition used by the alias route
- `query.condition.city`
  - shows the city filter selected by the alias route
- `data`
  - lists the projected rows returned by the alias route
- `data[*].id`
  - the identifier of each projected row
- `data[*].name`
  - the projected person name
- `data[*].city`
  - the projected city value
- `data[*].title`
  - the projected title value
- `total_count`
  - the total number of rows included in this search result
- `fetched_count`
  - the number of rows returned in this response

### 5. Load The Detail Projection By Id

Load the projection-fixed detail route for the same person.

```bash
$ bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.load-person-detail \
  --id tokyo-sales-entity-person-1742198400000-abcd1234
```

Example output:

```text
id: tokyo-sales-entity-person-1742198400000-abcd1234
name: Alice
city: Tokyo
title: Reader
```

This demonstrates:

- the `detail` projection is exposed through its own load route
- projection-fixed named views can coexist with the summary route

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `named-view-sample.view.load-person-detail`
  - selects the generated detail load operation
- `--id ...`
  - supplies the view/entity identifier to load

Return shape:

- `id`
  - the identifier of the projected detail record
- `name`
  - the projected person name
- `city`
  - the projected city value
- `title`
  - the projected title value

### 6. Inspect The Named View Metadata

Finally, inspect the metadata that describes the named view line.

```bash
$ bash ../../bin/cncf --discover=classes \
  command named-view-sample.meta.describe --format yaml
```

Example output:

```text
type: component
name: NamedViewSample
origin: builtin
summary: Component NamedViewSample
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
  view_names:
  - detail
  - summary
  queries:
  - name: search_by_city
    expression: person.city == query.city
  source_events:
  - person.created
  - person.updated
  rebuildable: true
operation_definitions: [
  ]
```

This demonstrates:

- named views are part of the component metadata
- query aliases are part of the view metadata
- `EVENTS` and `REBUILDABLE` are visible in the external metadata surface

Command parameters:

- `--discover=classes`
  - tells CNCF to discover the generated classes from the sample build
- `command`
  - selects CNCF command execution mode
- `named-view-sample.meta.describe`
  - selects the component metadata operation
- `--format yaml`
  - requests YAML/text-friendly structured output

Return shape:

- `services`
  - lists the available services exposed by the component
- `views`
  - lists the configured view definitions
- `views[*].view_names`
  - shows the named aliases such as `detail` and `summary`
- `views[*].queries`
  - shows the configured query aliases such as `search_by_city`
- `views[*].source_events`
  - shows the event sources associated with the view
- `views[*].rebuildable`
  - shows whether the named view is marked rebuildable

## Difference From 08-view

`08-view` demonstrates the default generated read-model projection.

This lab adds explicit named view-definition metadata:

- `VIEWS :: summary, detail`
- `EVENTS :: person.created, person.updated`
- `REBUILDABLE :: true`
- `QUERY > searchByCity`

At this line, named views are exposed in two ways:

- projection-fixed operations such as `load-person-summary` and `load-person-detail`
- generic named search with a query alias such as `--view search_by_city`

## Runtime Cache

The current runtime line also includes view cache behavior:

- repeated summary/detail load and search may be served from the runtime view cache
- entity writes invalidate cached view results
- named view aliases and custom view queries keep the same external surface
