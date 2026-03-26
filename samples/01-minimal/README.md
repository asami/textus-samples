# 01-minimal

## Overview

This is the smallest executable CNCF sample.
It implements one Component, one user-defined Service, and one user-defined Operation.
It also acts as the first reference point for the CNCF command-path execution model.
The CNCF dependency version is controlled by `../../versions/cncf-version.conf`, with `CNCF_VERSION` as an override.

## Structure

- Component: `minimal`
- Service: `main`
- Operation: `hello`
- Selector: `minimal.main.hello`
The component implementation lives in `src/main/scala/minimal/minimal.scala`.

## Service Taxonomy

This sample defines one user-visible service:

- `main`

The wider CNCF runtime model may also expose framework-provided service surfaces such as:

- `meta`
- `system`

Those framework-visible services are part of the CNCF execution model, even though the minimal sample itself is centered on `minimal.main.hello`.

## How To Run

Development-time execution uses `run.sh`.
It is intended for programming-time work and local verification.
The active component is loaded from compiled classes, and repository components may still come from `cwd/component.d`.
The CNCF runtime itself comes from the published `goldenport-cncf` dependency resolved through sbt.

```bash
./run.sh
```

Deployment-style execution uses `invoke.sh`.
It is intended to simulate repository-based loading.
The script packages the sample jar into `samples/component-repository.d/minimal.jar` and then loads that repository.

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

## Key Learnings

- The smallest unit of Component / Service / Operation
- Selector format: `<component>.<service>.<operation>`
- Development-time class loading vs deployment-time repository loading
- The basic shape of an executable sample
