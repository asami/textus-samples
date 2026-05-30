# 04.a-designed-sync-command-lab

## Overview

`06.a-designed-sync-command-lab` shows a command that is synchronous by design.

It is the direct contrast to `06-cqrs`:

- `06-cqrs`
  - the default command shape is job-backed and asynchronous
- `06.a-designed-sync-command-lab`
  - one command is explicitly modeled as synchronous

The key point is that this is not a runtime hack.
The synchronous behavior is part of the application contract.

## CQRS Context

In CNCF, the normal command-side default is:

- submit a state-changing request
- receive a job id
- observe completion through job control

That default exists because cloud architecture usually benefits from:

- asynchronous writes
- eventual consistency
- independent scaling of write and read paths

But not every command should behave that way.

Some commands are intentionally designed to return their result immediately.
CNCF supports that by letting the model declare the execution style explicitly.

This sample shows that narrower point:

- a command still belongs to the command side
- but its execution is deliberately synchronous
- so the caller receives the result directly instead of a job id

## Position

- `06-cqrs`
  - shows the default job-backed command shape
- `06.a-designed-sync-command-lab`
  - shows an explicitly synchronous command
- later samples
  - will show richer command, event, and job behavior

## Intended Use Case

Use this sample when you want to confirm:

- how to declare a synchronous command in CML
- how CNCF exposes that command at the shell
- that the result comes back immediately
- how this differs from the default CQRS command path

Typical use cases are:

- explaining when a command should not become a job
- showing that execution style is part of the modeled contract
- comparing immediate command completion with job-backed completion

## Files

- `src/main/cozy/cqrs.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/06.a-designed-sync-command-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev --component-dev-dir .` will use later.

```bash
$ cd samples/06.a-designed-sync-command-lab
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/06.a-designed-sync-command-lab
$ bash run.sh
```

`run.sh` is only a convenience batch runner.

It is the batch form of the walkthrough below.

The main learning path is still the explicit shell sequence in `Command Walkthrough`.

## Command Walkthrough

This sample uses:

```bash
bash cncf dev command --project . --component-dev-dir . ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked directly through the installed `cncf` launcher
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- `--component-dev-dir .`:
  - use the locally compiled generated classes under `target/`
  - this is the local sample-friendly way to run the generated component without first packaging and installing a separate artifact
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Command Help

```bash
$ cncf dev command --project . --component-dev-dir . help designed-sync.item.create-item
```

Output example:

```yaml
type: operation
name: createItem
service: Item
selector:
  cli: designed-sync.item.create-item
returns:
  - CreateItemResult
```

This confirms the command contract and selector.

### Metadata Describe

```bash
$ cncf dev command --project . --component-dev-dir . designed-sync.meta.describe --format yaml
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

This shows that the operation is still modeled as a command.

### Execute The Designed-Sync Command

```bash
$ cncf dev command --project . --component-dev-dir . designed-sync.item.create-item --name beta --title Beta
```

Output example:

```yaml
name: beta
title: Beta
```

This is the point of the sample:

- the operation is a command
- but the caller receives the result immediately
- there is no job id
- there is no `await-job-result` step

## Why This Matters

`04.a` shows that CNCF does not force all commands into one execution style.

The normal cloud-oriented default is still:

- asynchronous command execution
- job control
- eventual consistency

But when the application contract requires an immediate result, the model can say so directly.

Use sync when the caller really needs the completed result as part of the same interaction.

Typical cases are:

- validation-heavy command APIs where the caller must receive the created or normalized value immediately
- administrative or setup operations where the work is small and bounded
- local coordination commands where eventual consistency would only add noise
- synchronous UI flows where the next step depends on the returned value itself

Do not use designed sync just because the implementation is currently simple.

The default async command shape is still better when:

- the write may become slower later
- the write can fan out to other components
- retries, tracing, and operational visibility matter
- the read side may lag behind by design

## Choosing The Sync Style

Use these samples as a practical guide:

- `06-cqrs`
  - use this when the command should stay job-backed
  - this is the normal cloud-oriented default
- `06.a-designed-sync-command-lab`
  - use this when the command contract itself should be synchronous
  - the model declares the sync behavior explicitly
- `06.b-test-sync-command-lab`
  - use this when you want synchronous execution mainly for testing or local verification
  - this is not the same as making sync part of the application contract

So the rough rule is:

- business contract says immediate result is required
  - use designed sync
- production architecture should stay async/job-backed
  - use the default CQRS command path
- tests or local experiments need simpler observation
  - use test sync

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- job management
- server/client flow
- read-side projection
- event routing
- handwritten runtime customization

Those concerns belong to neighboring samples.
