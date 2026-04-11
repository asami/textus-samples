# 11.a-multi-component-subsystem-lab

## Overview

This lab is the baseline extension after `11-subsystem`.
It will demonstrate a formal subsystem that hosts multiple components in one subsystem without introducing inter-component wiring.

This is the intended bridge between:

- `11-subsystem`
  - one subsystem
  - one component
- `12-subsystem-wiring`
  - multiple components
  - explicit inter-component wiring

## Intended Use Case

Use this lab when you want to learn the next formal subsystem step after the single-component baseline:

- one subsystem can host multiple components
- those components can remain independently hosted
- subsystem composition can grow before any wiring semantics are introduced

## Current Status

This lab demonstrates the independent multi-component subsystem shape.

The current runtime line is:

- `component.d/testsubsystemmulti.sar`
- `component.d/alphacomp.car`
- `component.d/betacomp.car`
- `--textus.subsystem=testsubsystemmulti`

## What This Lab Needs

This sample provides:

- one explicit subsystem descriptor
- multiple independently hosted component artifacts
- no component-to-component wiring
- help and execution commands that show the hosted components as one subsystem surface

## Files

- `run.sh`
  - generates the component CARs and subsystem SAR, then runs the walkthrough
- `src/main/scala/alpha/`
  - the `alphacomp` implementation
- `src/main/scala/beta/`
  - the `betacomp` implementation

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the lab

```bash
sbt --batch compile
```

## Run The Whole Scenario

This lab runs the full independent multi-component subsystem walkthrough in one shot.

```bash
bash run.sh
```

Expected result:

```text
Hello from alphacomp in testsubsystemmulti
Hello from betacomp in testsubsystemmulti
```

## What This Sample Generates

`run.sh` builds the sample classes and then generates:

- `alphacomp.car`
- `betacomp.car`
- `testsubsystemmulti.sar`

These are execution artifacts only.
They are created in a temporary working directory and are not committed inputs.

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--component-dir <temporary-component-dir>`
  - activates the generated component and subsystem artifacts for this walkthrough
- `--textus.subsystem=testsubsystemmulti`
  - selects the multi-component subsystem

### 1. Inspect subsystem help

This confirms that one subsystem now exposes both component surfaces.

### 2. Inspect the components

This sample inspects:

- `alphacomp`
- `betacomp`

independently, while both remain hosted under the same subsystem.

### 3. Inspect operation help

This sample inspects:

- `alphacomp.main.hello`
- `betacomp.main.hello`

to show that no extra wiring is required for the components to coexist.

### 4. Execute the operations

This sample executes both operations separately.
The result should show that both components are reachable inside one explicit subsystem while remaining independent.
