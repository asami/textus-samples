# 04.b-event-job-server-client-lab

## Overview

This is the practical server/client follow-up to `04-event-driven` and `04.a-event-job-trace-lab`.

It keeps the same event story:

- `emitEvent` emits `item.changed`
- CNCF event reception runs `recordEffect`
- `loadEffect` proves the visible reaction happened

This lab is intentionally local and small.
It is not a distributed broker or queue lab.

## What It Shows

- one server startup path
- one client path that emits the event
- one client path that proves the triggered reaction

## Model

- component: `event-driven`
- service: `Event`
- emitting command: `emitEvent`
- reaction action: `recordEffect`
- observation query: `loadEffect`
- emitted event: `item.changed`

## How It Works

- `run-server.sh` starts the CNCF server shape
- `run-client-emit.sh` sends `name=alpha title=Alpha` to `client http post /event-driven/event/emit-event`
- `run-client-load.sh` reads the effect back through `client http get /event-driven/event/load-effect`

## How This Differs From Earlier Labs

- `04-event-driven`
  - proves event emission and visible post-event effect
- `04.a-event-job-trace-lab`
  - proves the same reaction can be traced through job/history/event observation
- `04.b-event-job-server-client-lab`
  - shows the same reaction from a more practical server/client image

## How To Run

Build:

```bash
sbt cozyGenerate
sbt clean compile
```

Run the server:

```bash
bash run-server.sh
```

Emit the event from the client:

```bash
bash run-client-emit.sh
```

Confirm the triggered reaction from the client:

```bash
bash run-client-load.sh
```

End-to-end demo:

```bash
bash run-demo.sh
```

## Status

This lab is the server/client follow-up companion sample.
