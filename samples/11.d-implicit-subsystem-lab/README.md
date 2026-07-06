# 11.d-implicit-subsystem-lab

## Overview

This sample explains the implicit subsystem shape.
There is no explicit `Subsystem` definition here.
Instead, CNCF discovers the sample component from compiled classes and treats the effective runtime as a minimum subsystem.

This is the shorthand form of subsystem execution.
The user still runs a normal component selector, but the selector is executed inside a runtime-composed subsystem.

## Intended Use Case

Use this sample when you want to understand:

- how a component-only sample still runs as a subsystem
- what CNCF supplies implicitly around the sample component
- why explicit subsystem definition is a later, more formal step rather than the first executable requirement

## Structure

- implicit subsystem
- one component: `subsystem`
- one service: `main`
- one operation: `hello`

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the sample

```bash
sbt --batch compile
```

## Shortcut: Run The Whole Scenario
This is the shortcut verification path, not the teaching path.
For learning, read the explicit command sequence in `Command Walkthrough` first and type those commands by hand.
`run.sh` should be treated as the batch form that replays the documented commands.

```bash
bash run.sh
```

This is the convenience batch form of the walkthrough below.

## Command Walkthrough

The commands below use the standard CNCF CLI entry point.
In this repository it is invoked as `cncf`.

The common option is:

- current-directory project auto activation
  - tells CNCF to discover the sample component from compiled classes and assemble the effective runtime

### 1. Inspect subsystem help

```bash
cncf command meta.help --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured subsystem introspection
- `--format yaml`
  - renders the result in YAML

Expected result:
- builtin runtime components are visible
- the discovered sample component `subsystem` also appears

### 2. Inspect the discovered component

```bash
cncf command meta.help subsystem --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `meta.help`
  - selects structured component introspection
- `subsystem`
  - identifies the sample component discovered into the implicit subsystem
- `--format yaml`
  - renders the result in YAML

### 3. Inspect operation help

```bash
cncf command help subsystem.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `help`
  - selects the CLI-oriented help entry point
- `subsystem.main.hello`
  - identifies the operation selector hosted by the implicit subsystem

### 4. Execute the operation

```bash
cncf command subsystem.main.hello
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step
- `subsystem.main.hello`
  - selects the sample operation exposed from the discovered component

Expected result:

```text
Hello from the minimum subsystem
```

## What To Notice

- the subsystem exists even though there is no explicit `Subsystem` definition file
- the visible command path is still component-oriented
- builtin runtime components and the discovered sample component coexist in the same effective subsystem
- this is the implicit form that `11-subsystem` will contrast with the explicit subsystem form

## Key Learnings

- implicit subsystem
- component discovery as runtime composition
- minimum executable subsystem without explicit subsystem definition
