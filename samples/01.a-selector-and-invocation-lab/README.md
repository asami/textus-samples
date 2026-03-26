# 01.a-selector-and-invocation-lab

## Overview

This is a guided hands-on lab built on top of `01-minimal`.
Its role is to help a learner understand the CNCF execution model by operating an already minimal sample.
This is the place where the repository compares selector usage and multiple startup styles, including `command`, `server`, and `client`.

## Structure

- Companion lab for `01-minimal`
- Focused on observation and repetition
- No new core pattern beyond the `01-minimal` base
- Compares startup methods rather than introducing a new architectural pattern

## How To Run

This lab depends on `01-minimal`.
Use this README as a guided observation sequence.

## Lab Goals

By the end of this lab, the learner should understand:

- selector format
- `run.sh` vs `invoke.sh`
- class discovery vs repository loading
- how `command`, `server`, and `client` relate as execution modes
- which parts of the startup command are stable and which parts change by mode

## Guided Steps

### 1. Observe The Minimal Command Path

Start with the minimal selector:

```bash
cd ../01-minimal
./run.sh
```

Question to answer:

- which selector is being executed?

Expected answer:

- `minimal.main.hello`

### 2. Compare Development-Time And Deployment-Style Invocation

Run:

```bash
cd ../01-minimal
./run.sh
./invoke.sh
```

Compare:

- class discovery vs repository loading
- when packaging happens
- whether the command path changes

Key observation:

- the command path stays the same
- the loading source changes

### 3. Compare Explicit Startup Shapes

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

### 4. Compare What Changes And What Stays Stable

What should remain stable:

- Component identity
- Service identity
- Operation identity
- selector semantics

What may change:

- startup mode
- repository source
- runtime role

### 5. Continue To The Next Labs

After understanding `command` / `server` / `client`, continue to:

- `../01.b-builtin-and-help-lab/README.md`
- `../01.c-component-script/README.md`

Those labs explain builtin/help surfaces and script-style operational usage.

## Example Commands

```bash
cd ../01-minimal
./run.sh
./invoke.sh
```

## Key Learnings

- selector format
- development-time vs deployment-style invocation
- class discovery vs repository loading
- command vs server vs client startup comparison
- stable selector semantics across execution modes
