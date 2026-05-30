# 03-operation

## Overview

`05-operation` is the base operation contract sample.

It is the first sample in the `03` line and focuses on CML operation modeling itself:

- `SERVICE`
- `OPERATION`
- `TYPE`
- `INPUT`
- `OUTPUT`
- `SUMMARY`
- `DESCRIPTION`

This sample is intentionally small.
It is not about CRUD, entity persistence, or command behavior.
It is about the user-facing operation contract surface that later samples build on.

## Position

- `04.f-crud-nested-value-lab`
  - typed CRUD/value modeling just before operation contract
- `05-operation`
  - operation contract modeling in CML
- `06-cqrs`
  - command/query separation built on operation contracts

## Intended Use Case

Use this sample when you want to confirm:

- how to declare one service and one operation in CML
- how `QUERY` operation contracts are exposed through CNCF metadata
- how the generated help surface reflects `input_type` and `output_type`

Typical use cases are:

- starting the `03` line from the smallest possible operation contract
- checking operation naming and selector generation before adding runtime behavior
- teaching the shape of `SERVICE > OPERATION > INPUT/OUTPUT`

## Files

- `src/main/cozy/operation-contract.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/05-operation
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev --component-dev-dir .` will use later.

```bash
$ cd samples/05-operation
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/05-operation
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

### Operation Help

```bash
$ cncf dev command --project . --component-dev-dir . help operation-contract-sample.greeting.greeting
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `operation-contract-sample.greeting.greeting`
  - selects the generated operation

Output example:

```yaml
type: operation
name: greeting
summary: Operation: Greeting.greeting
component: OperationContractSample
service: Greeting
selector:
  cli: operation-contract-sample.greeting.greeting
returns:
  - GreetingResult
```

This confirms the user-facing contract surface:

- service name: `Greeting`
- operation name: `greeting`
- output type: `GreetingResult`

### Metadata Describe

```bash
$ cncf dev command --project . --component-dev-dir . operation-contract-sample.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `operation-contract-sample.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - asks for structured YAML output

Output example:

```yaml
services:
- name: Greeting
  runtime_name: greeting
operation_definitions:
- name: greeting
  kind: QUERY
  input_type: GreetingQuery
  output_type: GreetingResult
  input_value_kind: QUERY_VALUE
```

This is the main confirmation line of the sample.
It shows how the modeled operation contract appears in generated CNCF metadata.

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- entity integration
- CRUD generation
- command execution behavior
- job behavior

Those concerns are covered by the later `03.a`, `03.b`, and `04-*` lines.
