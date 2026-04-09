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

This lab is not implemented yet.

The role of this lab has been fixed, but the sample implementation has not yet been created.

## What This Lab Needs

To make this lab runnable, the sample should provide:

- one explicit subsystem descriptor
- multiple independently hosted component artifacts
- no component-to-component wiring
- help and execution commands that show the hosted components as one subsystem surface

## Files

- `run.sh`
  - currently reports the implementation gap explicitly

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

This lab is a placeholder for the future independent multi-component subsystem line.

```bash
bash run.sh
```

Expected result:

```text
11.a-multi-component-subsystem-lab is not runnable yet.
This lab is reserved for the independent multi-component subsystem baseline extension.
Inter-component wiring belongs to 12-subsystem-wiring.
```
