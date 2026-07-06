# 08-view

## Overview

This sample family is the first dedicated view-oriented slot after `09-aggregate`.

Its purpose is to show that CNCF provides a `view` service as the read side corresponding to a CQRS query,
with UI-facing read access as the main intended use case.

The point of this first line is not cache policy or named-view variations.
The point is to make the basic shell-visible shape of view-oriented read access obvious.

## Intended Use Case

This sample is intended for the simplest read-model use case:

- a screen that loads one projected item by id
- a screen that searches projected items by a query condition
- a read path that is clearly separate from aggregate mutation

Typical examples are:

- a profile/details screen that loads one view record
- a finder/search screen that queries a read model
- a UI or script that needs read-oriented projection data rather than aggregate command handling

## First Completion Line

The first completion line is:

1. one view-oriented sample model exists
2. one view load route works
3. one view search route works
4. the README explains why this is view-oriented rather than aggregate-oriented access

## Status

Implemented to the first view line.

## Current Line

The current first line is:

1. one view-oriented sample model exists
2. one view load route works
3. one view search route works
4. the README explains why this is view-oriented rather than aggregate-oriented access

## Setup

### 1. Prepare the `cncf` and `cozy` commands

Before running the sample, prepare the local CNCF launcher and the `cozy` launcher.

```bash
$ cd samples/10-view
$ ../../bin/setup cncf
$ ../../bin/setup cozy
```

The CNCF setup step is also where the shared runtime library versions can be pinned when needed.

```bash
$ ../../bin/setup cncf \
  --cncf-version 0.4.12 \
  --core-version 0.3.7 \
  --simplemodeling-model-version 0.1.7
```

Shared defaults are read from:

- `../../versions/cncf-version.conf`
- `../../versions/goldenport-core-version.conf`
- `../../versions/simplemodeling-model-version.conf`

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf` will use later.

```bash
$ cd samples/10-view
$ sbt --batch clean compile
```

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

If you want the entire scenario in one shot, use:

```bash
$ cd samples/10-view
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

### 1. Inspect The Available View Load Command

Start by asking CNCF what the generated view load operation looks like on the CLI.

```bash
$ cd samples/10-view

$ cncf command \
  command help view-sample.view.load-person
```

Example output:

```text
type: operation
name: loadPerson
summary: Operation: view.loadPerson
component: ViewSample
service: view
selector:
  canonical: ViewSample.view.loadPerson
  cli: view-sample.view.load-person
  rest: /view-sample/view/load-person
  accepted:
    - ViewSample.view.loadPerson
arguments:
returns:
  - Option[Person]
usage:
  - command view-sample.view.load-person
```

This confirms:

- the component selector
- the `view` service selector
- the generated load operation
- that the first view line is exposed directly as a shell command

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-sample.view.load-person`
  - identifies the specific operation to describe
  - `view-sample`
    - the component selector
  - `view`
    - the service selector
  - `load-person`
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

### 2. Load One View Record By Id

Next, load one projected person record by id.

```bash
$ cncf command \
  command view-sample.view.load-person \
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

- the read-model shape returned by the `view` service
- a shell-visible load route for one projected record
- that the consumer sees projection data, not aggregate internals

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-sample.view.load-person`
  - selects the generated view load operation
- `--id ...`
  - supplies the view/entity identifier to load

Return shape:

- `id`
  - the identifier of the projected view record
- `name`
  - the projected person name
- `city`
  - the projected city value
- `title`
  - the projected title value

### 3. Search The View By Query Condition

Finally, search the same view using a query-side condition.

```bash
$ cncf command \
  command view-sample.view.search-person-record --name Alice
```

Example output:

```text
query:
  condition:
    name: Alice
data:
- id: tokyo-sales-entity-person-1742198400000-abcd1234
  name: Alice
  city: Tokyo
  title: Reader
total_count: 1
fetched_count: 1
```

This demonstrates:

- a query-oriented view search route
- structured search output
- a read path shaped for UI/search use rather than aggregate mutation

Command parameters:

- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `view-sample.view.search-person-record`
  - selects the generated view search operation
- `--name Alice`
  - supplies the query-side search condition

Return shape:

- `query`
  - echoes the structured search condition that CNCF executed
- `query.condition.name`
  - shows the query filter that selected the projected row
- `data`
  - lists the projected view rows returned by the search
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

## Runtime Cache

The current runtime line now includes view cache behavior:

- repeated view load/search may be served from an in-memory view cache
- entity create/save/update/delete invalidates the view cache
- the cache is a runtime optimization only and does not change the view surface

## Why This Is View-Oriented

This sample is not aggregate-oriented because the runtime surface is a read-model projection.
The `view` service loads and searches a generated `Person` view, not an aggregate assembled for mutation.

The structural point is:

- source persistence remains in `ENTITY`
- read access goes through generated `VIEW`
- runtime projection converts entity records into view records before returning them
