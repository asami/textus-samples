# 09.e-subsystem-parameter-lab

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

### Prepare the cozy command

```bash
../../bin/setup cozy
```

### Build the lab

```bash
sbt --batch clean compile
```

### Prepare the referenced subsystem descriptor

This lab reuses:

- `../09-subsystem/subsystem.cml`
- `../09.c-implicit-subsystem-lab`

The descriptor comes from `11-subsystem`.
The component implementation is loaded directly from the neighboring implicit subsystem sample.

## Run The Whole Scenario

This command runs the full descriptor-direct subsystem walkthrough in one shot.
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
- `--textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml`
  - points the runtime at the subsystem descriptor file directly
- `--component-repository=scala-cli:../09.c-implicit-subsystem-lab`
  - points the runtime at the component implementation repository

### 1. Inspect subsystem help

```bash
bash ../../bin/cncf command meta.help --format yaml --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml --component-repository=scala-cli:../09.c-implicit-subsystem-lab
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=scala-cli:../09.c-implicit-subsystem-lab`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML

### 2. Inspect the component

```bash
bash ../../bin/cncf command meta.help subsystem --format yaml --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml --component-repository=scala-cli:../09.c-implicit-subsystem-lab
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=scala-cli:../09.c-implicit-subsystem-lab`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `subsystem`
  - identifies the component surface loaded through the parameter-driven subsystem selection
- `--format yaml`
  - renders the result in YAML

### 3. Inspect operation help

```bash
bash ../../bin/cncf command help subsystem.main.hello --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml --component-repository=scala-cli:../09.c-implicit-subsystem-lab
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=scala-cli:../09.c-implicit-subsystem-lab`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `subsystem.main.hello`
  - identifies the operation path exposed by the selected subsystem

### 4. Execute the operation

```bash
bash ../../bin/cncf command subsystem.main.hello --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml --component-repository=scala-cli:../09.c-implicit-subsystem-lab
```

Parameters:
- `--textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml`
  - selects the subsystem descriptor directly
- `--component-repository=scala-cli:../09.c-implicit-subsystem-lab`
  - selects the repository that contains the component implementation
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `subsystem.main.hello`
  - selects the operation path exposed by the selected subsystem

Expected result:

```text
Hello from the minimum subsystem
```
