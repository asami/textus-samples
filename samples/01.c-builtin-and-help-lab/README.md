# 01.c-builtin-and-help-lab

## Overview

This is a guided hands-on lab derived from `01-minimal`.
It intentionally keeps the same minimal source locally so the learner can observe two things side by side:

- the sample-defined component `minimal`
- runtime-provided builtin and help-oriented command surfaces

## Structure

- Local working copy of the `01-minimal` source
- Focused on runtime-provided command surfaces
- Distinguishes sample-defined commands from builtin ones

## How To Run

Run the commands from this directory.
This lab keeps the same `minimal.main.hello` sample, but the learning target is the surrounding runtime surface:

- `meta.help`
- `help <selector>`
- builtin commands such as `admin.system.ping`

This lab uses three viewpoints:

- `command`
  - most common for development-time testing
  - next for learning builtin/help structure quickly
  - also useful for management commands built on top of component logic
- `client`
  - closer to real remote operation against a running server
- `server` + `curl`
  - useful for observing the exposed HTTP surface directly

## Lab Goals

By the end of this lab, the learner should understand:

- the difference between a sample-defined selector and a builtin/runtime-provided selector
- the difference between `help` as CLI navigation and `meta.help` as structured introspection
- how builtin commands can coexist with the sample component in the same runtime
- why `command` is used first for development-time testing, then for learning, and then for management commands built on component logic
- why `client` is often the real operational entry point against a remote server

## Guided Steps

### 1. Inspect The Local Source

Open these files:

- `src/main/scala/minimal/MinimalComponent.scala`
- `run-subsystem-help.sh`
- `run-component-help.sh`
- `run-operation-help.sh`
- `run-admin-ping.sh`

Confirm this first:

- `minimal.main.hello` is still the sample-defined operation
- the help and admin commands are not implemented inside `MinimalComponent.scala`
- those additional surfaces come from the CNCF runtime

### 2. Observe Subsystem-Level Help

Run:

```bash
./run-subsystem-help.sh
```

Observe:

- this is not calling `minimal.main.hello`
- the output describes the current subsystem/runtime surface
- builtin and sample-provided surfaces appear together

Conceptual shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command meta.help"
```

### 3. Observe Component-Level Help For `minimal`

Run:

```bash
./run-component-help.sh
```

Observe:

- this targets `minimal`, not the whole subsystem
- the output should describe the sample component
- `meta` / `system` visibility may appear as runtime-provided surfaces around the sample component

Conceptual shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command meta.help minimal"
```

### 4. Observe Operation Help Through The `help` Alias

Run:

```bash
./run-operation-help.sh
```

Observe:

- the visible entry command is `help`
- the real inspection target is `minimal.main.hello`
- this is the user-facing navigation surface

Conceptual shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help minimal.main.hello"
```

Key distinction:

- `help` is the CLI-oriented navigation entry
- `meta.help` is the structured introspection protocol

### 5. Observe A Builtin Runtime Command

Run:

```bash
./run-admin-ping.sh
```

Observe:

- this command is not defined in `MinimalComponent.scala`
- it comes from a builtin runtime surface
- it helps distinguish runtime-provided commands from sample-defined commands

Conceptual shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command admin.system.ping"
```

### 6. Compare Sample-Defined And Builtin Surfaces

Sample-defined:

- `minimal.main.hello`

Runtime-provided:

- `meta.help`
- `help minimal.main.hello`
- `admin.system.ping`

Questions to answer:

- which commands are implemented in the sample source?
- which commands are added by the CNCF runtime?
- which command is for navigation?
- which command is for structured introspection?

Expected usage frequency for `command` in this project:

1. development-time testing
2. learning and inspection
3. management commands built on top of component logic

### 7. Compare With Real Operational Entry Points

After the command-mode observations are clear, compare them with the runtime forms used outside local study.

Client is the more typical operational entry point when a remote server is already running.

Client example:

```bash
../01.b-startup-shapes-lab/run-client.sh
```

This does not replace the command-mode checks.
It shows how the same runtime surface is usually approached in remote operation.

Server + curl example:

```bash
../01.b-startup-shapes-lab/run-server.sh
```

Then inspect from another terminal:

```bash
curl -i http://localhost:8080/
```

Compare these questions:

- which observations are easiest in `command` mode while learning?
- which operations are more natural through `client` in real deployment?
- which surfaces become visible only after a server is running?
- which surfaces are navigation-oriented and which are runtime-oriented?

### 8. Continue To The Next Lab

After understanding builtin/help surfaces, continue to:

- `../01.d-component-script/README.md`

## Example Commands

```bash
./run-subsystem-help.sh
./run-component-help.sh
./run-operation-help.sh
./run-admin-ping.sh
```

## Key Learnings

- builtin Components
- help-oriented command surfaces
- runtime discovery commands
- difference between `help` and `meta.help`
- difference between sample-defined and runtime-provided commands
- `command` first for development-time testing, next for learning, then for management commands
- `client` for real remote usage
- `server` + `curl` for HTTP-surface observation
