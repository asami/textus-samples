# 06.b-job-control-demo-lab

## Overview

`08.b-job-control-demo-lab` is the internal-proof companion to `08.a-job-control-lab`.

- [06-job] shows job observation as a user-facing shell surface
- [06.a-job-control-lab] shows job control as a user-facing shell surface
- `08.b-job-control-demo-lab` explains where the lower-level direct-framework proof now lives

This sample no longer uses a same-JVM demo main as the user-facing path.

The direct framework proof was moved to `cozy` scripted, because it is valuable mainly as an internal runtime assertion.

## CNCF Approach

By the time the user reaches `06.a`, the user-facing story is already complete:

- submit a control-ready job
- suspend it
- resume it
- cancel it
- inspect history and related events

What remains after that is not a user-facing sample concern.

It is framework verification:

- direct `Subsystem` bootstrapping
- direct `component.logic.submitJob(...)`
- direct `component.logic.controlJob(...)`
- direct `jobEngine` / `eventStore` inspection

Those checks are still useful, but they belong in scripted verification rather than in the sample path.

## Intended Use Case

Use this sample entry when you want to understand:

- why there is no additional end-user shell flow after `06.a`
- that the direct-framework proof still exists
- that the proof has been relocated to `cozy` scripted on purpose

This is mainly useful for maintainers who want to know where the lower-level verification went.

## Files

- `src/main/cozy/job-control-lab.cml`
  - the source model shared with the job-control proof line
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - convenience wrapper for the documented shell commands

## Setup

### 1. Prepare the `cozy` command

```bash
$ cd samples/08.b-job-control-demo-lab
$ ../../bin/setup cozy
```

This prepares the local `cozy` launcher used by `sbt-cozy`.

### 2. Build the generated sample

```bash
$ cd samples/08.b-job-control-demo-lab
$ sbt --batch clean compile
```

This generates the component surface from `job-control-lab.cml`.

## Run The Whole Scenario

```bash
$ cd samples/08.b-job-control-demo-lab
$ bash run.sh
```

`run.sh` is only a convenience batch runner.

It is the batch form of the walkthrough below.

## Command Walkthrough

This sample uses:

```bash
bash cncf dev command --project-dev . ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this repository it is invoked through `cncf dev`
- `--project-dev .` auto activation:
  - loads the locally generated classes under `target/`
- `command`:
  - runs one-shot CNCF command execution without starting a persistent server
- `help`:
  - asks CNCF to describe the selected component, service, or operation instead of executing it

### 1. Inspect the component surface

```bash
$ cncf dev command --project-dev . help job-control-lab
```

Parameters:
- `command`
  - uses one-shot CNCF command execution for this step
- `help`
  - asks CNCF to describe the selected component
- `job-control-lab`
  - selects the generated component

This confirms that the generated component surface still exists, even though the direct framework proof is no longer exposed as the sample path.

### 2. Inspect the control-ready submit operation

```bash
$ cncf dev command --project-dev . help job-control-lab.item.create-item
```

Parameters:
- `command`
  - uses one-shot CNCF command execution for this step
- `help`
  - asks CNCF to describe the selected operation
- `job-control-lab.item.create-item`
  - selects the modeled command surface

This is the same user-facing selector that is exercised more fully in `06.a`.

### 3. Inspect component metadata

```bash
$ cncf dev command --project-dev . job-control-lab.meta.describe --format yaml
```

Parameters:
- `command`
  - uses one-shot CNCF command execution for this step
- `job-control-lab.meta.describe`
  - selects the component metadata endpoint
- `--format yaml`
  - requests YAML output

This shows the generated surface that the relocated scripted proof is built on.

## What Moved To Scripted

The following direct-framework proof was moved out of the sample path and into `cozy` scripted:

- direct `DefaultSubsystemFactory` bootstrapping
- direct `component.logic.submitJob(...)`
- direct `component.logic.controlJob(...)`
- direct `jobEngine.queryTimeline(...)`
- direct `eventStore.query(...)`

That proof now lives in the `cozy` scripted fixture for this lab.

## Difference From 06.a

- `06.a`
  - the user-facing shell-first job-control sample
- `06.b`
  - the record of where the lower-level direct-framework proof went

So `06.b` is no longer a second user path.
It is the relocation point for framework-level verification.
