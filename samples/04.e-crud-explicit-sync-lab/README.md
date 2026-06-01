# 02.e-crud-explicit-sync-lab

## Overview

`04.e-crud-explicit-sync-lab` is the explicit synchronous execution variant of the `02` CRUD line.

It keeps the same generated CRUD surface as `02.d`, but changes the command completion shape:

- the component still runs in server mode
- the client still calls that server over the normal CNCF client path
- the create route explicitly requests synchronous execution
- the create command returns immediately
- the result is not a job id
- the returned `id` can be used immediately for a follow-up load

This sample is not about durable persistence.
It is about showing that a generated server/client CRUD component can opt into an explicit synchronous command path.

## Position

Compared with the earlier CRUD samples:

- `04-crud`
  - shows the generated CRUD surface itself
- `04.a-crud-seed-import-lab`
  - shows seed import and descriptor-first metadata
- `04.b-simpleentity-crud-lab`
  - shows the `SimpleEntity` CRUD variation
- `04.c-crud-sqlite-lab`
  - shows persistence across separate commands through SQLite
- `04.d-crud-server-memory-lab`
  - shows the normal server/client runtime path with memory-backed state and job-based completion
- `04.e-crud-explicit-sync-lab`
  - shows the server/client runtime path with memory-backed state and explicit synchronous completion

## Intended Use Case

Use this sample when you want to confirm:

- that a generated CRUD component can be started in server mode
- that a command route can explicitly request synchronous execution
- that the client receives an immediate create result instead of a job id
- that the returned `id` can be used right away for a same-server-session follow-up load

Typical use cases are:

- confirming an explicit sync route before introducing async job handling
- demonstrating the runtime difference between:
  - default job-backed command execution
  - explicit synchronous command execution
- checking a development-time server/client flow where immediate completion is preferable

## Files

- `src/main/cozy/crud.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run-server.sh`
  - starts the generated component in server mode
- `run-client-create.sh`
  - sends the create command through the client path with explicit synchronous execution
- `run-client-search.sh`
  - loads the created entity from the same server-held memory state
- `run-demo.sh`
  - starts the server, runs the client flow, and stops the server
- `run.sh`
  - batch wrapper for the documented shell commands

## Explicit Sync Runtime

This sample uses the same in-memory server runtime as `02.d`.

The difference is the route-level runtime parameter:

```bash
--textus.command.execution-mode sync-direct-no-job
```

That means:

- the command still goes through the server/client path
- the command is not converted into a background job
- the result comes back immediately
- the returned `id` can be used immediately for a follow-up load

This makes the sample useful for confirming the runtime behavior of an explicit synchronous route without switching to a local-only execution path.

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/04.e-crud-explicit-sync-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev` will use later.

```bash
$ cd samples/04.e-crud-explicit-sync-lab
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/04.e-crud-explicit-sync-lab
$ bash run.sh
```

`run.sh` is only a convenience batch runner.

It is the batch form of the walkthrough below.

The main learning path is still the explicit shell sequence in `Command Walkthrough`.

## Command Walkthrough

This sample uses:

```bash
bash cncf dev command --project . ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked directly through the installed `cncf` launcher
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- `--project .` auto activation:
  - use the locally compiled generated classes under `target/`
  - this is the local sample-friendly way to run the generated component without first packaging and installing a separate artifact
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `server`:
  - start CNCF in persistent server mode
- `client`:
  - send a request to a running CNCF server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Component Help

```bash
$ cncf dev command --project . help crud
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `crud`
  - selects the generated component

Output example:

```yaml
type: component
name: Crud
selector:
  cli: crud
children:
  - entity
operation_definitions:
  - createItem
  - getItem
  - listItems
```

### Entity Service Help

```bash
$ cncf dev command --project . help crud.entity
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `crud.entity`
  - selects the generated entity service

Output example:

```yaml
type: service
name: entity
selector:
  cli: crud.entity
children:
  - createItem
  - loadItem
  - searchItemRecord
```

### Create Help

```bash
$ cncf dev command --project . help crud.entity.create-item
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `crud.entity.create-item`
  - selects the generated create operation

Output example:

```yaml
type: operation
name: createItem
selector:
  cli: crud.entity.create-item
returns:
  - CreateItemResult
```

### Load Help

```bash
$ cncf dev command --project . help crud.entity.load-item
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `crud.entity.load-item`
  - selects the generated load operation used for follow-up confirmation

Output example:

```yaml
type: operation
name: loadItem
selector:
  cli: crud.entity.load-item
returns:
  - Option[Item]
```

### Metadata Describe

```bash
$ cncf dev command --project . crud.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `crud.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - asks for structured YAML output

Output example:

```yaml
services:
- name: entity
  runtime_name: entity
operation_definitions:
- name: createItem
  kind: COMMAND
- name: getItem
  kind: QUERY
- name: listItems
  kind: QUERY
```

### Start The Server

```bash
$ bash run-server.sh
```

Parameters:


Observed readiness line:

```text
Ember-Server service bound to address: [::]:8080
```

### Create Through The Client Path With Explicit Sync

```bash
$ bash run-client-create.sh
```

Parameters:

- `--textus.command.execution-mode sync-direct-no-job`
  - requests explicit synchronous command execution
- `crud.entity.create-item`
  - invokes the generated create operation
- `--name alpha`
  - sets the domain attribute `name`
- `--title Alpha`
  - sets the domain attribute `title`

Output example:

```yaml
id: major-minor-entity-item-1775439094097-6Qx5e9XO21EVeVmgfwoocs
```

The important point is that this result returns immediately and gives the created entity `id`, not a job id.

### Load The Returned Entity Immediately

```bash
$ bash run-client-search.sh major-minor-entity-item-1775439094097-6Qx5e9XO21EVeVmgfwoocs
```

Parameters:

- `crud.entity.load-item`
  - invokes the generated load operation
- `--id ...`
  - uses the `id` returned immediately by the sync create path

Output example:

```yaml
id: major-minor-entity-item-1775439094097-6Qx5e9XO21EVeVmgfwoocs
name_attributes:
  name: alpha
  title: Alpha
```

### Run The Whole Scenario

```bash
$ bash run-demo.sh
```

This script:

- starts the server
- waits for the readiness line
- runs the client create command with explicit sync
- extracts the returned `id`
- runs the follow-up client load against the same server process

## Runtime Difference From 02.d

`02.d` and `02.e` share the same server/client and memory-backed runtime shape.

The key difference is completion behavior:

- `02.d`
  - create returns a job id
  - the client then calls `job-control.job.await-job-result`
- `02.e`
  - create explicitly requests synchronous execution
  - create returns immediately with the created entity `id`
  - no job wait step is involved
