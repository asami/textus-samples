# 01.b-startup-shapes-lab

## Overview

This is a guided hands-on lab derived from `01-minimal`.
It intentionally carries the same minimal source locally so the learner can focus on one question:

- how does the startup line change between `command`, `server`, and `client`?

Its role inside the `01` line is to teach Textus runtime roles after the
learner has already seen the minimal command selector.
It still does not introduce CML.

This lab helps a learner understand how Textus uses different startup shapes
for different runtime roles, especially:

- `command`
- `server`
- `client`

## Structure

- Local working copy of the `01-minimal` source
- Focused on startup mode comparison
- Compares runtime entry shapes rather than loading sources
- Positioned after `01.a`, which covers development-directory startup vs component repository startup

## How To Run

Run the commands from this directory.
This lab keeps the same `minimal.main.hello` sample so the startup-shape differences are easier to see.

Verification order for this lab is sequential:

1. start `server`
2. confirm server startup is complete
3. probe the server with `curl`
4. check `client`
5. check `command`

Do not run these checks in parallel.

## Lab Goals

By the end of this lab, the learner should understand:

- selector format
- how `command`, `server`, and `client` relate as execution modes
- which parts of the startup command are stable and which parts change by mode

## Guided Steps

### 1. Inspect The Local Files

Open these files:

- `src/main/scala/minimal/MinimalComponent.scala`
- `run-command.sh`
- `run-server.sh`
- `run-client.sh`

Confirm this first:

- the selector used for the command path is still `minimal.main.hello`
- the sample logic stays the same
- only the startup shape changes

### 2. Start The Server Shape First

Run:

```bash
./run-server.sh
```

Observe:

- the process starts in server mode
- the startup line no longer contains a selector
- the process is intended to keep serving rather than finish immediately

Wait until server startup is complete before moving to the next step.
Do not run `client`, `command`, or `invoke` in parallel with server startup.

To stop the process:

- use `Ctrl-C`

Conceptual shape:

```bash
cncf server
```

### 3. Probe The Running Server With `curl`

Run:

```bash
curl -i http://localhost:8080/minimal/main/hello
```

Observe:

- the request is sent from outside the server terminal
- the server shape is observable through HTTP
- `http://localhost:8080/` is still the correct base URL in the current default setup
- `/` is the test-oriented top page for this default server behavior
- the `curl` probe should target the current sample component through the canonical path `/component/service/operation`
- for this sample, that path is `/minimal/main/hello`

Record:

- request URL
- HTTP method
- request body if any
- response status
- response body

### 4. Run The Client Shape After Server Confirmation

Run:

```bash
./run-client.sh
```

Observe:

- by default this lab uses `client --help` so the startup form is visible without requiring a second setup path
- the startup line again contains no selector
- the mode is for remote interaction rather than immediate local command execution

Conceptual shape:

```bash
cncf client --help
```

### 5. Run The Command Shape Last

Run:

```bash
./run-command.sh
```

Observe:

- one selector is executed directly
- the process ends after the command result is produced
- the visible output is `Hello CNCF`

Conceptual shape:

```bash
cncf command minimal.main.hello
```

### 6. Compare The Startup Shapes

Compare the three helpers:

- `command`
  - includes a selector
  - executes one target and exits
- `server`
  - does not include a selector at startup
  - the startup line no longer contains a selector
  - starts a serving process
  - can be observed through HTTP requests such as `curl`
- `client`
  - does not include a selector at startup
  - starts a client-side interaction mode

The internal model is intended to remain aligned even though the startup form differs.

Suggested comparison questions:

- Which mode accepts a selector directly at startup?
- Which mode keeps running after startup?
- Which mode is most useful while implementing one operation?
- Which mode best resembles an interactive tool?

### 7. Compare What Changes And What Stays Stable

What should remain stable:

- Component identity
- Service identity
- Operation identity
- selector semantics

What may change:

- startup mode
- runtime role

What should not change:

- the logical target `minimal.main.hello`
- the meaning of the selector tokens
- the sample contract established by the minimal source
- the local implementation in `src/main/scala/minimal/MinimalComponent.scala`

### 8. Continue To The Next Labs

After understanding `command` / `server` / `client`, continue to:

- `../01.c-builtin-and-help-lab/README.md`
- `../01.d-component-script/README.md`

Those labs explain builtin/help surfaces and script-style operational usage.

## Example Commands

```bash
./run-command.sh
./run-server.sh
./run-client.sh
```

Recommended reading order after running the commands:

1. `../01-minimal/README.md`
2. this lab README
3. `../01.a-invocation-source-lab/README.md`
4. `../../guide/invocation/component-and-subsystem-invocation-guide.md`
5. `../01.c-builtin-and-help-lab/README.md`
6. `../01.d-component-script/README.md`

## Key Learnings

- selector format
- command vs server vs client startup comparison
- one local source tree can be started through multiple runtime shapes
- stable selector semantics across execution modes
