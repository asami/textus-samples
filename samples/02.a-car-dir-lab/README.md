# 02.a-car-dir-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample shows the expanded `car.d` form.
It is the development and test variant of `02-component`.

## Intended Use Case

Use this sample when you want to inspect or edit the expanded component archive contents before packaging them back into a runnable `*.car`.

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
  meta/manifest.json
```

## Run The Whole Scenario

This command runs the full expanded-CAR walkthrough in one shot.
It loads `car.d` directly as the formal development-time CAR layout.

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--no-default-components`
  - keeps the walkthrough focused on the explicit `car.d` repository
- `--component-repository=component-dir:car.d`
  - loads the expanded CAR directory directly

With the current activation policy:

- `car.d`
  - is the expanded debug/development shape
  - and may be auto-activated by the runtime
- this sample still uses an explicit repository and `--no-default-components`
  - so the walkthrough stays isolated and deterministic

### 1. Inspect the component

```bash
bash ../../bin/cncf --no-default-components --component-repository=component-dir:car.d command meta.help testcomp --format yaml
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
- `--no-default-components`
  - excludes unrelated default repositories
- `--component-repository=component-dir:car.d`
  - loads the expanded CAR directory directly

### 2. Inspect operation help

```bash
bash ../../bin/cncf --no-default-components --component-repository=component-dir:car.d command help testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `testcomp.main.hello`
  - identifies the operation path exposed by the expanded CAR
- `--no-default-components`
  - excludes unrelated default repositories
- `--component-repository=component-dir:car.d`
  - loads the expanded CAR directory directly

### 3. Execute the operation

```bash
bash ../../bin/cncf --no-default-components --component-repository=component-dir:car.d command testcomp.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `testcomp.main.hello`
  - selects the sample operation exposed by the expanded CAR
- `--no-default-components`
  - excludes unrelated default repositories
- `--component-repository=component-dir:car.d`
  - loads the expanded CAR directory directly

Expected result:

```text
Hello from testcomp
```

## Key Learnings

- expanded `car.d`
- development-time archive inspection
- direct execution from the expanded CAR layout
