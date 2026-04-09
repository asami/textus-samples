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
- `../11-subsystem/component.d`

The descriptor comes from `11-subsystem`.
The component artifact is resolved from the neighboring baseline subsystem sample.

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
- `--component-repository=component-dir:../11-subsystem/component.d`
  - points the runtime at the component implementation repository

### 1. Inspect subsystem help

```bash
bash ../../bin/cncf command meta.help --format yaml --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml --component-repository=component-dir:../11-subsystem/component.d
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=component-dir:../11-subsystem/component.d`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML

### 2. Inspect the component

```bash
bash ../../bin/cncf command meta.help testcomp --format yaml --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml --component-repository=component-dir:../11-subsystem/component.d
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=component-dir:../11-subsystem/component.d`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `testcomp`
  - identifies the component surface loaded through the parameter-driven subsystem selection
- `--format yaml`
  - renders the result in YAML

### 3. Inspect operation help

```bash
bash ../../bin/cncf command help testcomp.main.hello --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml --component-repository=component-dir:../11-subsystem/component.d
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=component-dir:../11-subsystem/component.d`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the selected subsystem

### 4. Execute the operation

```bash
bash ../../bin/cncf command testcomp.main.hello --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml --component-repository=component-dir:../11-subsystem/component.d
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=component-dir:../11-subsystem/component.d`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the operation path exposed by the selected subsystem

Expected result:

```text
Hello from testcomp in testsubsystem
```
