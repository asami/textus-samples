# 02.d-crud-server-memory-lab

## Overview

`04.d-crud-server-memory-lab` is the server/client runtime variant of the `02` CRUD line.

It keeps the same generated CRUD surface as `04-crud`, but changes the runtime shape:

- the component runs as a server
- the client calls that server over the normal CNCF client path
- created state lives in the server-side in-memory runtime
- the confirmation flow uses the normal job result path

This sample is not about durable persistence.
It is about the normal asynchronous command flow in a server/client runtime.

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
  - shows the server/client runtime path with memory-backed state and job-based completion

## Intended Use Case

Use this sample when you want to confirm:

- that a generated CRUD component can be started in server mode
- that the client receives a job id for a normal command route
- that `job-control.job.await-job-result` returns the created entity id
- that a later client load can observe the same server-held memory state

Typical use cases are:

- checking the default asynchronous command behavior before adding a durable datastore
- verifying server/client wiring in local development
- demonstrating the difference between:
  - in-process command execution
  - server/client command execution
  - persisted state through SQLite or another datastore

## Files

- `src/main/cozy/crud.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run-server.sh`
  - starts the generated component in server mode
- `run-client-create.sh`
  - sends the create command through the client path
- `run-client-await.sh`
  - waits for the created job result and returns the created entity id
- `run-client-load.sh`
  - loads the created entity from the same server-held memory state
- `run-demo.sh`
  - starts the server, runs the client flow, and stops the server
- `run.sh`
  - batch wrapper for the documented shell commands

## Memory-Backed Runtime

This sample uses the normal in-memory server runtime.

That means:

- no SQLite file is involved
- no external database process is required
- state is shared while the same server process is alive
- state disappears when the server stops

So this lab is useful for runtime-shape verification, but not for durable persistence checks.

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/04.d-crud-server-memory-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev` will use later.

```bash
$ cd samples/04.d-crud-server-memory-lab
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/04.d-crud-server-memory-lab
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
operationDefinitions:
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

### Await Job Result Help

```bash
$ cncf dev command --project . help job-control.job.await-job-result
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `job-control.job.await-job-result`
  - selects the builtin job result wait operation

Output example:

```yaml
type: operation
name: await_job_result
selector:
  cli: job-control.job.await-job-result
arguments:
  - id
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

### Create Through The Client Path

```bash
$ bash run-client-create.sh
```

Parameters:

- `crud.entity.create-item`
  - sends the generated create command to the running server
- `--name alpha`
  - domain attribute for the created record
- `--title Alpha`
  - domain attribute for the created record

Output example:

```text
cncf-job-job-1775436410587-49qikeg2etodmCJPFdWyt4
```

This is the important difference from the explicit sync sample:
the normal server/client command path returns a job id first.

### Await The Created Entity Id

```bash
$ bash run-client-await.sh cncf-job-job-1775436410587-49qikeg2etodmCJPFdWyt4
```

Parameters:

- `job-control.job.await-job-result`
  - waits for the server-side command to finish
- `--id ...`
  - the job id returned by `crud.entity.create-item`

Output example:

```json
{"id":"major-minor-entity-item-1775436410635-48fDQHc6khJwRiCgUcYgxG"}
```

This is the bridge between the asynchronous command path and the later entity load.

### Load The Created Record From The Same Server Memory State

```bash
$ bash run-client-load.sh major-minor-entity-item-1775436410635-48fDQHc6khJwRiCgUcYgxG
```

Parameters:

- `crud.entity.load-item`
  - invokes the generated load route through the client path
- `--id ...`
  - the entity id returned from `await-job-result`

Output example:

```json
{"id":"major-minor-entity-item-1775436410635-48fDQHc6khJwRiCgUcYgxG","name_attributes":{"name":"alpha","title":"Alpha"}}
```

This confirms that:

- the create command completed on the server
- the created id was returned from the job result path
- the same server-side in-memory state is visible to a later client load

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
  - invokes the generated metadata route
- `--format yaml`
  - requests YAML output

Output example:

```yaml
services:
- runtime_name: entity
operation_definitions:
- name: createItem
- name: getItem
- name: listItems
```

## Runtime Difference From 02.c

`04.c-crud-sqlite-lab` proves persistence across separate commands by reusing one SQLite file.

`04.d-crud-server-memory-lab` proves something different:

- the create and load happen through a running server
- the client sees the normal job-based command shape
- the state remains visible only while the same server process is alive

If you need durable cross-command persistence, use `02.c`.
If you need to understand the default server/client command lifecycle, use `02.d`.
