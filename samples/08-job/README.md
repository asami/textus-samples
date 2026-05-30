# 06-job

## Purpose

This sample is the first dedicated job-management sample after `07.a-event-job-trace-lab`.

Its purpose is to show the CNCF job surface directly:

- one command submits job-backed work
- one selector waits for completion
- one selector reads the stored result
- one selector reads summarized status
- one selector reads the job timeline

Unlike `05.a`, this sample is no longer about event-triggered reaction.

It focuses directly on job observation itself.

## Why It Matters

Earlier samples already showed that CNCF command execution is often job-backed.

What was still missing was a sample that treats job management itself as the main topic.

This sample fills that gap.

It shows how CNCF expects the user to interact with job-managed work:

- submit the command
- capture the returned job id
- use `job-control` to observe completion, result, status, and history

## CNCF Approach

CNCF treats job management as a built-in operational surface instead of an afterthought.

The approach is:

- command-side work is submitted through an ordinary modeled selector
- the runtime returns a job id
- `job-control` provides standard selectors for waiting, reading result, reading summarized status, and reading history

This lets the user observe asynchronous work without relying on framework-internal APIs.

## Intended Use Case

Use this sample when you want to explain:

- how a job-backed command is submitted from the shell
- how to wait for job completion without writing custom polling code
- how to inspect result, status, and timeline through standard CNCF selectors
- how `job-control` becomes the operational interface for asynchronous command execution

## Files

- `src/main/cozy/job.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Model

- component: `job-sample`
- service: `item`
- command selector: `job-sample.item.create-item`
- job selectors:
  - `job-control.job.await-job-result`
  - `job-control.job.get-job-result`
  - `job-control.job.get-job-status`
  - `job-control.job.load-job-history`

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev --component-dev-dir .` will use later.

```bash
sbt --batch clean compile
```

## Run The Whole Scenario

```bash
bash run.sh
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
- `server`:
  - start CNCF in persistent server mode
- `client`:
  - send a request to a running CNCF server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### 1. Inspect the component surface

Start by confirming that the generated component exposes the item command and standard support services.

```bash
cncf dev command --project . --component-dev-dir . help job-sample
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it
- `job-sample`
  - selects the generated component

Example output:

```yaml
type: component
name: JobSample
children:
  - Item
  - aggregate
  - entity
  - meta
  - system
  - view
operationDefinitions:
  - createItem
```

### 2. Inspect the job-backed command contract

Next, inspect the actual command that creates job-managed work.

```bash
cncf dev command --project . --component-dev-dir . help job-sample.item.create-item
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it
- `job-sample.item.create-item`
  - selects the job-backed create command

Example output:

```yaml
type: operation
name: createItem
service: Item
selector:
  cli: job-sample.item.create-item
returns:
  - CreateItemResult
```

### 3. Inspect the job-control surface

Confirm that the standard `job-control` selectors are available for this command's lifecycle.

```bash
cncf dev command --project . --component-dev-dir . help job-control.job
cncf dev command --project . --component-dev-dir . help job-control.job.await-job-result
cncf dev command --project . --component-dev-dir . help job-control.job.get-job-result
cncf dev command --project . --component-dev-dir . help job-control.job.get-job-status
cncf dev command --project . --component-dev-dir . help job-control.job.load-job-history
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it
- `job-control.job`
  - selects the standard job-control service
- `job-control.job.await-job-result`
  - selects the wait-until-finished operation
- `job-control.job.get-job-result`
  - selects the stored result reader
- `job-control.job.get-job-status`
  - selects the summarized status reader
- `job-control.job.load-job-history`
  - selects the timeline reader

This is the key point of the sample:

job observation is not a custom sample API.

It is a standard CNCF operational surface.

### 4. Inspect metadata

Use metadata to confirm the modeled runtime shape before starting the server.

```bash
cncf dev command --project . --component-dev-dir . job-sample.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `job-sample.meta.describe`
  - describes the generated runtime metadata
- `--format yaml`
  - requests structured YAML output

Example output:

```yaml
operation_definitions:
- name: createItem
  kind: COMMAND
  input_type: CreateItem
  output_type: CreateItemResult
```

### 5. Start the server

Now start CNCF in server mode so the job state remains available across requests.

```bash
cncf dev server --project . --component-dev-dir .
```

Parameters:
- `server`
  - starts CNCF in persistent server mode so job state remains available across later client requests

Expected signal:

```text
Ember-Server service bound to address: [::]:8080
```

### 6. Submit the job-backed command

From another shell, submit the create command.

```bash
cncf dev client --project . --component-dev-dir . job-sample.item.create-item --name alpha --title Alpha
```

Parameters:
- `client`
  - sends the request to the running local CNCF server
- `job-sample.item.create-item`
  - submits the modeled command through the normal job-backed path
- `--name alpha`
  - field name: `name`
  - meaning: logical item name
  - sample value: `alpha`
- `--title Alpha`
  - field name: `title`
  - meaning: descriptive title
  - sample value: `Alpha`

Expected result example:

```text
cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt
```

### 7. Wait for completion

Use the returned job id to wait until the command finishes.

```bash
cncf dev client --project . --component-dev-dir . job-control.job.await-job-result --id cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt
```

Parameters:
- `client`
  - sends the request to the running local CNCF server
- `job-control.job.await-job-result`
  - waits until the job reaches a final result
- `--id ...`
  - the job id returned by `job-sample.item.create-item`

Expected result example:

```json
{"name":"alpha","title":"Alpha","textus":{"format":"yaml"}}
```

### 8. Read the stored result directly

Read the persisted result again through the explicit result selector.

```bash
cncf dev client --project . --component-dev-dir . job-control.job.get-job-result --id cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt
```

Parameters:
- `client`
  - sends the request to the running local CNCF server
- `job-control.job.get-job-result`
  - reads the stored result payload for the completed job
- `--id ...`
  - the target job id

Expected result example:

```json
{"name":"alpha","title":"Alpha","textus":{"format":"yaml"}}
```

### 9. Read the summarized status

Read the operational summary view for the same job.

```bash
cncf dev client --project . --component-dev-dir . job-control.job.get-job-status --id cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt
```

Parameters:
- `client`
  - sends the request to the running local CNCF server
- `job-control.job.get-job-status`
  - reads the summarized status, result summary, and task state
- `--id ...`
  - the target job id

Important fields:

- `status`
- `result_success`
- `tasks`
- `timeline`
- `debug_request_summary`

Expected result example:

```json
{"job_id":"cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt","status":"Succeeded","result_success":true,"debug_request_summary":"JobSample.Item.createItem"}
```

### 10. Read the job history

Finally, inspect the timeline itself.

```bash
cncf dev client --project . --component-dev-dir . job-control.job.load-job-history --id cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt
```

Parameters:
- `client`
  - sends the request to the running local CNCF server
- `job-control.job.load-job-history`
  - reads the ordered event history for the job lifecycle
- `--id ...`
  - the target job id

Expected result example:

```json
{"job_id":"cncf-job-job-1775515879226-1rYQNxHkvryh5M1PjJ9ADt","offset":0,"limit":100,"total_count":5,"fetched_count":5,"events":[{"sequence":1,"kind":"job.submitted"},{"sequence":2,"kind":"job.running"},{"sequence":3,"kind":"task.running"},{"sequence":4,"kind":"task.succeeded"},{"sequence":5,"kind":"job.succeeded"}]}
```

## Expected Learnings

- how a command becomes job-managed work
- how to wait for completion from the shell
- how to read stored result separately from summarized status
- how to inspect the timeline of the same job
