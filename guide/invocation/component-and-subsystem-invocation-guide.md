# Component and Subsystem Invocation Guide

## Purpose

This note explains how samples should be started in two situations:

- during development
- after deployment or packaging

It is intended as shared guidance for `01-minimal` and later samples.

## Core Idea

In this repository, the primary executable entry point should be the CNCF library main:

- `org.goldenport.cncf.CncfMain`

The sample itself should mainly contribute:

- `component.d` or `repository.d`
- implementation classes
- command path definitions such as `component.service.operation`

This means the default runtime model is:

1. start the CNCF main
2. load the sample-local component definitions
3. invoke a command path

Sample-local main classes are allowed, but only when the CNCF main cannot express the required behavior cleanly.

## Invocation Stages

The project should distinguish invocation by stage, not only by command form.

Recommended stages:

1. programming-time execution
2. local verification execution
3. local deployment execution
4. remote repository deployment execution

### 1. Programming-Time Execution

This is the way implementers verify the sample while writing code.

Typical goals:

- confirm that the sample builds
- confirm that the intended local source is activated correctly
- confirm that the intended command path executes
- reproduce behavior quickly from the sample directory

In this mode, the sample should normally be run through `sbt`.

This is the preferred style while programming because:

- source changes are reflected immediately
- classpath and build settings stay aligned with the sample
- CNCF main invocation is tested in the same build context as the code under development

So the programming-time default is:

- run from the sample directory
- use:
  - `cwd/component.d` for active packaged artifacts when needed
  - `cwd/repository.d` for searchable packaged artifacts when needed
- execute through `sbt runMain`

Conceptually:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command <component>.<service>.<operation>"
```

At this stage, the preferred source composition is:

- the component under active development from compiled classes via `--discover=classes`
- active packaged dependencies from `cwd/component.d`
- searchable packaged dependencies from `cwd/repository.d`
- optional already-published dependencies from configured repositories

### 2. Local Verification Execution

This is still a development-oriented mode, but it is slightly more structured than ad hoc programming-time runs.

Typical goals:

- verify a documented command path
- confirm expected output
- reproduce behavior through repository-standard scripts

Typical entry points:

- `./run-sample.sh 01-minimal`
- `cd samples/01-minimal && ./run.sh`

This stage should still prefer `sbt` under the hood.

For this repository, the shell entry points for development-oriented verification are:

- `run-sample.sh`
- `samples/<sample>/run.sh`

These are convenience wrappers around the same underlying CNCF main invocation.

### 3. Local Deployment Execution

This mode simulates deployed usage without relying on a remote repository.

Typical goals:

- verify packaging assumptions locally
- test with a local repository path
- run with a system-managed shared component directory

This is useful when:

- remote distribution is not ready yet
- the environment is isolated
- the operator intentionally wants local artifact control

In this repository, `invoke.sh` should use one of these local packaged-source shapes:

```text
samples/component.d
samples/repository.d
```

Use:

- `component.d`
  - when the sample wants an active packaged source
- `repository.d`
  - when the sample wants search plus explicit component/subsystem selection

This preserves the deployment-style contract even before real remote repository deployment is in place.

### 4. Remote Repository Deployment Execution

This is the preferred final deployment mode.

Typical goals:

- run using published artifacts
- resolve components or subsystems from a remote Component Repository
- exercise the final operational model

## Repository-Based Deployment Model

The default final deployment model is repository-based.

Component and Subsystem artifacts are expected to be stored in a remote Component Repository and loaded from there at runtime.

The reference example for the official Textus side is:

- `simplemodeling.org/car`

So the long-term expected production-style flow is:

1. build and package the Component or Subsystem artifact
2. publish it to a remote Component Repository
3. configure the runtime to load from that repository
4. invoke the exposed command path through the CNCF runtime

This is the preferred default for final deployment because it keeps:

- artifact distribution explicit
- runtime composition centralized
- deployment behavior closer to the intended CNCF operating model

## Local Deployment Option

Remote repositories are the default, but local deployment must also remain available.

Some environments need local control, for example:

- local development machines
- isolated test environments
- systems that manage approved components inside a local shared directory

In those cases, local packaged sources are a valid alternative, such as:

- a project-local `repository.d`
- a project-local `component.d`
- a system-wide shared component directory
- another locally managed packaged search or active path configured for CNCF
- the sample packaged-source directories under `samples/component.d` or `samples/repository.d`

So the project should explain two deployment options:

- default: remote packaged search and activation
- alternative: local packaged directories or shared active directories

For sample documentation and scripts, the immediate local stand-in is:

- `samples/component.d` or `samples/repository.d`

## Development-Time vs Deployment-Time Loading

The main difference is where the runtime resolves Component and Subsystem artifacts.

During development:

- the sample under active work should be loaded from compiled classes
- `--discover=classes` should be enabled
- `cwd/component.d` should hold active packaged dependencies when needed
- `cwd/repository.d` should hold searchable packaged dependencies when needed
- the recommended working directory is the sample directory itself
- `CncfMain` runs against the sample workspace

In practice, the preferred development-time pattern is:

1. `cd` into the sample directory
2. compile and run through `sbt`
3. let the actively developed component load from compiled classes
4. keep active packaged dependencies under `cwd/component.d` when needed
5. keep searchable packaged dependencies under `cwd/repository.d` when needed
6. invoke `CncfMain` from that working directory

And the preferred command style during programming is:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command <component>.<service>.<operation>"
```

After deployment:

- the runtime should normally resolve the artifact from a remote Component Repository
- local repository loading is still allowed when the operational environment prefers it

This means the command surface may stay the same while the artifact source changes.

Example:

- development-time command: `minimal.main.hello`
- deployment-time command: still `minimal.main.hello`
- difference: artifact location and repository resolution

## Component vs Subsystem

### Component-Level View

A Component is the unit that owns services and operations.

At invocation time, the caller typically sees a command path such as:

```text
component.service.operation
```

For `01-minimal`, the intended example is:

```text
minimal.main.hello
```

From the caller's perspective, this is the most visible execution target.

### Subsystem-Level View

A Subsystem is the composition boundary that groups one or more components.

At invocation time, the caller may still execute a component command path, but the runtime behavior is shaped by how the subsystem assembles:

- components
- repositories
- shared execution context
- wiring relationships

So the practical distinction is:

- Component view: which command path is called
- Subsystem view: how that command becomes executable in a composed runtime

This becomes especially important in later samples:

- `09-subsystem`
- `10-subsystem-wiring`
- `101-distributed`

In deployed environments, the repository model applies to both:

- Component artifacts
- Subsystem artifacts

The runtime may fetch either from a remote repository, or from a locally managed repository when that mode is explicitly selected.

## Recommended Default Pattern

For most samples, use this pattern:

1. Keep the actively developed implementation in compiled classes, and place packaged dependencies in `component.d` only when needed.
2. Use `org.goldenport.cncf.CncfMain` as the entry point.
3. Call the target command path.
4. Keep shell scripts thin.

The sample shell script should describe the sample-specific part only:

- sample directory
- command path
- exceptional main class only if truly needed

## Why CNCF Main Comes First

Using the CNCF main as the default has several advantages:

- all samples share the same execution model
- framework limitations become visible early
- shell scripts stay small and comparable
- the project tests the actual CNCF runtime contract, not a sample-specific shortcut

If a sample fails to fit this model, the first question should be:

- should CNCF be extended?

Only after that should the project consider adding a sample-local main.

## Exceptional Pattern: Sample-Local Main

Use a sample-local main only when there is a concrete reason, such as:

- the sample needs a temporary framework workaround
- the sample is intentionally demonstrating a custom integration point
- the required runtime behavior is not yet expressible through `CncfMain`

When this happens:

- the reason must be documented
- the exception should appear in the sample README
- the related checklist or journal entry should mention the framework gap

## Recommended Shell Roles

Each sample may define these entry points:

- `run.sh`
  - development-time verification shortcut
- `invoke.sh`
  - deployment-style invocation

Repository-shared logic should stay in:

- `scripts/cncf-common.sh`
- `scripts/cncf-run-main.sh`
- `scripts/sample-runner.sh`

This keeps sample scripts declarative and short.

The role split should be understood together with the stage split:

- learning walkthrough: type the explicit `cncf ...` / `sbt ...` commands from
  the sample README and read each parameter
- programming-time execution: direct `cncf dev ...` or `sbt runMain ...` from
  the sample directory
- local verification execution: `run.sh` or `run-sample.sh` after the manual
  command sequence is understood
- local deployment execution: `invoke.sh` with local repository assumptions
- remote repository deployment execution: deployed runtime calling the same logical command path through published artifacts

## Example: 01-minimal

For `01-minimal`, the desired model is:

- component: `minimal`
- service: `main`
- operation: `hello`
- command path: `minimal.main.hello`
- runtime entry point: `org.goldenport.cncf.CncfMain`

Shortcut verification expectation:

```bash
./run-sample.sh 01-minimal
```

or:

```bash
cd samples/01-minimal
./run.sh
```

These shortcut forms are useful for regression checks, but they are not the
primary teaching form. For learning, type the underlying command directly so the
launcher mode, project argument, and selector are visible:

Manual learning expectation:

```bash
cd samples/01-minimal
cncf dev command --project-dev . minimal.main.hello
```

Lower-level programming-time expectation:

```bash
cd samples/01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"
```

Deployment-time expectation:

```bash
cd samples/01-minimal
./invoke.sh
```

In a final deployment model, the same logical command should preferably be backed by:

- a remote repository artifact by default
- a local repository artifact when remote distribution is not desired

Even if scripts route through the same underlying CNCF main, they represent different responsibilities:

- explicit command lines are for learning and documentation
- `run.sh` is for implementer verification after the command line is understood
- `invoke.sh` is for the intended calling contract

## Practical Rule For Implementers

When implementing a sample, decide in this order:

1. What is the command path?
2. Can `CncfMain` run it directly?
3. Can the sample remain only a component/subsystem definition plus code?
4. Is a framework extension needed?
5. Only if necessary, should a sample-local main be introduced.

## Summary

The repository should explain invocation through a stable layered model:

- programming-time execution through `sbt`
- programming-time execution through `sbt` with class discovery for the active component
- development-time invocation for implementers
- deployment-time invocation for users and callers
- CNCF main as the default runtime entry point
- component command path as the visible execution surface
- subsystem composition as the hidden assembly model behind that surface
- remote Component Repository as the default final deployment source
- local repository or shared component directory as an alternative deployment option
