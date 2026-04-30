# 02.a-car-dir-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample shows the expanded `car.d` form.
It is the CAR loader/debug variant of `02-component`.

## Intended Use Case

Use this sample when you want to inspect or edit the expanded component archive contents before packaging them back into a runnable `*.car`.
For the normal sbt edit/run loop, prefer `--component-dev-dir <project>` so the
runtime uses the development classpath and `src/main/car` without rebuilding a
CAR.

## Files

- `car.d/`
  - expanded CAR directory
- `run.sh`
  - convenience batch runner

## Setup

### Build the sample

```bash
sbt --batch compile packageBin
```

### Prepare the expanded `car.d`

The main development shape is:

```text
car.d/
  descriptor.yaml
  component/main.jar
```

## Run The Whole Scenario

This command runs the full expanded-CAR walkthrough in one shot.
It loads `car.d` directly as an expanded CAR directory.

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample

With the current activation policy:

- `car.d`
  - is the expanded CAR debug/inspection shape
  - and is activated explicitly with `--component-car-dir car.d`

### 1. Inspect the component

```bash
bash ../../bin/cncf --component-car-dir car.d command meta.help testcomp --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured component introspection
- `testcomp`
  - identifies the component loaded from `car.d`
- `--format yaml`
  - renders the result in YAML

### 2. Inspect operation help

```bash
bash ../../bin/cncf --component-car-dir car.d command help testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the expanded CAR

### 3. Execute the operation

```bash
bash ../../bin/cncf --component-car-dir car.d command testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the sample operation exposed by the expanded CAR

Expected result:

```text
Hello from testcomp
```

## Key Learnings

- expanded `car.d`
- archive loader/debug inspection
- direct execution from the expanded CAR layout
