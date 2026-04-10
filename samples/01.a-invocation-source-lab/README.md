# 01.a-invocation-source-lab

## Overview

This is a guided hands-on lab derived from `01-minimal`.
It intentionally carries the same minimal source locally so the learner can experiment in this directory without moving back to `../01-minimal`.

Its role is to help a learner understand how the same selector is invoked through two different loading paths:

- development-time class discovery
- deployment-style repository loading

## Structure

- Local working copy of the `01-minimal` source
- Focused on `run.sh` vs `invoke.sh`
- Compares loading sources rather than startup modes

This lab does not introduce a new Component design.
It reuses the same `minimal.main.hello` example so the learner can isolate one question:

- where does the active Component come from?

## How To Run

Run the commands from this directory.
The point of the lab is that both commands execute the same selector against the same local source tree, but they load the Component through different paths.

## Lab Goals

By the end of this lab, the learner should understand:

- selector format
- `run.sh` vs `invoke.sh`
- class discovery vs repository loading
- which parts of the command stay stable when the loading source changes

## Guided Steps

### 1. Inspect The Local Source

Open these files first:

- `src/main/scala/minimal/MinimalComponent.scala`
- `component.d/minimal.md`
- `run.sh`
- `invoke.sh`

Confirm these points:

- the Scala implementation class is `MinimalComponent`
- the runtime Component name is `minimal`
- the selector stays `minimal.main.hello`

### 2. Run The Development-Time Path

Run:

```bash
./run.sh
```

Observe:

- `run.sh` calls the shared sample runner
- it enables `--discover=classes`
- it targets `minimal.main.hello`
- the execution source is the compiled classes in this sample directory

Conceptual shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"
```

### 3. Run The Deployment-Style Path

Run:

```bash
./invoke.sh
```

Observe:

- `invoke.sh` first runs `sbt package`
- it copies the built jar into `samples/component.d`
- then it activates that directory through `--component-dir ../component.d`
- the selector is still `minimal.main.hello`

Conceptual shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --component-dir=../component.d command minimal.main.hello"
```

### 4. Compare The Two Loading Sources

What stays stable:

- selector
- Component name
- Service name
- Operation name
- visible behavior

What changes:

- active loading source
- packaging step
- whether the jar is copied into the virtual repository

This is the key lesson of this lab:

- one logical selector
- two different loading paths
- same functional result

### 5. Continue To The Next Labs

After understanding invocation sources, continue to:

- `../01.b-startup-shapes-lab/README.md`
- `../01.c-builtin-and-help-lab/README.md`
- `../01.d-component-script/README.md`

## Example Commands

```bash
./run.sh
./invoke.sh
```

Recommended reading order:

1. `../01-minimal/README.md`
2. this lab README
3. `../01.b-startup-shapes-lab/README.md`
4. `../../guide/invocation/component-and-subsystem-invocation-guide.md`
5. `../01.c-builtin-and-help-lab/README.md`
6. `../01.d-component-script/README.md`

## Key Learnings

- selector format
- development-time vs deployment-style invocation
- class discovery vs repository loading
- one local source tree can support both observations
- stable selector semantics across loading sources
