# 03.b-discover-classes-cml-lab

This directory name is historical. The sample now demonstrates the current
launcher-based CML development source form: `cncf dev ...`.

## Overview

CNCF samples use two launchers with different roles: `textus` is for
application/user execution, while `cncf` is the development launcher for CNCF
components and runtime surfaces. This sample runs a generated CML component that is under
development, so it uses `cncf dev ...` directly.

Use this sample when you are actively editing `component.cml` and want CNCF to
run the generated component from the component development directory without
packaging a CAR.

## Setup

Install the CNCF launcher once:

```bash
cs install --force cncf \
  --channel https://www.simplemodeling.org/repository/textus/coursier-channel.json
```

Compile the generated sample:

```bash
sbt --batch clean compile
```

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

```bash
bash run.sh
```

## Command Walkthrough

### 1. Inspect the generated component

```bash
cncf dev command --project-dev . meta.help component-cml-sample --format yaml
```

### 2. Inspect generated operation help

```bash
cncf dev command --project-dev . help component-cml-sample.greeting.greeting
```

### 3. Inspect generated metadata

```bash
cncf dev command --project-dev . component-cml-sample.meta.describe --format yaml
```

## Key Learnings

- `--project-dev .` auto activation is the current generated component edit/run loop.
- CAR and `car.d` remain the packaged-source inspection paths.
- The old class-discovery flag is not part of the current sample path.
