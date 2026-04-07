# 03.b-discover-classes-cml-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample shows `--discover=classes` for a generated CML component.
It is the development-time implicit loading line of `03-component-cml`.

## Intended Use Case

Use this sample when you are actively editing `component.cml` and want CNCF to
load the generated component directly from compiled classes without packaging a
CAR first.

## Files

- `src/main/cozy/component.cml`
  - the source model
- `run.sh`
  - convenience batch runner

## Setup

### Prepare the `cozy` command

```bash
../../bin/setup cozy
```

### Build the generated sample

```bash
sbt --batch clean compile
```

## Run The Whole Scenario

This command runs the full generated class-discovery walkthrough in one shot.

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--discover=classes`
  - tells CNCF to discover the generated component from compiled classes

### 1. Inspect the generated component

```bash
bash ../../bin/cncf --discover=classes command meta.help component-cml-sample --format yaml
```

### 2. Inspect generated operation help

```bash
bash ../../bin/cncf --discover=classes command help component-cml-sample.greeting.greeting
```

### 3. Inspect generated metadata

```bash
bash ../../bin/cncf --discover=classes command component-cml-sample.meta.describe --format yaml
```

## Key Learnings

- generated component loading through `--discover=classes`
- no packaging step during active CML development
- same generated surface as the `CAR` and `car.d` lines
