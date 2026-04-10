# 03.a-car-dir-cml-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample shows the expanded generated `car.d` form.
It is the development and test variant of `03-component-cml`.

## Intended Use Case

Use this sample when you want to inspect or edit the expanded generated
component archive contents without first rebuilding a zipped `*.car`.

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

## Run The Whole Scenario

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
  - is the expanded debug/development shape
  - and is auto-activated by the runtime
- no explicit repository option is required for the direct generated `car.d`
  walkthrough

### 1. Inspect the generated component

```bash
bash ../../bin/cncf command meta.help component-cml-sample --format yaml
```

### 2. Inspect generated operation help

```bash
bash ../../bin/cncf command help component-cml-sample.greeting.greeting
```

### 3. Inspect generated metadata

```bash
bash ../../bin/cncf command component-cml-sample.meta.describe --format yaml
```

## Key Learnings

- expanded generated `car.d`
- direct inspection of generated archive contents
- same generated surface as the packaged `CAR` line when launched from a neutral sample
