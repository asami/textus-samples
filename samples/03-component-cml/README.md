# 03-component-cml

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

`03-component-cml` introduces CML-driven component generation in the formal
`CAR` packaging line.

`02` showed a hand-written component in three runtime shapes:

- `component.d/*.car`
- `car.d`
- `--discover=classes`

`03` keeps the same three runtime shapes, but replaces the hand-written Scala
component with a `component.cml` source model that Cozy generates into runtime
classes.

In the `03` line, the forms are split like this:

- `03-component-cml`
  - formal generated component artifact
  - `component.d/*.car`
- `03.a-car-dir-cml-lab`
  - expanded generated `car.d`
  - development and test form
- `03.b-discover-classes-cml-lab`
  - `--discover=classes`
  - development-time implicit loading of generated classes

## Intended Use Case

Use this sample when you want to see the first model-driven component line in
the same formal packaging shape as `02-component`:

- define a component in CML
- package the generated classes as a CAR
- inspect the generated component and operation surface

This sample is intentionally small.
It focuses on generation and metadata, not runtime business behavior.

## Files

- `src/main/cozy/component.cml`
  - the source model
- `component.d/component-cml-sample.car`
  - the packaged generated component artifact
- `car.d/`
  - expanded generated CAR used for inspection
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## Setup

### Prepare the `cozy` command

```bash
../../bin/setup cozy
```

### Build the generated sample

```bash
sbt --batch clean compile packageBin
```

This generates the Scala sources from `src/main/cozy/component.cml` and compiles
them so the sample can package the generated component jar into a CAR.

### Prepare the component CAR

The generated component artifact in this sample is a CAR:

```text
component-cml-sample.car
  descriptor.yaml
  component/main.jar
  meta/manifest.json
```

This is the generated counterpart of the hand-written `02-component` line.

## Run The Whole Scenario

This command runs the full CML-generated component walkthrough in one shot.
It is the batch form of the step-by-step `Command Walkthrough` below.

It will:

- inspect generated component help
- inspect generated operation help
- inspect generated metadata
- run from a neutral launcher sample so only the packaged CAR is visible

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--sample-dir samples/01-minimal`
  - launches CNCF from a neutral sample so the packaged generated CAR is not mixed with the current sample classes
- `--component-repository=component-dir:/absolute/path/to/component.d`
  - loads packaged generated component artifacts from `component.d`

### 1. Inspect the generated component

```bash
bash ../../bin/cncf --sample-dir samples/01-minimal --component-repository=component-dir:/absolute/path/to/component.d command meta.help component-cml-sample --format yaml
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
- `--sample-dir samples/01-minimal`
  - avoids ambiguity between the packaged CAR and the current sample's generated classes
- `--component-repository=component-dir:/absolute/path/to/component.d`
  - loads the packaged generated component artifact

### 2. Inspect generated operation help

```bash
bash ../../bin/cncf --sample-dir samples/01-minimal --component-repository=component-dir:/absolute/path/to/component.d command help component-cml-sample.greeting.greeting
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `component-cml-sample.greeting.greeting`
  - identifies the generated operation
- `--sample-dir samples/01-minimal`
  - avoids ambiguity between the packaged CAR and the current sample's generated classes
- `--component-repository=component-dir:/absolute/path/to/component.d`
  - loads the packaged generated component artifact

### 3. Inspect generated metadata

```bash
bash ../../bin/cncf --sample-dir samples/01-minimal --component-repository=component-dir:/absolute/path/to/component.d command component-cml-sample.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `component-cml-sample.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - renders the result in YAML
- `--sample-dir samples/01-minimal`
  - avoids ambiguity between the packaged CAR and the current sample's generated classes
- `--component-repository=component-dir:/absolute/path/to/component.d`
  - loads the packaged generated component artifact

## What To Notice

- the user-facing selector still follows `component.service.operation`
- the source of truth is now `component.cml`, not a hand-written Scala component
- generation changes the authoring method, not the CNCF help surface
- `03` mirrors the same `CAR / car.d / --discover=classes` comparison shape as `02`

## Key Learnings

- first CML-driven component generation
- generated CAR packaging
- generated component and operation metadata
