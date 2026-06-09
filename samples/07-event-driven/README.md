# 05-event-driven

## Overview

`07-event-driven` is the first sample that makes event-oriented modeling visible in CNCF.

It shows three things:

- one command is modeled as an event emitter
- one command is modeled as the event reception target
- one query is modeled as the observable post-event effect

This sample is intentionally the base event sample.
It focuses on the visible shell surface rather than on internal same-JVM proof code.

## Event-Driven Context In CNCF

After `06-cqrs`, the next step is to move from:

- command/query split

to:

- command
- event emission
- event reception
- observable effect

CNCF approaches this by making event-related structure part of the model:

- event-producing operations
- event definitions
- reception targets
- post-event query surfaces

So the framework direction is not "call another method after a command".
It is:

- emit an event as part of runtime behavior
- let CNCF route and receive it
- expose the effect through a user-facing read path

## Position

- `06-cqrs`
  - makes the command/query split visible
- `07-event-driven`
  - makes event emission and reception visible
- later `05.*`
  - will show richer event/job/server-client behavior

## Intended Use Case

Use this sample when you want to confirm:

- how event-related operations appear in the generated shell surface
- that event emission, reception, and observation are modeled separately
- how CNCF names the event-facing command and query selectors

Typical use cases are:

- introducing the event-driven part of the model
- checking generated selectors before adding larger runtime topology
- understanding how event emission and observation are separated in CNCF

## Files

- `src/main/cozy/event.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/07-event-driven
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf dev` will use later.

```bash
$ cd samples/07-event-driven
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/07-event-driven
$ bash run.sh
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
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Component Help

```bash
$ cncf dev command --project-dev . help event-driven
```

Output example:

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

This confirms that the generated component exposes an event-facing service.

### Emit Event Help

```bash
$ cncf dev command --project-dev . help event-driven.event.emit-event
```

Output example:

```yaml
type: operation
name: emitEvent
service: Event
selector:
  cli: event-driven.event.emit-event
returns:
  - EmitEventResult
```

### Load Effect Help

```bash
$ cncf dev command --project-dev . help event-driven.event.load-effect
```

Output example:

```yaml
type: operation
name: loadEffect
service: Event
selector:
  cli: event-driven.event.load-effect
returns:
  - LoadEffectResult
```

### Metadata Describe

```bash
$ cncf dev command --project-dev . event-driven.meta.describe --format yaml
```

This lets you inspect the modeled event-facing runtime surface in one place.

## What This Sample Shows

`07-event-driven` is the first event sample, so it deliberately stays small.

It shows:

- there is an explicit event-emission command
- there is an explicit event-reception action
- there is an explicit effect-loading query

It does not try to show the full visible effect from shell-only commands in this sample.
That runtime proof is kept in `cozy` scripted, where internal same-JVM verification belongs.

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- handwritten `Subsystem` bootstrapping in the user-facing path
- same-JVM internal demo code
- distributed broker integration
- server/client event topology
- event-job tracing details

Those concerns belong to the later `05.*` samples and to `cozy` scripted verification.
