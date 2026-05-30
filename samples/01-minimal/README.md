# 01-minimal

## Overview

This is the smallest executable CNCF sample.
It implements one Component, one user-defined Service, and one user-defined Operation.
It also acts as the first reference point for the CNCF command-path execution model.
The CNCF dependency version is controlled by `../../versions/cncf-version.conf`, with `CNCF_VERSION` as an override.

This README is intentionally short.
Its job is to explain the minimal sample itself and point you to the next documents when you want to learn more than "run this and get Hello CNCF".

## Structure

- Subsystem
  - the composition boundary that hosts one or more Components
- Component: `minimal`
  - the executable unit that exposes Services
- Service: `main`
  - the unit that groups Operations
- Operation: `hello`
  - the executable behavior selected from the CLI or other runtime modes
- Selector: `minimal.main.hello`
The Scala implementation class lives in `src/main/scala/minimal/MinimalComponent.scala`.
The CNCF component name exposed to the runtime is `minimal`.

This README intentionally keeps those definitions short.
Use the linked lab and guide documents for the fuller model.

## Execution Modes

CNCF is not only about `command` execution.
The same internal model is expected to support multiple runtime modes, including:

- `command`
- `server`
- `client`
- `script`

This sample mainly demonstrates the `command` entry point first.
The surrounding documents explain how the same model extends into the other modes.

## How To Run

Development-time execution uses `run.sh`.
It is intended for programming-time work and local verification.
The active component is loaded from compiled classes, and repository components may still come from `cwd/component.d`.
The CNCF runtime itself comes from the published `goldenport-cncf` dependency resolved through sbt.

```bash
./run.sh
```

Deployment-style execution uses `invoke.sh`.
It is intended to simulate active packaged loading.
The script packages the sample jar into `samples/component.d/MinimalComponent.jar` and uses that directory as the active component source.

```bash
./invoke.sh
```

## Example Commands

```bash
./run.sh
./invoke.sh
```

Programming-time direct execution:

```bash
./run.sh
```

## What The Scripts Actually Do

The shell wrappers are intentionally thin, but that also makes their behavior easy to miss.

### `run.sh`

`run.sh` is the development-time entry point.
It calls the shared runner with:

- `--component-dev-dir .`
- `--command-path minimal.main.hello`

Conceptually, it becomes:

```bash
cncf dev command --project . --component-dev-dir . minimal.main.hello
```

Meaning:

- run the CNCF main directly
- discover the actively developed Component from compiled classes
- execute the selector `minimal.main.hello`

### `invoke.sh`

`invoke.sh` is the deployment-style entry point.
It first packages the sample jar, copies it into the virtual repository, and then calls the shared runner with:

- `--component-dir ../component.d`
- `--command-path minimal.main.hello`

Conceptually, it becomes:

```bash
sbt package
cncf dev command --project . --no-project-classpath --component-dir ../component.d minimal.main.hello
```

Meaning:

- build the sample artifact
- place that artifact into the sample active component directory
- run the CNCF main directly
- load the Component from the active packaged directory instead of from active class discovery

### Why The Two Scripts Differ

The point of the split is to make two different execution models visible:

- `run.sh`
  - development-time execution
  - class discovery for the active Component
- `invoke.sh`
  - deployment-style execution
  - repository-based loading

If you only read the script names, this difference is easy to miss.
This section exists to make the underlying `sbt` / CNCF main parameters explicit.

## Behavior

The command:

```text
Hello CNCF
```

## Constraints

The `hello` operation should remain:

- stateless
- deterministic
- independent from external systems

## Where To Go Next

If you only want the minimum executable sample, this README is enough.
If you want to understand the surrounding execution and deployment model, continue with the documents below.

### Learn By Operating The Sample

- `../01.a-invocation-source-lab/README.md`
  - guided hands-on learning path based on `01-minimal`
  - focuses on `run.sh` vs `invoke.sh`
  - explains development-time class loading vs deployment-style repository loading

- `../01.b-startup-shapes-lab/README.md`
  - compares `command`, `server`, and `client` startup styles

- `../01.c-builtin-and-help-lab/README.md`
  - explains builtin Components and help-oriented command surfaces

### Learn How Component Commands Become Small Scripts

- `../01.d-component-script/README.md`
  - management-command style usage based on Component operations

### Learn Invocation And Deployment Patterns

- `../../guide/invocation/component-and-subsystem-invocation-guide.md`
  - development-time execution
  - deployment-style invocation
  - repository-based loading model
  - subsystem / component / service / operation relationship
  - command / server / client execution framing

- `../../guide/script/component-script-examples.md`
  - examples of thin shell wrappers around Component command paths
  - script-style operational usage

### Learn The Current Engineering Status

- `../../docs/phase/samples/01-minimal.md`
  - current completion checklist

- `../../docs/journal/2026/03/01-minimal-development-instruction.md`
  - original development instruction

- `../../docs/journal/2026/03/01-minimal-recovery-instruction.md`
  - current recovery instruction for unresolved execution issues

## Reading Map

Use this order if you want a structured learning path:

1. Read this README
2. Run `./run.sh` and `./invoke.sh`
3. Read `../01.a-invocation-source-lab/README.md`
4. Read `../01.b-startup-shapes-lab/README.md`
5. Read `../01.c-builtin-and-help-lab/README.md`
6. Read `../../guide/invocation/component-and-subsystem-invocation-guide.md`
7. Read `../01.d-component-script/README.md`
8. Read `../../guide/script/component-script-examples.md`

## Key Learnings

- The smallest unit of Component / Service / Operation
- Selector format: `<component>.<service>.<operation>`
- CNCF execution is broader than `command` alone
- Development-time class loading vs deployment-time repository loading
- The basic shape of an executable sample
