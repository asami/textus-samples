# 11.e-sar-dir-lab

## Overview

This lab demonstrates loader/debug startup from expanded `sar.d`.
Its role is not distribution packaging.
Its role is working directly against the unpacked SAR layout for loader/debug
inspection. For the normal application edit/run loop, prefer
`--subsystem-dev-dir <project>`.

## Intended Use Case

Use this lab when you want to compare:

- the packaged SAR used in `11-subsystem`
- the expanded `sar.d` form used for SAR loader/debug inspection
- the selector-less `--subsystem-dev-dir <project>` form used for normal
  subsystem development roots
- how the same subsystem can be started without rebuilding the `.sar` file each time

## What This Lab Will Show

This lab shows:

- direct startup from expanded `sar.d`
- the same subsystem behavior as `11-subsystem`
- the difference between a packaged subsystem artifact and its unpacked working form

## Files

- `sar.d/`
  - the expanded SAR directory used for archive inspection
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
  subsystem-descriptor.yaml
```

This is the debug/inspection interpretation of an unpacked subsystem artifact.
In this repository, `run.sh` generates `base.car` from the neighboring `11-subsystem` sample before running the walkthrough.

For convenience, the repository code can also scan nested expanded forms such as:

```text
sar.d/explicit-subsystem
  component/base.car
  subsystem-descriptor.yaml
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

- `--subsystem-sar-dir sar.d`
  - explicitly selects the expanded SAR directory
- `command`
  - uses ordinary one-shot CNCF command execution for this lab

With the current activation policy:

- `sar.d`
  - is the expanded SAR debug/inspection shape
  - and is activated only through the explicit `--subsystem-sar-dir` route
- this walkthrough passes only the SAR directory
  - the subsystem name is inferred from `subsystem-descriptor.yaml`
  - no implicit `sar.d` auto-discovery is used

### 1. Inspect subsystem help

```bash
cncf dev command --project . meta.help --format yaml --subsystem-sar-dir sar.d
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML
- `--subsystem-sar-dir sar.d`
  - selects the expanded SAR directory

### 2. Inspect the component

```bash
cncf dev command --project . meta.help testcomp --format yaml --subsystem-sar-dir sar.d
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
- `--subsystem-sar-dir sar.d`
  - selects the expanded SAR directory

### 3. Inspect operation help

```bash
cncf dev command --project . help testcomp.main.hello --subsystem-sar-dir sar.d
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the expanded `sar.d`
- `--subsystem-sar-dir sar.d`
  - selects the expanded SAR directory

### 4. Execute the operation

```bash
cncf dev command --project . testcomp.main.hello --subsystem-sar-dir sar.d
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the operation path exposed by the expanded `sar.d`
- `--subsystem-sar-dir sar.d`
  - selects the expanded SAR directory

Expected result:

```text
Hello from testcomp in testsubsystem
```
