# 03-component-cml

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

`03-component-cml` introduces CML-driven component generation in the formal
`CAR` packaging line.

`02` showed a hand-written component in three runtime shapes:

- `repository.d/*.car`
- `car.d`
- current-directory project auto activation

`03` keeps the same three runtime shapes, but replaces the hand-written Scala
component with a `component.cml` source model that Cozy generates into runtime
classes.

In the `03` line, the forms are split like this:

- `03-component-cml`
  - formal generated component artifact
  - `repository.d/*.car`
- `03.a-car-dir-cml-lab`
  - expanded generated `car.d`
  - CAR loader/debug form
- `03.b-discover-classes-cml-lab`
  - current-directory project auto activation
  - development-time implicit loading of generated classes

## Intended Use Case

Use this sample when you want to see the first model-driven component line in
the same formal packaging shape as `02-component`:

- define a component in CML
- package the generated classes as a CAR
- search the packaged CAR explicitly for command execution
- inspect the generated component and operation surface

This sample is intentionally small.
It focuses on generation and metadata, not runtime business behavior.

## Files

- `src/main/cozy/component.cml`
  - the source model
- `repository.d/component-cml-sample.car`
  - the packaged generated component artifact
- `car.d/`
  - expanded generated CAR used for inspection
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### Build the generated sample

```bash
sbt --batch compile packageBin
```

This generates the Scala sources from `src/main/cozy/component.cml` and compiles
them so the sample can package the generated component jar into a CAR.

### Prepare the component CAR

The generated component artifact in this sample is a CAR:

```text
component-cml-sample.car
  descriptor.yaml
  component/main.jar
```

This is the generated counterpart of the hand-written `02-component` line.

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

This command runs the full CML-generated component walkthrough in one shot.
It is the batch form of the step-by-step `Command Walkthrough` below.

It will:

- inspect generated component help
- inspect generated operation help
- inspect generated metadata
- run directly from the current sample and search the packaged CAR explicitly

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--repository-dir /absolute/path/to/repository.d`
  - adds packaged generated component artifacts to the search repository
- `--textus.component=component-cml-sample`
  - explicitly activates the packaged generated component by name

With the current activation policy:

- `repository.d/*.car`
  - is a search target
  - but is not auto-activated by default
- `--repository-dir /absolute/path/to/repository.d`
  - makes the packaged generated component searchable for this command
- `--textus.component=component-cml-sample`
  - selects which packaged component to activate from that search repository

### 1. Inspect the generated component

```bash
cncf command --no-project-classpath --repository-dir /absolute/path/to/repository.d --textus.component=component-cml-sample meta.help component-cml-sample --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured component introspection
- `component-cml-sample`
  - identifies the generated component
- `--format yaml`
  - renders the result in YAML
- `--repository-dir /absolute/path/to/repository.d`
  - adds the packaged generated component artifact directory to the search repository
- `--textus.component=component-cml-sample`
  - activates the packaged generated component by name

### 2. Inspect generated operation help

```bash
cncf command --no-project-classpath --repository-dir /absolute/path/to/repository.d --textus.component=component-cml-sample help component-cml-sample.greeting.greeting
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `component-cml-sample.greeting.greeting`
  - identifies the generated operation
- `--repository-dir /absolute/path/to/repository.d`
  - adds the packaged generated component artifact directory to the search repository
- `--textus.component=component-cml-sample`
  - activates the packaged generated component by name

### 3. Inspect generated metadata

```bash
cncf command --no-project-classpath --repository-dir /absolute/path/to/repository.d --textus.component=component-cml-sample component-cml-sample.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `component-cml-sample.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - renders the result in YAML
- `--repository-dir /absolute/path/to/repository.d`
  - adds the packaged generated component artifact directory to the search repository
- `--textus.component=component-cml-sample`
  - activates the packaged generated component by name

## What To Notice

- the user-facing selector still follows `component.service.operation`
- the source of truth is now `component.cml`, not a hand-written Scala component
- generation changes the authoring method, not the CNCF help surface
- packaged generated CARs are explicit runtime inputs, not always-on defaults
- `03` mirrors the same `CAR / car.d /` comparison shape as `02`
- `--component-dev-dir` is the preferred Cozy/sbt edit/run loop; `car.d` is for expanded archive inspection

## Key Learnings

- first CML-driven component generation
- generated CAR packaging
- explicit search and activation of generated packaged CARs
- generated component and operation metadata
