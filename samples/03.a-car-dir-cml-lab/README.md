# 03.a-car-dir-cml-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample shows the expanded generated `car.d` form.
It is the CAR loader/debug variant of `03-component-cml`.

## Intended Use Case

Use this sample when you want to inspect or edit the expanded generated
component archive contents without first rebuilding a zipped `*.car`.
For the normal Cozy/sbt edit/run loop, prefer `--component-dev-dir <project>`
so generated classes and `src/main/car` are used directly.

## Files

- `src/main/cozy/component.cml`
  - the source model
- `car.d/`
  - expanded generated CAR directory
- `run.sh`
  - convenience batch runner

## Setup

### Build the generated sample

```bash
sbt --batch compile packageBin
```

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

This command runs the full expanded generated-CAR walkthrough in one shot.

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

### 1. Inspect the generated component

```bash
cncf dev command --project-dev . --no-project-classpath --component-car-dir car.d meta.help component-cml-sample --format yaml
```

### 2. Inspect generated operation help

```bash
cncf dev command --project-dev . --no-project-classpath --component-car-dir car.d help component-cml-sample.greeting.greeting
```

### 3. Inspect generated metadata

```bash
cncf dev command --project-dev . --no-project-classpath --component-car-dir car.d component-cml-sample.meta.describe --format yaml
```

## Key Learnings

- expanded generated `car.d`
- direct inspection of generated archive contents
- same generated surface as the packaged `CAR` line when launched from a neutral sample
