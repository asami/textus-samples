# 09.d-sar-dir-lab

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
- `subsystem-sar-dir.conf`
  - the runtime configuration for expanded `sar.d` startup
- `run.sh`
  - convenience batch runner

## Setup

### Prepare the cozy command

```bash
../../bin/setup cozy
```

### Build the lab

```bash
sbt --batch clean compile
```

### Prepare the expanded SAR directory

The main form in this lab is a directly expanded SAR:

```text
sar.d
  component/base.car
  meta/manifest.json
```

This is the development/test interpretation of an unpacked subsystem artifact.

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
- execute `subsystem.main.hello`

```bash
bash run.sh
```

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this lab
- `--cncf.config.file=subsystem-sar-dir.conf`
  - loads the explicit subsystem name and the repository path for the expanded `sar.d`
- `--no-default-components`
  - prevents the main runtime path from adding the same component a second time

### 1. Inspect subsystem help

```bash
bash ../../bin/cncf --no-default-components command meta.help --format yaml --cncf.config.file=subsystem-sar-dir.conf
```

Parameters:
- `--no-default-components`
  - prevents duplicate loading from the default runtime path
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML
- `--cncf.config.file=subsystem-sar-dir.conf`
  - loads the subsystem name and expanded `sar.d` repository path for this lab

### 2. Inspect the component

```bash
bash ../../bin/cncf --no-default-components command meta.help subsystem --format yaml --cncf.config.file=subsystem-sar-dir.conf
```

Parameters:
- `--no-default-components`
  - prevents duplicate loading from the default runtime path
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `subsystem`
  - identifies the component surface loaded from the expanded `sar.d`
- `--format yaml`
  - renders the result in YAML
- `--cncf.config.file=subsystem-sar-dir.conf`
  - loads the subsystem name and expanded `sar.d` repository path for this lab

### 3. Inspect operation help

```bash
bash ../../bin/cncf --no-default-components command help subsystem.main.hello --cncf.config.file=subsystem-sar-dir.conf
```

Parameters:
- `--no-default-components`
  - prevents duplicate loading from the default runtime path
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `subsystem.main.hello`
  - identifies the operation path exposed by the expanded `sar.d`
- `--cncf.config.file=subsystem-sar-dir.conf`
  - loads the subsystem name and expanded `sar.d` repository path for this lab

### 4. Execute the operation

```bash
bash ../../bin/cncf --no-default-components command subsystem.main.hello --cncf.config.file=subsystem-sar-dir.conf
```

Parameters:
- `--no-default-components`
  - prevents duplicate loading from the default runtime path
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `subsystem.main.hello`
  - selects the operation path exposed by the expanded `sar.d`
- `--cncf.config.file=subsystem-sar-dir.conf`
  - loads the subsystem name and expanded `sar.d` repository path for this lab

Expected result:

```text
Hello from the minimum subsystem
```
