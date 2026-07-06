# 03.a-operation-command-lab

## Overview

`05.a-operation-command-lab` is the first command-oriented operation contract sample.

It follows `05-operation`, which fixed the minimal query contract line first.

This sample shows both:

- the generated contract surface of a `COMMAND` operation
- the minimal async command flow of `job id -> await-job-result`

In CQRS terms, this is the first sample on the `C` side.
It shows how a command interface begins at the operation contract level before later samples add processing behavior.

This matters in cloud architecture because scalable update processing is often achieved by making update handling asynchronous and accepting eventual consistency where appropriate.
For that reason, CNCF treats asynchronous job-backed command execution as the default command shape.
Since asynchronous execution requires job tracking, CNCF provides job management as a built-in capability rather than an optional add-on.

## Position

- `05-operation`
  - shows the minimal query-oriented operation contract
- `05.a-operation-command-lab`
  - shows the minimal command-oriented operation contract
- `05.b-operation-entity-lab`
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

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/05.a-operation-command-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf` will use later.

```bash
$ cd samples/05.a-operation-command-lab
$ sbt --batch clean compile
```

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

```bash
$ cd samples/05.a-operation-command-lab
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
- `--component-factory-class ...`:
  - use the sample-specific factory that provides executable behavior for the minimal walkthrough
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Operation Help

```bash
$ cncf command --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory help operation-command-contract-sample.greeting.submit-greeting
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `--component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory`
  - uses the sample-specific factory that provides the minimal command implementation
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
$ cncf command --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory operation-command-contract-sample.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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
$ cncf server --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory
```

Submit the command from another shell:

```bash
$ cncf client --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory operation-command-contract-sample.greeting.submit-greeting --name Alice
```

Output example:

```text
cncf-job-job-1775443773441-7COTU1hJrsGkCKUgPXhiCL
```

This command returns a job id first.
That is the default command shape in CNCF.

Then await the result using the returned job id:

```bash
$ cncf client --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory job-control.job.await-job-result --id cncf-job-job-1775443773441-7COTU1hJrsGkCKUgPXhiCL
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
