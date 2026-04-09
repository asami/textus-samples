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

This lab is runnable.

The phase 11 mixed spec shown here is:

- one subsystem hosts multiple components
- component sources are mixed
  - one standalone generic component artifact
  - one subsystem-bundled component artifact
- the components coexist without inter-component wiring

## Composition

This lab assembles:

- `genericcomp.car`
  - placed as a standalone reusable component artifact
- `testsubsystemmixed.sar`
  - contains `bundledcomp.car`
  - also carries the subsystem descriptor listing both components

The result is one subsystem where:

- `genericcomp` is external
- `bundledcomp` is local to the SAR
- both are resolved into the same subsystem

## Files

- `run.sh`
  - builds the mixed standalone-plus-bundled walkthrough
- `src/main/scala/genericcomp/GenericcompComponent.scala`
  - standalone generic component
- `src/main/scala/bundledcomp/BundledcompComponent.scala`
  - bundled subsystem-local component

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the sample

```bash
sbt --batch compile
```

## Run The Whole Scenario

This command builds both component artifacts and runs the mixed subsystem walkthrough.

```bash
bash run.sh
```

Expected result:

```text
Hello from genericcomp in testsubsystemmixed
Hello from bundledcomp in testsubsystemmixed
```

## What This Sample Generates

`run.sh` builds the sample classes and then generates:

- `genericcomp.car`
- `bundledcomp.car`
- `testsubsystemmixed.sar`

These are execution artifacts only.
They are created in a temporary working directory and are not committed inputs.

The mixed shape is:

- `genericcomp.car`
  - placed directly in the component repository
- `testsubsystemmixed.sar`
  - selected as the subsystem artifact
  - contains `bundledcomp.car`
  - carries the subsystem descriptor listing both components

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--component-repository=component-dir:<temporary-component-dir>`
  - points the runtime at the generated standalone `CAR` and selected `SAR`
- `--textus.runtime.subsystem=testsubsystemmixed`
  - selects the mixed subsystem

### 1. Inspect subsystem help

This confirms that one subsystem now exposes:

- builtin components
- the standalone `genericcomp`
- the bundled `bundledcomp`

at the same time.

### 2. Inspect each component

This sample inspects:

- `genericcomp`
- `bundledcomp`

to show that the runtime keeps their origins distinct:

- `genericcomp`
  - `component-dir:car:genericcomp:0.1.0`
- `bundledcomp`
  - `component-dir:sar:testsubsystemmixed:0.1.0:car:bundledcomp:0.1.0`

### 3. Inspect operation help

This sample inspects:

- `genericcomp.main.hello`
- `bundledcomp.main.hello`

to confirm that both operations are independently available inside the same subsystem.

### 4. Execute the operations

This sample executes both operations separately.
The result should show that one subsystem can host:

- a reusable standalone component
- a subsystem-bundled component

without requiring phase 12 wiring.

## Assembly Observability

This sample also provides a concrete example for the assembly warning model.
If the component repository accidentally contains another `bundledcomp.car` or `genericcomp.car`
with the same component name, the runtime records an assembly warning instead of silently hiding the collision.

That warning can be inspected through:

```bash
bash ../../bin/cncf command admin.assembly.warnings --format yaml --no-default-components --component-repository=component-dir:<temporary-component-dir> --textus.runtime.subsystem=testsubsystemmixed
```

In the normal walkthrough, no warning should be present.
