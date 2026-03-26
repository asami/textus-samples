# 01.b-startup-shapes-lab

## Overview

This is a guided hands-on lab built on top of `01-minimal`.
Its role is to help a learner understand how CNCF uses different startup shapes for different runtime roles, especially:

- `command`
- `server`
- `client`

## Structure

- Companion lab for `01-minimal`
- Focused on startup mode comparison
- No new core pattern beyond the `01-minimal` base
- Compares runtime entry shapes rather than loading sources

## How To Run

This lab depends on `01-minimal`.
Use this README as a guided observation sequence.

Prerequisite:

- `../01-minimal` must already be available and runnable
- the examples below should be executed from this repository checkout

## Lab Goals

By the end of this lab, the learner should understand:

- selector format
- how `command`, `server`, and `client` relate as execution modes
- which parts of the startup command are stable and which parts change by mode

## Guided Steps

### 1. Start With The Command Mode Shape

Run the command mode explicitly:

```bash
cd ../01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"
```

Observe:

- one selector is executed directly
- the process ends after the command result is produced

Question to answer:

- which part of the startup line is the selector?

Expected answer:

- `minimal.main.hello`

### 2. Start The Server Shape

Run:

```bash
cd ../01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain server"
```

Observe:

- the startup line no longer contains a selector
- the process is intended to keep serving rather than finish immediately

Question to answer:

- what disappeared from startup compared with command mode?

Expected answer:

- the explicit selector disappeared from the startup line

### 3. Start The Client Shape

Run:

```bash
cd ../01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain client"
```

Observe:

- the startup line again contains no selector
- the process enters a client-side interaction mode instead of running one command and exiting

### 4. Compare The Startup Shapes

The command-mode startup is conceptually:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"
```

The server-mode startup is conceptually:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain server"
```

The client-mode startup is conceptually:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain client"
```

At this stage, the important comparison is structural:

- `command`
  - executes one selector directly
- `server`
  - starts a serving process
- `client`
  - starts a client-side interaction mode

The internal model is intended to remain aligned even though the startup form differs.

Suggested comparison questions:

- Which mode accepts a selector directly at startup?
- Which mode keeps running after startup?
- Which mode is most useful while implementing one operation?
- Which mode best resembles an interactive tool?

### 5. Compare What Changes And What Stays Stable

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
- the sample contract established by `01-minimal`

### 6. Continue To The Next Labs

After understanding `command` / `server` / `client`, continue to:

- `../01.c-builtin-and-help-lab/README.md`
- `../01.d-component-script/README.md`

Those labs explain builtin/help surfaces and script-style operational usage.

## Example Commands

```bash
cd ../01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"
sbt --batch "runMain org.goldenport.cncf.CncfMain server"
sbt --batch "runMain org.goldenport.cncf.CncfMain client"
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
- stable selector semantics across execution modes
