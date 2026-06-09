# 03.c-method-execution-cml-lab

Before running the sample, prepare the local CNCF command once:

```bash
bash ../../bin/setup cncf
```

## Overview

This sample adds executable method behavior to the new `03` CML series.

Unlike `03-component-cml`, `03.a`, and `03.b`, which focus on generated surface
and loading shape comparison, this lab adds executable behavior and runs the
generated component from a packaged CAR.

## Intended Use Case

Use this sample when you want to confirm:

- a generated CML component can keep its modeled surface
- executable behavior can be packaged into the generated CAR
- method execution can be tested from the packaged CAR shape

## Files

- `src/main/cozy/component-method-execution.cml`
  - the source model
- `src/main/scala/org/sample/componentmethodexecution/ComponentMethodExecutionSampleFactory.scala`
  - the execution factory packaged into the sample jar
- `repository.d/component-method-execution-sample.car`
  - the packaged generated component artifact used for execution
- `run.sh`
  - convenience batch runner

## Setup

### Prepare the `cozy` command

```bash
../../bin/setup cozy
```

### Build the generated sample

```bash
sbt --batch clean compile packageBin
```

## Run The Whole Scenario

This command runs the full method-execution walkthrough in one shot.

```bash
bash run.sh
```

## Command Walkthrough

The common parameters are:

- `command`
  - uses ordinary one-shot CNCF command execution for this sample
- `--repository-dir repository.d`
  - adds the packaged generated component artifact directory to the search repository
- `--textus.component=component-method-execution-sample`
  - explicitly activates the packaged generated component by name

### 1. Inspect the generated component

```bash
REPO_DIR="$PWD/repository.d"
cncf dev command --project-dev . --no-project-classpath --repository-dir "$REPO_DIR" --textus.component=component-method-execution-sample command meta.help component-method-execution-sample --format yaml
```

### 2. Inspect operation help

```bash
REPO_DIR="$PWD/repository.d"
cncf dev command --project-dev . --no-project-classpath --repository-dir "$REPO_DIR" --textus.component=component-method-execution-sample command help component-method-execution-sample.greeting.compose-greeting
```

### 3. Execute the method

```bash
REPO_DIR="$PWD/repository.d"
cncf dev command --project-dev . --no-project-classpath --repository-dir "$REPO_DIR" --textus.component=component-method-execution-sample command component-method-execution-sample.greeting.compose-greeting --name Alice
```

Expected result:

```json
{"message":"Hello, Alice"}
```

## Key Learnings

- generated method execution packaged into a CAR
- CAR-based execution path for generated components
- separation between modeled surface and packaged execution logic
