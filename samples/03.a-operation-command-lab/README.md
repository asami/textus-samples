# 03.a-operation-command-lab

## Overview

`03.a-operation-command-lab` is the first command-oriented operation contract sample.

It follows `03-operation`, which fixed the minimal query contract line first.

This sample shows both:

- the generated contract surface of a `COMMAND` operation
- the minimal async command flow of `job id -> await-job-result`

In CQRS terms, this is the first sample on the `C` side.
It shows how a command interface begins at the operation contract level before later samples add processing behavior.

This matters in cloud architecture because scalable update processing is often achieved by making update handling asynchronous and accepting eventual consistency where appropriate.
For that reason, CNCF treats asynchronous job-backed command execution as the default command shape.
Since asynchronous execution requires job tracking, CNCF provides job management as a built-in capability rather than an optional add-on.

## Position

- `03-operation`
  - shows the minimal query-oriented operation contract
- `03.a-operation-command-lab`
  - shows the minimal command-oriented operation contract
- `03.b-operation-entity-lab`
  - will connect operation modeling to an entity-oriented line

## Intended Use Case

Use this sample when you want to confirm:

- how `TYPE = COMMAND` appears in generated CNCF metadata
- how command input and output types are reflected in the help surface
- how command-shaped contracts differ from the earlier query-shaped contract sample
- how the command side of a CQRS-style interface starts from the modeled contract itself
- how a command returns a job id first and then yields the final result through job control

Typical use cases are:

- teaching operation contract modeling before adding runtime behavior
- checking command naming and selector generation
- verifying command-oriented `input_value_kind` and output types
- understanding why CNCF command handling starts from an asynchronous job-capable contract shape
- showing the smallest end-to-end example of async command submission plus synchronous result retrieval

## Files

- `src/main/cozy/operation-command-contract.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## How To Run

```bash
$ cd samples/03.a-operation-command-lab
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Operation Help

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory command help operation-command-contract-sample.greeting.submit-greeting
```

Parameters:

- `--component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory`
  - uses the sample-specific factory that provides the minimal command implementation
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `operation-command-contract-sample.greeting.submit-greeting`
  - selects the generated command operation

Output example:

```yaml
type: operation
name: submitGreeting
summary: Operation: Greeting.submitGreeting
component: OperationCommandContractSample
service: Greeting
selector:
  cli: operation-command-contract-sample.greeting.submit-greeting
returns:
  - GreetingAccepted
```

This confirms the contract surface:

- service name: `Greeting`
- operation name: `submitGreeting`
- output type: `GreetingAccepted`

### Metadata Describe

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory command operation-command-contract-sample.meta.describe --format yaml
```

Parameters:

- `operation-command-contract-sample.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - asks for structured YAML output

Output example:

```yaml
services:
- name: Greeting
  runtime_name: greeting
operation_definitions:
- name: submitGreeting
  kind: COMMAND
  input_type: GreetingCommand
  output_type: GreetingAccepted
  input_value_kind: COMMAND_VALUE
```

This is the main confirmation line of the sample.
It shows how a command-oriented operation contract appears in generated CNCF metadata.
In that sense, it is the first CQRS-`C` sample in the `03` line.

The runtime semantics behind that contract are intentionally not exercised here, but the background assumption is important:

- updates are asynchronous by default
- eventual consistency is an intentional scalability choice
- job management is required for that execution model
- CNCF therefore supports job management as a built-in function

### Command Execution And Await

This sample also shows the smallest concrete async command flow.

Start the server in one shell:

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory server
```

Submit the command from another shell:

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory client operation-command-contract-sample.greeting.submit-greeting --name Alice
```

Output example:

```text
cncf-job-job-1775443773441-7COTU1hJrsGkCKUgPXhiCL
```

This command returns a job id first.
That is the default command shape in CNCF.

Then await the result using the returned job id:

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory client job-control.job.await-job-result --id cncf-job-job-1775443773441-7COTU1hJrsGkCKUgPXhiCL
```

Output example:

```json
{"status":"accepted","name":"Alice"}
```

This is the point of the sample:

- the command side returns a job handle first
- the final result is retrieved through job control
- the synchronous feeling at the caller side is built on top of an async job

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- command execution side effects
- entity integration
- CRUD behavior

Those concerns belong to later samples.
