# 03.b-discover-classes-cml-lab

This directory name is historical. The sample now demonstrates the current
launcher-based CML development source form: `cncf dev ... --component-dev-dir .`.

## Overview

CNCF samples use two launchers with different roles: `textus` is for
application/user execution, while `cncf` is the development launcher for CNCF
components and runtime surfaces. This sample runs a generated CML component that is under
development, so it uses `cncf dev ... --component-dev-dir .` directly.

Use this sample when you are actively editing `component.cml` and want CNCF to
run the generated component from the component development directory without
packaging a CAR.

## Setup

Install the CNCF launcher once:

```bash
cs install cncf
```

Compile the generated sample:

```bash
sbt --batch clean compile
```

## Run The Whole Scenario

```bash
bash run.sh
```

## Command Walkthrough

### 1. Inspect the generated component

```bash
cncf dev command --project . --component-dev-dir . meta.help component-cml-sample --format yaml
```

### 2. Inspect generated operation help

```bash
cncf dev command --project . --component-dev-dir . help component-cml-sample.greeting.greeting
```

### 3. Inspect generated metadata

```bash
cncf dev command --project . --component-dev-dir . component-cml-sample.meta.describe --format yaml
```

## Key Learnings

- `--component-dev-dir .` is the current generated component edit/run loop.
- CAR and `car.d` remain the packaged-source inspection paths.
- The old class-discovery flag is not part of the current sample path.
