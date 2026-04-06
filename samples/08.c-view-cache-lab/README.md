# 08.c-view-cache-lab

## Overview

This lab extends the `08-view` line toward UI list rendering.

Its purpose is to show that the main read path for list screens is view search,
and that the runtime now treats paged search as a cache-aware path.

## Position

- `08-view`
  - default generated view load/search
- `08.a-view-definition-lab`
  - named view definition
- `08.b-simpleentity-view-lab`
  - `SimpleEntity` variant
- `08.c-view-cache-lab`
  - paged view search for UI lists

## Intended Use Case

This sample is intended for a UI that renders a list screen from a view search.

Typical examples are:

- a people list filtered by city
- an admin screen that pages through a large result set
- a screen that reads nearby pages repeatedly as the user moves through the list

The point of the sample is not a business rule.
The point is the shell-visible shape of:

- view search as the main list-read path
- paging with `query.limit` and `query.offset`
- `query.limit`
- `query.offset`
- repeated nearby page access
- a cache policy that is aware of paged access patterns

In other words, this sample is intended to show both:

1. the UI-facing command shape for paged view search
2. the runtime policy that treats paged search as a cache-aware path instead of an always-cold full search

The broader view line is introduced in [08-view](/Users/asami/src/dev2026/cncf-samples/samples/08-view/README.md).
That sample explains the more basic position that a view provides the read model corresponding to a CQRS query,
with UI-facing consumption as the main intended use case.

This `08.c` sample assumes that baseline and focuses only on:

- paged access
- repeated nearby page access
- paging-aware cache behavior

## First Completion Line

The first completion line is:

1. one view sample is modeled for repeated list access
2. one shell-visible paged search route works with `query.limit`
3. one shell-visible paged search route works with `query.offset`
4. one metadata path explains the list-oriented view line
5. the README explains how to invoke repeated page search from the shell

## Status

Implemented as the first user-facing UI-list view-search sample.

## Setup

### 1. Prepare the `cozy` command

Before running the sample, prepare the `cozy` launcher that sample generation uses.
This step resolves the configured `cozy` version and records a runnable launcher for `bin/cozy`.

```bash
$ cd samples/08.c-view-cache-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf --discover=classes` will use later.

```bash
$ cd samples/08.c-view-cache-lab
$ sbt --batch clean compile
```

## Run The Whole Scenario

If you want the entire scenario in one shot, use:

```bash
$ cd samples/08.c-view-cache-lab
$ bash run.sh
```

`run.sh` is only a convenience batch runner.

It is the batch form of the walkthrough below.

The main learning path is still the explicit shell sequence in `Command Walkthrough`.

## Command Walkthrough

This sample uses:

```bash
bash ../../bin/cncf --discover=classes ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked through `../../bin/cncf`
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- `--discover=classes`:
  - use the locally compiled generated classes under `target/`
  - this is the local sample-friendly way to run the generated component without first packaging and installing a separate artifact
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### 1. Inspect The Available View Search Command

Start by asking CNCF what the paged search operation is and how it is exposed on the CLI.

```bash
$ cd samples/08.c-view-cache-lab

$ ../../bin/cncf --discover=classes \
  command help view-cache-sample.view.search-person-summary-record
```

This confirms:

- the operation name
- the CLI selector
- the response type
- that the sample’s main read path is a view search operation

Example output:

```text
type: operation
name: searchPersonSummaryRecord
summary: Operation: view.searchPersonSummaryRecord
component: ViewCacheSample
service: view
selector:
  canonical: ViewCacheSample.view.searchPersonSummaryRecord
  cli: view-cache-sample.view.search-person-summary-record
  rest: /view-cache-sample/view/search-person-summary-record
  accepted:
    - ViewCacheSample.view.searchPersonSummaryRecord
arguments:
returns:
  - SearchResult[Person]
usage:
  - command view-cache-sample.view.search-person-summary-record
```

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-cache-sample.view.search-person-summary-record`
  - identifies the specific operation to describe
  - `view-cache-sample`
    - the component selector
  - `view`
    - the service selector
  - `search-person-summary-record`
    - the operation selector

### 2. Read The First Page

The first search requests the first page of Tokyo rows with a page size of two.

```bash
$ ../../bin/cncf --discover=classes \
  command view-cache-sample.view.search-person-summary-record --city Tokyo --query.limit 2 --query.offset 0
```

Observed result:

- `Emma`
- `Bella`

This is the starting point for a UI list screen.

Example output:

```text
query:
  condition:
    city: Tokyo
  where:
    op: eq
    path: city
    value: Tokyo
  limit: 2
  offset: 0
data:
- id: tokyo-sales-entity-person-1742198400000-aa05
  name: Emma
  city: Tokyo
  title: Lead
- id: tokyo-sales-entity-person-1742198400000-aa02
  name: Bella
  city: Tokyo
  title: Analyst
total_count: 2
offset: 0
limit: 2
fetched_count: 2
```

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-cache-sample.view.search-person-summary-record`
  - selects the summary view search operation
- `--city Tokyo`
  - becomes the query-side filter value for this sample
- `--query.limit 2`
  - becomes the requested page size
- `--query.offset 0`
  - becomes the starting row position

### 3. Read The Next Overlapping Page

The second search moves the offset by one so that the next page overlaps the previous page.

```bash
$ ../../bin/cncf --discover=classes \
  command view-cache-sample.view.search-person-summary-record --city Tokyo --query.limit 2 --query.offset 1
```

Observed result:

- `Bella`
- `Alice`

This shows the shell-level shape of nearby page access that a UI often performs.

Example output:

```text
query:
  condition:
    city: Tokyo
  where:
    op: eq
    path: city
    value: Tokyo
  limit: 2
  offset: 1
data:
- id: tokyo-sales-entity-person-1742198400000-aa02
  name: Bella
  city: Tokyo
  title: Analyst
- id: tokyo-sales-entity-person-1742198400000-aa01
  name: Alice
  city: Tokyo
  title: Reader
total_count: 2
offset: 1
limit: 2
fetched_count: 2
```

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-cache-sample.view.search-person-summary-record`
  - keeps the same search route as page 1
- `--city Tokyo`
  - keeps the same city filter
- `--query.limit 2`
  - keeps the same page size
- `--query.offset 1`
  - shifts the page start by one row

### 4. Read The Third Overlapping Page

The third search continues the same pattern.

```bash
$ ../../bin/cncf --discover=classes \
  command view-cache-sample.view.search-person-summary-record --city Tokyo --query.limit 2 --query.offset 2
```

Observed result:

- `Alice`
- `Diana`

At this point the sample demonstrates repeated nearby paging through a view search route.

Example output:

```text
query:
  condition:
    city: Tokyo
  where:
    op: eq
    path: city
    value: Tokyo
  limit: 2
  offset: 2
data:
- id: tokyo-sales-entity-person-1742198400000-aa01
  name: Alice
  city: Tokyo
  title: Reader
- id: tokyo-sales-entity-person-1742198400000-aa04
  name: Diana
  city: Tokyo
  title: Designer
total_count: 2
offset: 2
limit: 2
fetched_count: 2
```

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-cache-sample.view.search-person-summary-record`
  - keeps the same search route as the earlier pages
- `--city Tokyo`
  - keeps the same city filter
- `--query.limit 2`
  - keeps the same page size
- `--query.offset 2`
  - shifts the page start to the third nearby position

### 5. Inspect The Generated Component Metadata

Finally, inspect the component metadata exposed by the sample.

```bash
$ ../../bin/cncf --discover=classes \
  command view-cache-sample.meta.describe --format yaml
```

This confirms:

- the component name
- the available services
- the `person` view
- the `summary` named view
- the declared query `search_by_city`

Example output:

```yaml
type: component
name: ViewCacheSample
origin: builtin
summary: Component ViewCacheSample
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
  - summary
  queries:
  - name: search_by_city
    expression: person.city == query.city
  source_events: [
    ]
  rebuildable: false
operation_definitions: [
  ]
```

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-cache-sample.meta.describe`
  - asks the component to describe itself
- `--format yaml`
  - makes the description easier to inspect from the shell
- `view-cache-sample.meta.describe`
  - targets the metadata description operation
  - `view-cache-sample`
    - the component selector
  - `meta`
    - the metadata service
  - `describe`
    - the operation that explains the component definition

The sample script may still use isolated workspaces internally for deterministic verification,
but the user-facing command line does not require that parameter.

## Runtime Cache Policy

The current runtime line behind this sample is:

- view load is cached by id
- view search with `offset/limit` is cached by query condition and chunk
- `queryChunkSize` is a variation point
- search without `offset/limit` is cached only when the result size is at most `queryChunkSize`
- entity writes invalidate cached view results

The technical proof for cache hit/miss behavior is intentionally not carried by this sample.
That proof belongs in framework specs and `cozy` scripted tests.
