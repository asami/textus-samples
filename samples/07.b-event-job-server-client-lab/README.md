# 05.b-event-job-server-client-lab

## Purpose

This sample is the practical server/client follow-up to `07-event-driven` and `07.a-event-job-trace-lab`.

It keeps the same event story:

- `emitEvent` emits `item.changed`
- CNCF event reception runs `recordEffect`
- `loadEffect` proves the visible reaction happened

This lab is intentionally local and small.
It is not a distributed broker or queue lab.

## Why It Matters

`07-event-driven` showed the base event surface.

`07.a-event-job-trace-lab` showed how the same reaction appears in `job-control`.

`07.b-event-job-server-client-lab` turns that into the practical local shape that a user actually runs:

- start one server
- send one client command
- wait for completion
- read the visible result back from another client call

This is the smallest realistic event/job flow for a local CNCF service.

## CNCF Approach

CNCF uses the same pattern here as in the earlier command and event samples:

- the write-side action is job-backed
- the visible state is observed through a separate selector
- server/client mode is used because the reaction must survive across calls

This sample focuses on that operational shape, not on detailed trace inspection.

## Intended Use Case

Use this sample when you want to explain:

- the smallest practical server/client event flow
- why `await-job-result` belongs between emit and load
- how CNCF uses job-backed writes together with query-side observation in a local service image

## Files

- `src/main/cozy/event.cml`
- `build.sbt`
- `run-demo.sh`

Convenience wrappers also exist:

- `run-server.sh`
- `run-client-emit.sh`
- `run-client-await.sh`
- `run-client-load.sh`

## Model

- component: `event-driven`
- service: `Event`
- emitting command: `emitEvent`
- reaction action: `recordEffect`
- observation query: `loadEffect`
- emitted event: `item.changed`

## How This Differs From Earlier Labs

- `07-event-driven`
  - proves event emission and visible post-event effect
- `07.a-event-job-trace-lab`
  - proves the same reaction can be traced through job/history/event observation
- `07.b-event-job-server-client-lab`
  - shows the same reaction from a practical server/client flow

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

This step ensures that the sample uses the expected local Cozy version instead of an unrelated environment default.

```bash
../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf --discover=classes` will use later.

This step is required because the walkthrough runs the generated component directly from local compiled classes.

```bash
sbt clean compile
```

## Run The Whole Scenario

```bash
bash run-demo.sh
```

`run-demo.sh` is only a convenience batch runner.

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
- `server`:
  - start CNCF in persistent server mode
- `client`:
  - send a request to a running CNCF server

### 1. Start the server

Start CNCF in server mode first.

This keeps the in-memory event effect and job state available across client requests.

```bash
bash ../../bin/cncf --discover=classes server
```

Parameters:

- `server`:
  - starts CNCF in persistent server mode so in-memory event effects and job state remain available across client requests

Expected signal:

```text
Ember-Server service bound to address: [::]:8080
```

### 2. Emit the event from the client

Send the event-producing command from another shell.

This step submits the event-backed work and returns a job id.

```bash
bash ../../bin/cncf --discover=classes client event-driven.event.emit-event --name alpha --title Alpha
```

Parameters:

- `client`:
  - sends the request to the running local CNCF server
- `event-driven.event.emit-event`: the command-side selector that emits `item.changed`
- `--name alpha`:
  - field name: `name`
  - meaning: logical item name carried by the emitted event payload
  - sample value: `alpha`
- `--title Alpha`:
  - field name: `title`
  - meaning: additional descriptive payload carried by the same event
  - sample value: `Alpha`

Expected result example:

```text
cncf-job-job-1775498741256-3pl4ERPQi6PkTtsPrJ51fD
```

### 3. Wait for the routed reaction

Use the returned job id to wait until the routed reaction completes.

This is the practical step that prevents a race between event submission and effect loading.

```bash
bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id cncf-job-job-1775498741256-3pl4ERPQi6PkTtsPrJ51fD
```

Parameters:

- `client`:
  - sends the request to the running local CNCF server
- `job-control.job.await-job-result`:
  - component: `job-control`
  - service: `job`
  - meaning: wait until the emitted event's routed work reaches a final result
- `--id ...`:
  - field name: `id`
  - meaning: the job id returned by `event-driven.event.emit-event`
  - expected shape: `cncf-job-...`

Expected result example:

```json
{"outcome":"Routed","dispatched_count":1,"persisted":false}
```

### 4. Confirm the triggered reaction from the client

Now read the visible effect back through the query-side selector.

This confirms that the event-driven reaction already changed the visible state.

```bash
bash ../../bin/cncf --discover=classes client event-driven.event.load-effect
```

Parameters:

- `client`: query the running local CNCF server
- `event-driven.event.load-effect`:
  - component: `event-driven`
  - service: `event`
  - meaning: query-side selector for the visible post-event effect
- no explicit arguments:
  - this sample keeps one current visible effect snapshot
  - the selector simply reads that snapshot after the routed reaction finishes

Expected result example:

```json
{"cncf":{"event":{"kind":"changed","name":"item.changed","occurred_at":"2026-04-06T18:05:41.260881Z","persistent":"false"}},"name":"alpha","source":"event-driven","event_name":"item.changed","title":"Alpha","event_kind":"changed"}
```

## Convenience Wrappers

If you prefer the helper scripts, they map to the same commands:

- `bash run-server.sh`
  - `bash ../../bin/cncf --discover=classes server`
- `bash run-client-emit.sh`
  - `bash ../../bin/cncf --discover=classes client event-driven.event.emit-event --name alpha --title Alpha`
- `bash run-client-await.sh <job-id>`
  - `bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id <job-id>`
- `bash run-client-load.sh`
  - `bash ../../bin/cncf --discover=classes client event-driven.event.load-effect`

## Parameters And Returns

`event-driven.event.emit-event`

- parameters:
  - `--name`
    - required
    - logical item name carried by the emitted event
    - example: `alpha`
  - `--title`
    - required
    - additional event payload used to make the visible effect easy to inspect
    - example: `Alpha`
- returns:
  - a job id

`job-control.job.await-job-result`

- parameters:
  - `--id`
    - required
    - the job id returned by `event-driven.event.emit-event`
    - example: `cncf-job-job-1775498741256-3pl4ERPQi6PkTtsPrJ51fD`
- returns:
  - the routed job result

`event-driven.event.load-effect`

- parameters:
  - none in this sample
    - the query reads the current visible effect snapshot
- returns:
  - the visible post-event effect payload
