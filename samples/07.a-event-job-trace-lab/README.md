# 05.a-event-job-trace-lab

## Purpose

This sample is the bridge between `07-event-driven` and `08-job`.

It keeps the event-driven shape, but it adds job-oriented tracing so the learner can inspect how an emitted event appears in CNCF job state.

This is not yet a distributed messaging lab and not yet a full job-management sample. It is the first shell-first sample that makes the event reaction path visible through `job-control`.

## Why It Matters

After `06-cqrs`, CNCF already showed that command-side work is job-backed by default.

`07-event-driven` showed the visible event surface.

`07.a-event-job-trace-lab` adds the next missing piece:

- one action emits an event
- one event-triggered reaction is observed
- the resulting job state and job timeline are inspected from the shell

This is how CNCF connects event-driven reaction and job tracing:

- the command emits an event
- CNCF routes the reaction
- `job-control` exposes the job result, status, and history
- the visible effect can then be checked through the event-facing query

## CNCF Approach

CNCF is aiming to make event-driven behavior observable without forcing the user into framework-internal APIs.

The approach is:

- emit an event through a modeled command
- execute the routed reaction as job-managed work
- expose job observation through `job-control`
- expose the visible post-event state through ordinary query selectors

This sample covers the middle of that flow:

- submit
- await
- inspect job status and history
- verify the visible effect

## Intended Use Case

Use this sample when you want to explain:

- how an event-triggered reaction appears as job-managed work
- how to trace event-driven execution from the shell
- how CNCF lets you move from event emission to job inspection before going into fuller job-management samples

## Files

- `src/main/cozy/event.cml`
- `build.sbt`
- `run.sh`

## Model

- component: `event-driven`
- service: `Event`
- emitting command: `emitEvent`
- reaction action: `recordEffect`
- observation query: `loadEffect`
- emitted event: `item.changed`

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev` will use later.

```bash
sbt clean compile
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
bash cncf dev command --project-dev . ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked directly through the installed `cncf` launcher
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- `--project-dev .` auto activation:
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

### 1. Inspect the event surface

Start by confirming the user-facing event selectors.

This step answers:

- which component exposes the event flow
- which operation emits the event
- which query reads the visible post-event effect

```bash
cncf dev command --project-dev . help event-driven
cncf dev command --project-dev . help event-driven.event.emit-event
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `help event-driven`: inspect the component-level surface
- `help event-driven.event.emit-event`: inspect the emitting operation

At this point, you should see that the sample exposes:

- `event-driven.event.emit-event`
- `event-driven.event.load-effect`

Example result:

```yaml
type: component
name: EventDriven
children:
  - Event
operationDefinitions:
  - emitEvent
  - loadEffect
  - recordEffect
```

### 2. Inspect the job-trace selectors

Next, confirm that CNCF exposes job-side observation commands for the same flow.

This step answers:

- how to wait for the routed work to finish
- how to inspect the resulting job history from the shell

```bash
cncf dev command --project-dev . help job-control.job.await-job-result
cncf dev command --project-dev . help job-control.job.load-job-history
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `help job-control.job.await-job-result`: inspect the waiting operation
- `help job-control.job.load-job-history`: inspect the timeline-loading operation

The important point here is that event-driven execution is not opaque.

After submission, the user can inspect the same path through `job-control`.

### 3. Inspect metadata

Use metadata to confirm the modeled runtime shape before running anything.

This step answers:

- which runtime name is generated
- which operations are exposed
- which command/query split the sample has

```bash
cncf dev command --project-dev . event-driven.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `event-driven.meta.describe`: describe the generated runtime metadata
- `--format yaml`: request structured YAML output

You should see operation definitions including:

- `emitEvent`
- `recordEffect`
- `loadEffect`

### 4. Start the server

Now start CNCF in server mode so that state and job information remain available across requests.

This is necessary because this sample is about tracing a routed reaction over multiple shell calls.

```bash
cncf dev server --project-dev .
```

Parameters:
- `server`
  - starts CNCF in persistent server mode so job state and routed effects stay available across requests


### 5. Emit the event

In another shell, submit the event-producing command.

This step does not return the effect payload directly.

It returns the job id that represents the routed event-processing work.

```bash
cncf dev client --project-dev . event-driven.event.emit-event --name alpha --title Alpha
```

Parameters:
- `client`
  - sends the request to the running local CNCF server

- `event-driven.event.emit-event`: emitting command selector
- `--name alpha`: logical item name carried by the emitted event
- `--title Alpha`: extra payload carried by the emitted event

The response is a job id such as:

```text
cncf-job-job-1775496790693-6ohgPcxKOscZLoGHlK3cgO
```

The meaning of this step is:

- the event was accepted
- CNCF created job-managed work for the routed reaction
- the rest of the sample follows that job

### 6. Wait for the routed reaction

Wait for the submitted job to complete.

This step confirms that the event was not only submitted, but actually routed and processed.

```bash
cncf dev client --project-dev . job-control.job.await-job-result --id cncf-job-job-1775496790693-6ohgPcxKOscZLoGHlK3cgO
```

Parameters:
- `client`
  - sends the request to the running local CNCF server

- `job-control.job.await-job-result`: wait until the submitted job reaches a final result
- `--id ...`: the job id returned by `emit-event`

Expected response example:

```json
{"outcome":"Routed","dispatched_count":1,"persisted":false}
```

This response is the first proof that the event-triggered path actually ran.

`outcome = Routed` means the event was received and dispatched to the modeled reaction.

### 7. Inspect the resulting job status

After waiting, inspect the summarized job status.

This step is useful when you want one compact view that shows:

- whether the job succeeded
- whether the routed task succeeded
- which request summary the job belongs to

```bash
cncf dev client --project-dev . job-control.job.get-job-status --id cncf-job-job-1775496790693-6ohgPcxKOscZLoGHlK3cgO
```

Parameters:
- `client`
  - sends the request to the running local CNCF server

- `job-control.job.get-job-status`: fetch the summarized job state
- `--id ...`: target job id

Important fields in the response:

- `status`
- `result_success`
- `timeline`
- `debug_request_summary`

Example result:

```json
{"job_id":"cncf-job-job-1775497003360-7e4FqKaiOnoqLcgEB50m5f","status":"Succeeded","result_success":true,"debug_request_summary":"EventDriven.Event.emitEvent"}
```

In practice, this is the best first diagnostic view for an event-triggered command.

### 8. Inspect the job history

Then inspect the full timeline.

This step makes the execution progression explicit instead of leaving it implicit inside the runtime.

```bash
cncf dev client --project-dev . job-control.job.load-job-history --id cncf-job-job-1775496790693-6ohgPcxKOscZLoGHlK3cgO
```

Parameters:
- `client`
  - sends the request to the running local CNCF server

- `job-control.job.load-job-history`: fetch the detailed job timeline
- `--id ...`: target job id

Expected history kinds include:

- `job.submitted`
- `job.running`
- `task.running`
- `task.succeeded`
- `job.succeeded`

Example result:

```json
{"job_id":"cncf-job-job-1775497003360-7e4FqKaiOnoqLcgEB50m5f","total_count":5,"fetched_count":5,"events":[{"kind":"job.submitted"},{"kind":"job.running"},{"kind":"task.running"},{"kind":"task.succeeded"},{"kind":"job.succeeded"}]}
```

This is the trace bridge that the sample is about:

- command submission
- routed reaction
- task completion
- job completion

### 9. Inspect the visible post-event effect

Finally, read the visible post-event effect from the event-facing query.

This closes the loop:

- command submitted
- event routed
- job finished
- visible effect confirmed

```bash
cncf dev client --project-dev . event-driven.event.load-effect
```

Parameters:
- `client`
  - sends the request to the running local CNCF server

- `event-driven.event.load-effect`: read the visible post-event effect
- no explicit arguments: this sample keeps only the latest visible effect payload

Expected response example:

```json
{"cncf":{"event":{"kind":"changed","name":"item.changed","occurred_at":"2026-04-06T17:33:10.705654Z","persistent":"false"}},"name":"alpha","source":"event-driven","event_name":"item.changed","title":"Alpha","event_kind":"changed"}
```

At this point you have seen the whole shell-visible path:

- the event command
- the job id
- the routed result
- the job status
- the job history
- the final visible effect

## Parameters And Returns

`event-driven.event.emit-event`

- parameters:
  - `--name`: logical item name carried by the event
  - `--title`: additional payload carried by the event
- returns:
  - a job id

`job-control.job.await-job-result`

- parameters:
  - `--id`: job id returned by the emitting command
- returns:
  - the final routed result for that job

`job-control.job.get-job-status`

- parameters:
  - `--id`: job id
- returns:
  - current/final job status with summary timeline

`job-control.job.load-job-history`

- parameters:
  - `--id`: job id
- returns:
  - the full job history sequence

`event-driven.event.load-effect`

- parameters:
  - none
- returns:
  - the visible post-event effect payload

## How It Differs From `07-event-driven`

- `07-event-driven` stops at event surface and visible effect
- `07.a-event-job-trace-lab` adds `job-control` observation of the routed reaction
