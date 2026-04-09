# 11.c-subsystem-mixed-component-lab

## Overview

This lab is the intended mixed explicit subsystem example.
It will demonstrate a subsystem that wires:

- generally distributed components
- bundled components shipped inside the SAR

in the same subsystem.

## Intended Use Case

Use this lab when you want to learn the mixed deployment form:

- some components remain generic reusable artifacts
- some components are bundled for subsystem-local behavior
- the subsystem distributes a precise composition rather than a single isolated component

## Current Status

This lab is not runnable yet.

The current explicit subsystem descriptor line supports only a single component entry:

- one `component`
- one `coordinate`

That is enough for:

- `11-subsystem`
  - formal subsystem entry point
- `11.b-subsystem-bundled-component-lab`
  - bundled component subsystem
- `11.f-subsystem-parameter-lab`
  - descriptor-direct startup

But it is not enough for a true mixed explicit subsystem.

## What This Lab Needs

To make this lab runnable, CNCF needs:

- a subsystem descriptor that can describe multiple component bindings
- factory/bootstrap support that resolves both bundled and generic components into one subsystem
- a sample SAR layout that shows which components are bundled and which remain external

## Files

- `run.sh`
  - currently reports the framework gap explicitly

## Setup

### Prepare the cozy command

```bash
../../bin/setup cozy
```

### Build the lab skeleton

```bash
sbt --batch clean compile
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
