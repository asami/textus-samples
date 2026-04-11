# 11.e-sar-dir-lab

## Overview

This lab demonstrates development-time and test-time startup from expanded `sar.d`.
Its role is not distribution packaging.
Its role is working directly against the unpacked SAR layout.

## Intended Use Case

Use this lab when you want to compare:

- the packaged SAR used in `11-subsystem`
- the expanded `sar.d` form used during development or testing
- how the same subsystem can be started without rebuilding the `.sar` file each time

## What This Lab Will Show

This lab shows:

- direct startup from expanded `sar.d`
- the same subsystem behavior as `11-subsystem`
- the difference between a packaged subsystem artifact and its unpacked working form

## Files

- `sar.d/`
  - the expanded SAR directory used for development and testing
- `run.sh`
  - convenience batch runner

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the lab

```bash
sbt --batch compile
```

### Prepare the expanded SAR directory

The main form in this lab is a directly expanded SAR:

```text
sar.d
  component/base.car
  meta/manifest.json
```

This is the development/test interpretation of an unpacked subsystem artifact.
In this repository, `run.sh` generates `base.car` from the neighboring `11-subsystem` sample before running the walkthrough.

For convenience, the repository code can also scan nested expanded forms such as:

```text
sar.d/explicit-subsystem
  component/base.car
  meta/manifest.json
```

But the main line in this lab is the direct `sar.d/` layout.

## Run The Whole Scenario

This command runs the full expanded `sar.d` walkthrough in one shot.
It is the batch form of the step-by-step `Command Walkthrough` below.

It will:

- inspect subsystem help
- inspect component help
- inspect operation help
- execute `testcomp.main.hello`

```bash
bash run.sh
```

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
The common parameters are:

- `--textus.subsystem=testsubsystem`
  - selects the subsystem name exposed by the expanded `sar.d`
- `command`
  - uses ordinary one-shot CNCF command execution for this lab

With the current activation policy:

- `sar.d`
  - is the expanded debug/development subsystem shape
  - and is auto-activated by the runtime
- this walkthrough only needs the subsystem name
  - the expanded `sar.d` work area is discovered automatically

### 1. Inspect subsystem help

```bash
bash ../../bin/cncf command meta.help --format yaml --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML
- `--textus.subsystem=testsubsystem`
  - selects the subsystem defined by the expanded `sar.d`

### 2. Inspect the component

```bash
bash ../../bin/cncf command meta.help testcomp --format yaml --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `testcomp`
  - identifies the component surface loaded from the expanded `sar.d`
- `--format yaml`
  - renders the result in YAML
- `--textus.subsystem=testsubsystem`
  - selects the subsystem defined by the expanded `sar.d`

### 3. Inspect operation help

```bash
bash ../../bin/cncf command help testcomp.main.hello --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the expanded `sar.d`
- `--textus.subsystem=testsubsystem`
  - selects the subsystem defined by the expanded `sar.d`

### 4. Execute the operation

```bash
bash ../../bin/cncf command testcomp.main.hello --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the operation path exposed by the expanded `sar.d`
- `--textus.subsystem=testsubsystem`
  - selects the subsystem defined by the expanded `sar.d`

Expected result:

```text
Hello from testcomp in testsubsystem
```
