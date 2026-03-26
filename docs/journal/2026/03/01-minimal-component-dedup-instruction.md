# 01-minimal Component Dedup Instruction

Status: `Superseded`

This document records the duplicate-component resolution work for `01-minimal`.
That subtask has already been executed and is no longer the active work-order document.

Use these instead:

- [`docs/journal/2026/03/01-minimal-completion-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/01-minimal-completion-instruction.md) for the active instruction
- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md) for current status

## Purpose

Unblock `01-minimal` by fixing duplicate component resolution in the CNCF runtime.

The immediate issue is not sample structure anymore.
The remaining blocker is how the runtime merges components discovered from:

- compiled classes
- `component.d`
- component repositories

## Background

`01-minimal` uses this intended contract:

- Scala implementation class: `MinimalComponent`
- CNCF component name: `minimal`
- command path: `minimal.main.hello`

The current runtime behavior shows that class discovery and repository-style loading do not yet merge duplicate logical components correctly.

Observed behavior:

- `invoke.sh` can load the sample through the virtual repository
- `run.sh` still fails in development-time discovery mode
- the runtime reports `ambiguous selector 'minimal'`

The ambiguity indicates that more than one candidate component is being registered for the same logical command surface.

## Current Technical Reading

The current CNCF codebase appears to behave as follows:

- component instantiation has several `headOption` based first-win paths
- selector resolution does not first-win duplicates
- selector resolution returns `Ambiguous` when more than one exact component match remains

This means duplicate suppression is incomplete.
It exists in some lower-level loading paths, but not at the effective selector-facing integration boundary.

## Desired Runtime Policy

The runtime should accept multiple component sources at the same time.

This is required for the intended development model:

- actively developed component from compiled classes
- dependent unpublished components from `cwd/component.d`
- already packaged components from repositories

When multiple candidates represent the same logical component, the runtime should not fail immediately with selector ambiguity.

Instead, it should apply this policy:

1. Group candidates by logical component identity.
2. If there is only one candidate, use it.
3. If there are multiple candidates with version metadata, prefer the newest version.
4. If version comparison is not possible, prefer the first candidate in source priority order.
5. Emit a warning whenever multiple candidates were collapsed into one effective component.

## Expected Source Priority

Until a stronger policy is implemented, use this precedence:

1. class-discovered development component
2. local repository or `component.d` provided dependency component
3. packaged repository component

This reflects the intended programming-time workflow.

For archive-based artifacts such as CAR, version-aware selection should override simple first-win when multiple versions of the same logical component are present.

## Scope

In scope:

- duplicate logical component detection
- merge policy for class and repository discovered components
- warning emission for duplicate collapse
- version-aware winner selection when artifact metadata is available
- `01-minimal` verification after the merge policy is fixed

Out of scope:

- redesign of selector grammar
- redesign of Component / Service / Operation semantics
- expansion of `01-minimal` beyond one user-defined component
- distributed repository protocol design

## Candidate Change Areas

The most likely CNCF-side change points are:

- `cloud-native-component-framework/src/main/scala/org/goldenport/cncf/component/repository/ComponentProvider.scala`
- `cloud-native-component-framework/src/main/scala/org/goldenport/cncf/component/repository/ComponentRepository.scala`
- `cloud-native-component-framework/src/main/scala/org/goldenport/cncf/component/ComponentSpace.scala`
- `cloud-native-component-framework/src/main/scala/org/goldenport/cncf/subsystem/resolver/OperationResolver.scala`
- `cloud-native-component-framework/src/main/scala/org/goldenport/cncf/CncfMain.scala`

The preferred fix point is before selector resolution.
The resolver should ideally receive an already deduplicated effective component set.

## Work Order

1. Reproduce the current ambiguity with:

```bash
cd samples/01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello"
```

2. Identify where both `Minimal` and `minimal` are entering the effective subsystem.
3. Add deduplication at the component integration boundary.
4. Apply warning-on-collapse behavior.
5. If archive metadata is available, prefer the newest version when duplicates are versioned variants of the same component.
6. Re-run `01-minimal` in both modes:

```bash
cd samples/01-minimal
./run.sh
./invoke.sh
```

7. Only after both pass, update:

- `docs/phase/samples/01-minimal.md`
- `samples/01-minimal/README.md`
- related journal notes if the runtime policy changed materially

## Acceptance Criteria

The work is complete only when all of the following are true:

- `MinimalComponent` remains the Scala implementation class
- `minimal` remains the CNCF component name
- `minimal.main.hello` resolves without ambiguity
- `./run.sh` succeeds
- `./invoke.sh` succeeds
- duplicate logical components no longer cause selector ambiguity by default
- duplicate collapse emits an observable warning
- versioned archive duplicates prefer the newest version when version metadata is available

## Notes

If the runtime already contains partial first-win logic, do not duplicate it in another ad hoc place.
Unify the behavior so that:

- loading
- effective component set construction
- selector resolution

all follow the same duplicate policy.
