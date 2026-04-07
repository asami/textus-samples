# 09.a-subsystem-bundled-component-lab

## Overview

This lab demonstrates the explicit subsystem form where the subsystem artifact bundles its component artifact.

This is the second step in the `09` line:

1. `11-subsystem`
   - formal subsystem using generally distributed components
2. `11.a-subsystem-bundled-component-lab`
   - formal subsystem bundling its component artifact
3. `11.b-subsystem-mixed-component-lab`
   - formal subsystem mixing general and bundled components
4. `11.c-implicit-subsystem-lab`
   - implicit subsystem
5. `11.d-sar-dir-lab`
   - expanded `sar.d`
6. `11.e-subsystem-parameter-lab`
   - descriptor-direct startup

## Intended Use Case

Use this lab when you want to see the closed distribution form:

- the subsystem artifact carries its own component artifact
- startup does not depend on a separately distributed generic component
- the subsystem is distributed as one self-contained SAR

## Current Status

This lab is the current runnable bundled example.
Its runtime line is:

- `component.d/explicit-subsystem.sar`
- `--textus.runtime.subsystem=subsystem`

The bundled SAR embeds `base.car`.

## Files

- `component.d/explicit-subsystem.sar`
  - the packaged subsystem artifact
- `run.sh`
  - convenience batch runner

## Setup

### Prepare the cozy command

```bash
../../bin/setup cozy
```

### Build the referenced component sample

```bash
(cd ../09.c-implicit-subsystem-lab && sbt --batch clean compile)
```

### Build the bundled subsystem

This lab uses the same packaging flow as the current formal subsystem sample:

- build the component jar from `09.c`
- wrap it as `base.car`
- embed `base.car` into `explicit-subsystem.sar`

## Run The Whole Scenario

This command runs the bundled subsystem walkthrough in one shot.
It is the batch form of the step-by-step `Command Walkthrough` below.

It will:

- inspect subsystem help
- inspect component help
- inspect operation help
- execute `subsystem.main.hello`

```bash
bash run.sh
```

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this lab
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--textus.runtime.subsystem=subsystem`
  - selects the subsystem packaged in the bundled SAR

### 1. Inspect subsystem help

```bash
bash ../../bin/cncf command meta.help --format yaml --no-default-components --textus.runtime.subsystem=subsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--textus.runtime.subsystem=subsystem`
  - selects the bundled subsystem

### 2. Inspect the component

```bash
bash ../../bin/cncf command meta.help subsystem --format yaml --no-default-components --textus.runtime.subsystem=subsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `subsystem`
  - identifies the bundled component surface exposed by the subsystem
- `--format yaml`
  - renders the result in YAML
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--textus.runtime.subsystem=subsystem`
  - selects the bundled subsystem

### 3. Inspect operation help

```bash
bash ../../bin/cncf command help subsystem.main.hello --no-default-components --textus.runtime.subsystem=subsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `subsystem.main.hello`
  - identifies the operation path exposed by the bundled subsystem
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--textus.runtime.subsystem=subsystem`
  - selects the bundled subsystem

### 4. Execute the operation

```bash
bash ../../bin/cncf command subsystem.main.hello --no-default-components --textus.runtime.subsystem=subsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `subsystem.main.hello`
  - selects the operation path exposed by the bundled subsystem
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--textus.runtime.subsystem=subsystem`
  - selects the bundled subsystem

Expected result:

```text
Hello from the minimum subsystem
```
