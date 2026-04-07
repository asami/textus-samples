# 04.b-test-sync-command-lab

## Overview

`06.b-test-sync-command-lab` shows a command that stays async/job-backed by design,
but can be executed with a synchronous internal mode for test and local verification.

It completes the pair started by `04.a`:

- `04.a`
  - sync is part of the application contract
- `04.b`
  - async remains the application contract
  - sync is used only as a runtime testing/debugging mode

The important point is that the external interface stays job-shaped.

## CQRS Context

In CNCF, the normal command-side default is:

- submit a state-changing request
- receive a job id
- observe completion through job control

That remains the correct production default in most cloud-oriented systems.

But for tests and local verification, the fully async path can be inconvenient:

- every check needs `await-job-result`
- setup becomes noisy
- assertions become harder to read

`04.b` shows the compromise:

- keep the command contract async/job-backed
- force synchronous internal completion through runtime mode
- preserve the external job interface

This is why implicit or test sync is useful:

- production semantics remain unchanged
- tests get simpler internal completion
- the caller-facing contract is still realistic

## Position

- `06-cqrs`
  - default async/job-backed command
- `06.a-designed-sync-command-lab`
  - sync as a design-time business contract
- `06.b-test-sync-command-lab`
  - sync as a runtime testing/debugging aid

## Intended Use Case

Use this sample when you want to confirm:

- how to keep a command async by contract
- how to request test/local synchronous execution through runtime parameters
- that the result still remains job-shaped
- how this differs from `04.a` designed sync

Typical use cases are:

- test fixtures that want simpler command completion
- local debugging of command behavior without changing the model
- explaining the difference between contract sync and test sync

## Files

- `src/main/cozy/cqrs.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands
- `run-default.sh`
  - default async/job-backed execution
- `run-sync.sh`
  - runtime sync override with the same external job interface

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/06.b-test-sync-command-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf --discover=classes` will use later.

```bash
$ cd samples/06.b-test-sync-command-lab
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/06.b-test-sync-command-lab
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

### Command Help

```bash
$ bash ../../bin/cncf --discover=classes command help test-sync.item.create-item
```

Output example:

```yaml
type: operation
name: createItem
service: Item
selector:
  cli: test-sync.item.create-item
returns:
  - CreateItemResult
```

### Metadata Describe

```bash
$ bash ../../bin/cncf --discover=classes command test-sync.meta.describe --format yaml
```

Output example:

```yaml
operation_definitions:
- name: createItem
  kind: COMMAND
  input_type: CreateItem
  output_type: CreateItemResult
  input_value_kind: COMMAND_VALUE
```

The contract is still a normal command contract.

### Default Async / Job-Backed Execution

```bash
$ bash ../../bin/cncf --discover=classes command TestSync.Item.createItem --name beta --title Beta
```

Output example:

```text
cncf-job-job-...
```

Envelope form:

```bash
$ bash ../../bin/cncf --discover=classes command TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml
```

Output example:

```yaml
textus-execution:
  interface-shape: job
data:
  job-id: cncf-job-job-...
```

### Test Sync Override

```bash
$ bash ../../bin/cncf --discover=classes command --textus.runtime.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta
```

Output example:

```text
cncf-job-job-...
```

Envelope form:

```bash
$ bash ../../bin/cncf --discover=classes command --textus.runtime.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml
```

Output example:

```yaml
textus-execution:
  interface-shape: job
  requested-mode: sync-job-async-interface
data:
  job-id: cncf-job-job-...
```

This is the key point:

- the interface is still job-shaped
- the model is still async by contract
- only the runtime execution mode changes

## When To Use This

Use `04.b` when:

- production should keep async/job-backed semantics
- tests want easier completion semantics
- local debugging should avoid full async waiting logic

Do not use this when the business contract itself requires immediate completion.
That is the role of `04.a`.

So the practical rule is:

- business contract requires sync
  - use designed sync (`04.a`)
- production contract should remain async, but tests need easier execution
  - use test sync (`04.b`)

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- job result retrieval
- server/client flow
- read-side observation
- event routing
- handwritten runtime customization

Those concerns belong to neighboring samples.
