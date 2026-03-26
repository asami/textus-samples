# 01-minimal Recovery Instruction

Status: `Superseded`

This document records an earlier recovery phase for `01-minimal`.
It is no longer the active work-order document.

Use these instead:

- [`docs/journal/2026/03/01-minimal-completion-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/01-minimal-completion-instruction.md) for the active instruction
- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md) for current status

## Purpose

Recover `01-minimal` to a truly completed state under the current execution model.

The sample currently has code, scripts, and documentation, but execution verification shows that the runtime still fails to resolve the `minimal` component.

This document exists to drive the remaining work to closure.

## Current Verified Situation

The following has already been confirmed:

- the sample compiles
- `samples/01-minimal/src/main/scala/minimal/MinimalComponent.scala` exists
- the command path is intended to be `minimal.main.hello`
- `run.sh` and `invoke.sh` are both defined

The following has also been confirmed:

- `./run.sh` completes the intended command successfully
- `./invoke.sh` completes the intended command successfully
- the observed CNCF output is:

```text
cncf-job-job-1774483259087-6xkA9C8kP5noN3uFTCY48z
```

## Problem Statement

`01-minimal` is no longer blocked by build failure.
The component discovery and loading issue has been resolved.

This means the remaining work is not "write more sample code" in the abstract.
The remaining work is to make the runtime resolve `minimal` correctly in both modes:

- development-time mode
- deployment-style mode

## Required Outcome

The sample must satisfy both of the following:

### Development-Time Outcome

From `samples/01-minimal`, the following must succeed:

```bash
./run.sh
```

Interpretation:

- use `sbt`
- use `org.goldenport.cncf.CncfMain`
- use class discovery for the actively developed component
- allow repository components from `cwd/component.d` if needed

### Deployment-Style Outcome

From `samples/01-minimal`, the following must succeed:

```bash
./invoke.sh
```

Interpretation:

- use `sbt`
- use `org.goldenport.cncf.CncfMain`
- do not rely on active class discovery
- load from the sample virtual repository under `samples/component-repository.d`

## Likely Cause Areas

Investigation should focus on these areas first:

1. class discovery contract
   - whether the current `MinimalComponent` / provider layout exposes the CNCF component name `minimal` in the way `CncfMain --discover=classes` expects
2. ServiceLoader registration
   - whether the provider file and implementation class align with CNCF discovery rules
3. package prefix / discovery scope
   - whether `MinimalComponent` classes are inside the discovery scope used by CNCF
4. virtual repository layout
   - whether `samples/component-repository.d` contains the structure needed for `component-dir` loading
5. repository artifact preparation
   - whether `invoke.sh` points to a repository path that is conceptually correct but operationally empty

## Work Order

Implementers should proceed in this order:

1. Fix `run.sh` first.
   - Make development-time class discovery resolve `minimal`.
2. Confirm direct command execution:
   - `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"`
3. Confirm the output is exactly:
   - `Hello CNCF`
4. Then fix `invoke.sh`.
   - Decide what must exist under `samples/component-repository.d`
   - Make repository-based resolution of `minimal` actually work
5. Update README to match the working reality.
6. Update the phase checklist only after both paths are verified.

## Constraints

Do not expand the sample scope while fixing it.

Keep all of the following true:

- one Component
- one user-defined Service
- one user-defined Operation
- no external system dependency
- stateless behavior
- deterministic behavior

Do not turn this recovery effort into:

- a subsystem sample
- a repository abstraction sample
- a multi-component sample
- a framework redesign beyond what is required for `01-minimal`

## Files Most Likely To Change

- `samples/01-minimal/src/main/scala/minimal/MinimalComponent.scala`
- `samples/01-minimal/component.d/...`
- `samples/01-minimal/run.sh`
- `samples/01-minimal/invoke.sh`
- `samples/01-minimal/README.md`
- `samples/component-repository.d/...`

If the problem belongs in the framework, a CNCF-side change is acceptable, but only if it is clearly necessary to make the documented execution model work.

## Verification Checklist

The recovery is complete only when all items below are true:

- [x] `./run.sh` succeeds
- [x] `./invoke.sh` succeeds
- [x] `minimal.main.hello` resolves to the intended component
- [x] The output is `Hello CNCF`
- [x] The sample still uses only one user-defined service and one user-defined operation
- [x] The implementation remains stateless
- [x] The implementation remains deterministic
- [x] The implementation remains independent from external systems
- [x] `samples/01-minimal/README.md` matches the working behavior
- [x] `docs/phase/samples/01-minimal.md` is updated to match the verified state

## Closing Rule

Do not mark `01-minimal` as done again until both execution paths are working:

- development-time path via `run.sh`
- deployment-style path via `invoke.sh`
