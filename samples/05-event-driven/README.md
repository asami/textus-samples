# 05-event-driven

## Overview

This is the first minimal event-oriented sample after `04-cqrs`.

It shows three things:

- one action emits an event
- one CNCF event-reception path receives it
- one visible post-event effect can be observed afterwards

This sample is not a distributed messaging lab.
It stays inside a small local CNCF runtime and uses the same model-driven direction as the earlier samples.

## Model

- component: `event-driven`
- service: `event`
- emitting command: `emitEvent`
- receiver action: `recordEffect`
- observation query: `loadEffect`
- emitted event: `item.changed`

The model source is:

- `src/main/cozy/event.cml`

## How It Works

- `event.cml` now generates a discoverable `EventDriven` component and `Event` service.
- event metadata for `item.changed` is present in the generated component metadata.
- the `event-driven.event.*` help surface is now visible through `CncfMain --discover=classes`.
- CNCF now provides the minimal built-in runtime path used by this sample:
  - `emitEvent` emits `item.changed`
  - `recordEffect` records the received payload
  - `loadEffect` returns the recorded effect

The event reception is not a message bus or external broker.
It is the CNCF event-reception path inside the sample runtime.

## How This Differs From `04-cqrs`

- `04-cqrs`
  - makes command and query paths visibly different
  - `createItem` is job-backed
  - `loadItem` / `searchItemRecord` are read-oriented

- `05-event-driven`
  - makes event emission and event reception visible
  - `emitEvent` produces an event
  - `recordEffect` reacts to the event
  - `loadEffect` proves the reaction happened

So the point is not just request/response.
The point is that a runtime reaction happens after an emitted event.

## How To Run

Build/generate:

```bash
sbt cozyGenerate
sbt clean compile
```

Runtime help:

```bash
./run.sh
./run-demo.sh
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event.emit-event"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event.load-effect"
```

Command-path help checks:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event.emit-event"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event.load-effect"
```

End-to-end demo check:

```bash
./run-demo.sh
```

Observed flow:

- `emitEvent` runs as the event-producing command
- CNCF event reception dispatches `recordEffect`
- `loadEffect` returns the recorded effect in the same JVM

Example effect payload:

```json
{
  "cncf.event.kind": "changed",
  "cncf.event.name": "item.changed",
  "name": "alpha",
  "source": "event-driven",
  "title": "Alpha"
}
```

The demo keeps emission and observation in one JVM so the visible effect can be checked without introducing server/client infrastructure in this first event sample.

## Status

This sample is complete.

The active work order is:

- [04-event Development Instruction](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/04-event-development-instruction.md)
