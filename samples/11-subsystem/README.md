# 09-subsystem

## Overview

This sample is the baseline formal subsystem line.
It shows the case where the runtime starts a subsystem explicitly by subsystem name, while the component artifact itself is distributed separately as a reusable generic component.

In the `09` line, the forms are split like this:

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

- a generic component artifact in `component.d/testcomp.car`
- startup by `--textus.runtime.subsystem=testsubsystem`
- the distinction between subsystem name `testsubsystem` and component name `testcomp`

## Files

- `component.d/testcomp.car`
  - the generic component artifact
- `car.d/testcomp/`
  - the expanded CAR directory used for development and inspection
- `subsystem.cml`
  - the subsystem descriptor used by `09.e`
- `run.sh`
  - convenience batch runner

## Setup

### Prepare the cozy command

```bash
../../bin/setup cozy
```

### Build the sample

```bash
sbt --batch clean compile packageBin
```

### Prepare the generic component CAR

The reusable component artifact in this sample is a CAR:

```text
testcomp.car
  component/main.jar
  meta/manifest.json
```

`meta/manifest.json` is still used by the current runtime for archive loading.
This is a transitional compatibility shape. The planned direction is to retire
manifest-specific handling and converge CAR/SAR metadata into a top-level
descriptor.

The manifest in this sample is:

```json
{
  "name": "testcomp",
  "version": "0.1.0",
  "component": "testcomp",
  "subsystem": "testsubsystem"
}
```

Build the component jar:

```bash
COMPONENT_BINARY=target/scala-3.3.7/cncf-samples-09-subsystem_3-0.1.0-SNAPSHOT.jar
```

Prepare the CAR work directory:

```bash
rm -rf /tmp/testcomp.car.d
mkdir -p /tmp/testcomp.car.d/component /tmp/testcomp.car.d/meta
cp "$COMPONENT_BINARY" /tmp/testcomp.car.d/component/main.jar
cat > /tmp/testcomp.car.d/meta/manifest.json <<'EOF'
{
  "name": "testcomp",
  "version": "0.1.0",
  "component": "testcomp",
  "subsystem": "testsubsystem"
}
EOF
```

Create `testcomp.car`:

```bash
cd /tmp/testcomp.car.d
zip -qr /tmp/testcomp.car component meta
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
- `--no-default-components`
  - prevents duplicate loading from the default runtime search path
- `--textus.runtime.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

The same parameter can also be placed in a config file.
That is not a subsystem-specific feature.
It is the ordinary CNCF rule that command-line parameters and config parameters are transparent counterparts.

### 1. Inspect subsystem help

```bash
bash ../../bin/cncf command meta.help --format yaml --no-default-components --textus.runtime.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML
- `--no-default-components`
  - suppresses duplicate default repository loading during this explicit subsystem run
- `--textus.runtime.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

### 2. Inspect the component

```bash
bash ../../bin/cncf command meta.help testcomp --format yaml --no-default-components --textus.runtime.subsystem=testsubsystem
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
- `--no-default-components`
  - suppresses duplicate default repository loading during this explicit subsystem run
- `--textus.runtime.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

### 3. Inspect operation help

```bash
bash ../../bin/cncf command help testcomp.main.hello --no-default-components --textus.runtime.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the selected subsystem
- `--no-default-components`
  - suppresses duplicate default repository loading during this explicit subsystem run
- `--textus.runtime.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

### 4. Execute the operation

```bash
bash ../../bin/cncf command testcomp.main.hello --no-default-components --textus.runtime.subsystem=testsubsystem
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the operation path exposed by the selected subsystem
- `--no-default-components`
  - suppresses duplicate default repository loading during this explicit subsystem run
- `--textus.runtime.subsystem=testsubsystem`
  - selects the subsystem named `testsubsystem`

Expected result:

```text
Hello from testcomp in testsubsystem
```
