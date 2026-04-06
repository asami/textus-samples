# 03-operation

## Overview

`03-operation` is the base operation contract sample.

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

- `02.f-crud-nested-value-lab`
  - typed CRUD/value modeling just before operation contract
- `03-operation`
  - operation contract modeling in CML
- `04-cqrs`
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

## How To Run

```bash
$ cd samples/03-operation
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Operation Help

```bash
$ bash ../../bin/cncf --discover=classes command help operation-contract-sample.greeting.greeting
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
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
$ bash ../../bin/cncf --discover=classes command operation-contract-sample.meta.describe --format yaml
```

Parameters:

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
