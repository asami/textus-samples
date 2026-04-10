# 11.f-subsystem-parameter-lab

## Overview

This lab demonstrates startup through a subsystem descriptor parameter.
Its role is development-time and test-time control, not the primary production packaging story.

## Intended Use Case

Use this lab when you want to compare:

- descriptor-direct startup
- SAR-first startup
- implicit subsystem startup

## Current Status

This lab demonstrates parameter-driven explicit subsystem startup without a SAR artifact.
The distributed unit is the subsystem descriptor itself, and the descriptor is provided directly on the CLI.

## Files

- `run.sh`
  - convenience batch runner for the descriptor-direct line

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the lab

```bash
sbt --batch compile
```

### Prepare the referenced subsystem descriptor

This lab reuses:

- `../11-subsystem/subsystem.cml`
- the component implementation built in `../11-subsystem`

The descriptor comes from `11-subsystem`.
`run.sh` creates a temporary `component.d/testcomp.car` from the neighboring baseline component jar before starting the walkthrough.

## Run The Whole Scenario

This command runs the full descriptor-direct subsystem walkthrough in one shot.
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

- `command`
  - uses ordinary one-shot CNCF command execution for this lab
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - points the runtime at the subsystem descriptor file directly
- `--component-dir <temporary-component-dir>`
  - activates the temporary packaged component directory generated from the baseline component jar

The supported path in this lab is `bash run.sh`, because the batch runner prepares the temporary active `component.d` automatically.

### 1. Inspect subsystem help

`run.sh` executes the equivalent command after preparing the temporary active packaged directory.

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-dir <temporary-component-dir>`
  - activates the packaged component artifact directory
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML

### 2. Inspect the component

`run.sh` executes the equivalent command after preparing the temporary active packaged directory.

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-dir <temporary-component-dir>`
  - activates the packaged component artifact directory
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `testcomp`
  - identifies the component surface loaded through the parameter-driven subsystem selection
- `--format yaml`
  - renders the result in YAML

### 3. Inspect operation help

`run.sh` executes the equivalent command after preparing the temporary active packaged directory.

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-dir <temporary-component-dir>`
  - activates the packaged component artifact directory
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the selected subsystem

### 4. Execute the operation

`run.sh` executes the equivalent command after preparing the temporary active packaged directory.

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-dir <temporary-component-dir>`
  - activates the packaged component artifact directory
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the operation path exposed by the selected subsystem

Expected result:

```text
Hello from testcomp in testsubsystem
```
