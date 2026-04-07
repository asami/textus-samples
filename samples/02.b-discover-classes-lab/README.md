# 02.b-discover-classes-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample shows `--discover=classes`.
It is the development-time implicit component construction line.

## Intended Use Case

Use this sample when you are actively developing a component and want CNCF to discover the component directly from compiled classes without packaging a CAR first.

This is convenient, but it is not the baseline distribution form.

## Files

- `src/main/scala/testcomp/TestcompComponent.scala`
  - the component source discovered from compiled classes
- `run.sh`
  - convenience batch runner

## Setup

### Prepare the cozy command

```bash
../../bin/setup cozy
```

### Build the sample

```bash
sbt --batch clean compile
```

## Run The Whole Scenario

This command runs the full class-discovery walkthrough in one shot.

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--discover=classes`
  - tells CNCF to discover the active component from compiled classes

### 1. Inspect the component

```bash
bash ../../bin/cncf --discover=classes command meta.help testcomp --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured component introspection
- `testcomp`
  - identifies the discovered component
- `--format yaml`
  - renders the result in YAML
- `--discover=classes`
  - discovers the component from compiled classes

### 2. Inspect operation help

```bash
bash ../../bin/cncf --discover=classes command help testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the discovered component
- `--discover=classes`
  - discovers the component from compiled classes

### 3. Execute the operation

```bash
bash ../../bin/cncf --discover=classes command testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the sample operation exposed by the discovered component
- `--discover=classes`
  - discovers the component from compiled classes

Expected result:

```text
Hello from testcomp
```

## What To Notice

- this shape avoids CAR packaging during active development
- the selector is still the ordinary component selector
- this is an advanced development-time convenience, not the formal distribution shape

## Key Learnings

- `--discover=classes`
- implicit component construction
- difference between development-time discovery and formal CAR packaging
