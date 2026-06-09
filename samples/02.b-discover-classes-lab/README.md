# 02.b-discover-classes-lab

This directory name is historical. The sample now demonstrates the current
launcher-based development source form: `cncf dev ...`.

## Overview

CNCF samples use two launchers with different roles: `textus` is for
application/user execution, while `cncf` is the development launcher for CNCF
components and runtime surfaces. This sample runs a Scala component that is under
development, so it uses `cncf dev ...` directly.

Use this sample when you are actively developing a Scala component and want CNCF
to run it from the component development directory without packaging a CAR.

## Setup

Install the CNCF launcher once:

```bash
cs install --force cncf \
  --channel https://www.simplemodeling.org/repository/textus/coursier-channel.json
```

Compile the sample:

```bash
sbt --batch clean compile
```

## Run The Whole Scenario

```bash
bash run.sh
```

## Command Walkthrough

### 1. Inspect the component

```bash
cncf dev command --project-dev . meta.help testcomp --format yaml
```

### 2. Inspect operation help

```bash
cncf dev command --project-dev . help testcomp.main.hello
```

### 3. Execute the operation

```bash
cncf dev command --project-dev . testcomp.main.hello
```

Expected result:

```text
Hello from testcomp
```

## Key Learnings

- `--project-dev .` auto activation is the current component edit/run loop.
- The component selector is the same as packaged CAR execution.
- The old class-discovery flag is not part of the current sample path.
