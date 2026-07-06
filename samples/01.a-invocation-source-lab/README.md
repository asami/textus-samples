# 01.a-invocation-source-lab

## Overview

This is a guided hands-on lab derived from `01-minimal`.
It intentionally carries the same minimal source locally so the learner can experiment in this directory without moving back to `../01-minimal`.

Its role is to help a learner understand how the same Textus selector is
invoked through two different Component startup sources:

- development-directory startup
- component repository startup

This lab is not about comparing `run.sh` and `invoke.sh` as shell scripts.
Those files are only convenience entry points.
The learning target is where the active Component comes from.

## Structure

- Local working copy of the `01-minimal` source
- Focused on active Component source
- Compares development-directory startup with component repository startup
- Does not introduce CML

This lab does not introduce a new Component design.
It reuses the same `minimal.main.hello` example so the learner can isolate one question:

- where does the active Component come from?

## How To Run

Run the commands from this directory.
The point of the lab is that both commands execute the same selector against the same local source tree, but they load the Component through different paths.

## Lab Goals

By the end of this lab, the learner should understand:

- selector format
- development-directory startup
- component repository startup
- which parts of the command stay stable when the loading source changes

## Guided Steps

### 1. Inspect The Local Source

Open these files first:

- `src/main/scala/minimal/MinimalComponent.scala`
- `run.sh`
- `invoke.sh`

Confirm these points:

- the Scala implementation class is `MinimalComponent`
- the runtime Component name is `minimal`
- the selector stays `minimal.main.hello`
- CML is intentionally not used in the `01` line

### 2. Start From The Development Directory

Run:

```bash
./run.sh
```

Observe:

- the startup target is the current sample directory
- the Component is loaded from the actively developed project
- the selector is `minimal.main.hello`

Conceptual shape:

```text
cncf command minimal.main.hello
```

### 3. Start From The Component Repository

Run:

```bash
./invoke.sh
```

Observe:

- the sample is packaged first
- the packaged Component is placed in the repository-style artifact directory
- the active Component is loaded from that repository source
- the selector is still `minimal.main.hello`

Conceptual shape:

```text
cncf command --no-project-classpath --component-dir ../component.d minimal.main.hello
```

### 4. Compare The Two Startup Sources

What stays stable:

- selector
- Component name
- Service name
- Operation name
- visible behavior

What changes:

- active Component source
- packaging step
- whether the startup uses the development directory or the component repository

This is the key lesson of this lab:

- one logical selector
- two different Component startup sources
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
- development-directory startup
- component repository startup
- one local source tree can support both observations
- stable selector semantics across loading sources
