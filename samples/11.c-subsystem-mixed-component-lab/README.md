# 11.c-subsystem-mixed-component-lab

## Overview

This lab is the intended mixed explicit subsystem example.
It will demonstrate a subsystem that hosts multiple components whose sources are mixed:

- generally distributed components
- bundled components shipped inside the SAR

in the same subsystem.
Inter-component wiring belongs to phase 12 and is intentionally out of scope here.

## Intended Use Case

Use this lab when you want to learn the mixed deployment form:

- some components remain generic reusable artifacts
- some components are bundled for subsystem-local behavior
- the subsystem distributes a precise composition rather than a single isolated component
- the components can coexist without introducing phase 12 wiring yet

## Current Status

This lab is not runnable yet.

The intended phase 11 spec is:

- one subsystem can host multiple components
- component sources may be mixed
  - generic reusable component artifacts
  - subsystem-bundled component artifacts
- the phase 11 mixed sample stops at coexistence
  - it does not yet introduce inter-component wiring

The remaining gap is implementation support for this multi-component mixed subsystem shape.

## What This Lab Needs

To make this lab runnable, CNCF still needs:

- a subsystem descriptor that can describe multiple component bindings
- factory/bootstrap support that resolves both bundled and generic components into one subsystem
- a sample SAR layout that shows which components are bundled and which remain external
- a runnable sample that keeps the components independent, leaving wiring to phase 12

## Files

- `run.sh`
  - currently reports the framework gap explicitly

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the lab skeleton

```bash
sbt --batch compile
```

## Run The Whole Scenario

This lab is a placeholder for the future mixed explicit subsystem line.

```bash
bash run.sh
```

Expected result:

```text
11.c-subsystem-mixed-component-lab is not runnable yet.
Current GenericSubsystemDescriptor supports only one component entry.
Mixed explicit subsystems require descriptor and factory support for multiple component bindings.
```
