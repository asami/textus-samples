# 11-subsystem

## Overview

This sample is the baseline formal subsystem line.
It shows the case where the runtime starts a subsystem explicitly by subsystem name, while the component artifact itself is distributed separately as a reusable generic component.

In the `11` line, the forms are split like this:

- `11-subsystem`
  - formal subsystem
  - general component artifact
  - no bundled component in the subsystem artifact
- `11.a-subsystem-bundled-component-lab`
  - explicit subsystem bundling its component artifact
- `11.b-subsystem-mixed-component-lab`
  - explicit subsystem mixing general and bundled components
- `11.c-implicit-subsystem-lab`
  - implicit subsystem
- `11.d-sar-dir-lab`
  - expanded `sar.d`
- `11.e-subsystem-parameter-lab`
  - descriptor-direct startup

## Intended Use Case

Use this sample when you want to learn the baseline explicit subsystem form:

- the subsystem is chosen explicitly
- the component is distributed separately as a reusable generic artifact
- subsystem name and component name are distinct

## What This Sample Will Show

This sample shows:

- startup by `--textus.subsystem=testsubsystem`
- a generic component artifact generated from the sample build output
- a descriptor-only subsystem SAR generated into `component.d`
- the distinction between subsystem name `testsubsystem` and component name `testcomp`

## Files

- `component.d/`
  - the location where the generated generic component artifact and subsystem descriptor artifact are placed for execution
- `car.d/testcomp/`
  - the expanded CAR directory used for archive inspection
- `subsystem.cml`
  - the subsystem descriptor used by `11.e`
- `run.sh`
  - convenience batch runner

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the sample

```bash
sbt --batch compile packageBin
```

### Prepare the runtime artifacts

The reusable component artifact in this sample is a generated CAR, and the explicit subsystem name is resolved through a generated descriptor-only SAR:

```text
testcomp.car
  component/main.jar
  component-descriptor.yaml

testsubsystem.sar
  subsystem-descriptor.yaml
```

The component descriptor is the canonical CAR metadata. `meta/manifest.json` is
not accepted by this sample line.

Build the component jar:

```bash
COMPONENT_BINARY=target/scala-3.3.7/textus-samples-11-subsystem_3-0.1.0-SNAPSHOT.jar
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
subsystem: testsubsystem
EOF
```

Create `testcomp.car`:

```bash
cd /tmp/testcomp.car.d
zip -qr /tmp/testcomp.car component-descriptor.yaml component
mkdir -p component.d
cp /tmp/testcomp.car component.d/testcomp.car
```

Create `testsubsystem.sar`:

```bash
mkdir -p /tmp/testsubsystem.sar.d
cat > /tmp/testsubsystem.sar.d/subsystem-descriptor.yaml <<'EOF'
subsystem: testsubsystem
version: 0.1.0
components:
  - component: testcomp
    coordinate: org.simplemodeling.car:testcomp:0.1.0
EOF
(cd /tmp/testsubsystem.sar.d && zip -qr "$PWD/component.d/testsubsystem.sar" subsystem-descriptor.yaml)
```

Optional expanded `car.d` for inspection:

```bash
rm -rf car.d/testcomp
mkdir -p car.d/testcomp
cd car.d/testcomp
unzip -q ../../component.d/testcomp.car
```

The generated `*.car` file is not tracked in this repository.
The sample keeps the expanded `car.d` inspection shape, while `component.d/testcomp.car`
is treated as a generated runtime artifact.

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

This command runs the full baseline explicit subsystem walkthrough in one shot.
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
  - uses ordinary one-shot CNCF command execution for this sample
- `--textus.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

With the current activation policy:

- `component.d/*.car`
  - is a search target but is not auto-activated by default
- `component.d/testsubsystem.sar`
  - lets the runtime resolve the subsystem by name
- once the subsystem descriptor is selected from `component.d`
  - the runtime activates the matching packaged component source from the same repository for that subsystem context

The same parameter can also be placed in a config file.
That is not a subsystem-specific feature.
It is the ordinary CNCF rule that command-line parameters and config parameters are transparent counterparts.

### 1. Inspect subsystem help

```bash
cncf command meta.help --format yaml --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML
- `--textus.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

### 2. Inspect the component

```bash
cncf command meta.help testcomp --format yaml --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured introspection
- `testcomp`
  - identifies the generic component surface wired into the selected subsystem
- `--format yaml`
  - renders the result in YAML
- `--textus.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

### 3. Inspect operation help

```bash
cncf command help testcomp.main.hello --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the selected subsystem
- `--textus.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

### 4. Execute the operation

```bash
cncf command testcomp.main.hello --textus.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the operation path exposed by the selected subsystem
- `--textus.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

Expected result:

```text
Hello from testcomp in testsubsystem
```
