# 01-minimal

## Overview

This is the first Textus operation sample.
It intentionally does not introduce CML yet.
The purpose of the `01` line is to learn how to operate Textus before learning
how to define a Component with CML.

This sample implements the smallest executable Textus Component shape:

- one Component
- one user-defined Service
- one user-defined Operation
- one command selector

The selector is:

```text
minimal.main.hello
```

Later samples introduce the normal CML authoring path.
This sample stays below that level so the runtime operation model is visible
without also teaching CML syntax.
The CNCF dependency version is controlled by `../../versions/cncf-version.conf`, with `CNCF_VERSION` as an override.

## Position In The 01 Line

`01` is the Textus operation chapter.
It is not the CML chapter.

- `01-minimal`
  - the smallest command selector execution
  - introduces Component / Service / Operation as runtime concepts
- `01.a-invocation-source-lab`
  - compares development-directory startup with component repository startup
  - keeps the selector stable while the Component loading source changes
- `01.b-startup-shapes-lab`
  - compares `command`, `server`, and `client`
  - keeps the same minimal Component while the runtime role changes
- `01.c-builtin-and-help-lab`
  - introduces `meta.help`, `help`, and builtin/admin surfaces
  - distinguishes sample-defined operations from runtime-provided operations
- `01.d-component-script`
  - shows the script DSL as a special operational form
  - remains outside the normal Component/CML authoring line

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
This is deliberate for the `01` line: the sample is explaining Textus operation
first, not Component authoring by CML.

## Execution Modes

Textus is not only about `command` execution.
The same internal model is expected to support multiple runtime modes, including:

- `command`
- `server`
- `client`
- `script`

This sample demonstrates the `command` entry point first.
The surrounding documents explain how the same model extends into the other modes.

## How To Run

Run the minimal command from this directory:

```bash
./run.sh
```

Use repository-style invocation only when you want to see the packaged loading
path:

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

## Startup Sources

The new `cncf` launcher line is expected to keep these roles visible:

### `run.sh`

`run.sh` is the development-directory startup.
The Component is loaded from the current sample project.

Current conceptual shape:

```text
cncf command minimal.main.hello
```

### `invoke.sh`

`invoke.sh` is the component repository startup.
It packages the sample and loads the Component from the repository-style
artifact directory instead of the development directory.

Current conceptual shape:

```text
sbt package
cncf command --no-project-classpath --component-dir ../component.d minimal.main.hello
```

The important point is not the script names.
The important point is the source of the active Component:

- development directory
- component repository

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
If you want to understand the surrounding execution and repository startup
model, continue with the documents below.

### Learn By Operating The Sample

- `../01.a-invocation-source-lab/README.md`
  - guided hands-on learning path based on `01-minimal`
  - explains development-directory startup vs component repository startup

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
  - component repository startup
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
2. Run the development-directory startup and the component repository startup
3. Read `../01.a-invocation-source-lab/README.md`
4. Read `../01.b-startup-shapes-lab/README.md`
5. Read `../01.c-builtin-and-help-lab/README.md`
6. Read `../../guide/invocation/component-and-subsystem-invocation-guide.md`
7. Read `../01.d-component-script/README.md`
8. Read `../../guide/script/component-script-examples.md`

## Key Learnings

- The smallest unit of Component / Service / Operation
- Selector format: `<component>.<service>.<operation>`
- Textus execution is broader than `command` alone
- Development-directory startup vs component repository startup
- Why `01` does not introduce CML yet
