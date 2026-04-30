# 02-component

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample is the baseline component packaging line.
It shows the formal component artifact shape using `component.d/*.car` as the active packaged source.

In the `02` line, the forms are split like this:

- `02-component`
  - formal component artifact
  - `component.d/*.car`
- `02.a-car-dir-lab`
  - expanded `car.d`
  - CAR loader/debug form
- `02.b-discover-classes-lab`
  - `--discover=classes`
  - development-time implicit component construction

## Intended Use Case

Use this sample when you want to understand the standard component distribution form before moving on to CRUD or other higher-level lines.

- a component is packaged as a CAR
- the runtime activates packaged component artifacts from `component.d`
- packaged CARs are activated explicitly by component name for command execution
- the command path is still `component.service.operation`

## What This Sample Will Show

This sample shows:

- a reusable component artifact in `component.d/testcomp.car`
- the expanded CAR in `car.d/testcomp/` for inspection
- component help
- operation help

## Files

- `component.d/testcomp.car`
  - the packaged component artifact
- `car.d/testcomp/`
  - the expanded CAR directory used for archive inspection
- `src/main/scala/testcomp/TestcompComponent.scala`
  - the sample component source used to build the jar placed into the CAR
- `run.sh`
  - convenience batch runner

## Setup

### Build the sample

```bash
sbt --batch compile packageBin
```

### Prepare the component CAR

The reusable component artifact in this sample is a CAR:

```text
testcomp.car
  component/main.jar
  component-descriptor.yaml
```

The descriptor is the canonical CAR metadata. `meta/manifest.json` is not
accepted by this sample line.

Build the component jar:

```bash
COMPONENT_BINARY=target/scala-3.3.7/cncf-samples-02-component_3-0.1.0-SNAPSHOT.jar
```

Prepare the CAR work directory:

```bash
rm -rf /tmp/testcomp.car.d
mkdir -p /tmp/testcomp.car.d/component
cp "$COMPONENT_BINARY" /tmp/testcomp.car.d/component/main.jar
cat > /tmp/testcomp.car.d/component-descriptor.yaml <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
EOF
```

Create `testcomp.car`:

```bash
cd /tmp/testcomp.car.d
zip -qr /tmp/testcomp.car component-descriptor.yaml component
cp /tmp/testcomp.car component.d/testcomp.car
```

Optional expanded `car.d` for inspection:

```bash
rm -rf car.d/testcomp
mkdir -p car.d/testcomp
cd car.d/testcomp
unzip -q ../../component.d/testcomp.car
```

## Run The Whole Scenario

This command runs the full baseline component walkthrough in one shot.
It is the batch form of the step-by-step `Command Walkthrough` below.

It will:

- inspect component help
- inspect operation help

```bash
bash run.sh
```

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--textus.component=testcomp`
  - activates the packaged component named `testcomp` from `component.d`

With the current activation policy:

- `component.d/*.car`
  - is the active packaged component source in this baseline sample
- `--textus.component=testcomp`
  - selects the packaged component explicitly by component name

### 1. Inspect the component

```bash
bash ../../bin/cncf --textus.component=testcomp command meta.help testcomp --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured component introspection
- `testcomp`
  - identifies the packaged component to inspect
- `--format yaml`
  - renders the result in YAML
- `--textus.component=testcomp`
  - selects the packaged component artifact from `component.d`

### 2. Inspect operation help

```bash
bash ../../bin/cncf --textus.component=testcomp command help testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the component
- `--textus.component=testcomp`
  - selects the component artifact from `component.d`

## What To Notice

- component execution does not require a subsystem sample first
- the user-facing selector is still `component.service.operation`
- `component.d/*.car` is the baseline distribution form
- packaged CARs are explicit runtime inputs, not always-on defaults
- explicit activation is now expressed by component name rather than by repository option
- `--discover=classes` is the classpath-based development variant
- `car.d` is the expanded archive inspection variant
- `--component-dev-dir` is the preferred component edit/run loop; `car.d` is for expanded archive inspection
- the `02` baseline focuses on packaging and surface inspection; `02.b` is where the same component is executed via class discovery

## Key Learnings

- component artifact basics
- CAR as the formal component package
- explicit activation of packaged CARs from `component.d` by component name
- selector format: `component.service.operation`
