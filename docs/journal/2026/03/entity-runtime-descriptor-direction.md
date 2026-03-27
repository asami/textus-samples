# Entity Runtime Descriptor Direction

## Status

Design note.

## Problem

`EntityRuntimePlanProvider` is currently used as a transitional way to supply
entity runtime metadata from Scala code.

This works as an escape hatch, but it is not the right long-term contract for
model-driven components.

Current symptoms:

- generated CRUD components compile, but runtime bootstrap initially needed
  extra wiring
- sample-specific wrapper factories were temporarily introduced in places like
  `textus-user-account` and `02.a-crud-seed-import-lab`
- CNCF bootstrap becomes tempted to rely on reflection to recover static
  metadata from generated classes

The reflection route is a bad sign. The missing part is not "more discovery",
but a proper static metadata contract.

## Direction

Static entity runtime definition should be passed through a descriptor, not
through program logic.

This means:

- static metadata belongs to descriptor files or generated descriptor objects
- action logic stays in generated Scala programs
- CNCF bootstrap reads descriptors and constructs runtime collections from them

## Descriptor Delivery

The default assumption is that the descriptor is packaged with the component
artifact.

Primary form:

- descriptor is embedded in CAR

Additional forms should also be supported:

- descriptor path or location explicitly given as a CNCF runtime parameter
- descriptor loaded from local development or test deployment locations

For example:

- CAR embedded descriptor for normal deployment
- command-line descriptor override for debugging or controlled execution
- local test/development directories that mirror CAR layout

The preferred local override form is:

- `car.d/`

This directory should use CAR-style placement, so descriptor and related
artifact-side resources can be resolved using the same structural assumption as
real CAR packaging.

This is preferable to introducing a completely separate ad hoc directory such as
`entity-runtime.d`.

This keeps the descriptor model deployment-oriented, while still allowing
development and test overrides.

## Descriptor Resolution Priority

Descriptor resolution should be explicit and ordered.

A reasonable priority is:

1. runtime parameter override
2. local development / test deployment location such as `car.d`
3. descriptor embedded in CAR

This means:

- production can rely on the artifact-default descriptor
- tests can inject temporary descriptor state through `car.d` without
  rebuilding CAR
- debugging can point CNCF at a specific descriptor source directly

The exact source names can evolve, but the priority model should be stable.

## What Should Move To Descriptors

The following are static definitions and should not depend on runtime
reflection or hand-written wrapper factories:

- entity name
- collection id
- persistent type binding
- memory policy
- working-set definition
- partition strategy
- aggregate-to-entity linkage
- view-to-entity linkage

These are the entity-side equivalent of definitions already exposed by
generated components such as:

- `aggregateDefinitions`
- `viewDefinitions`
- operation definitions

## Current Transitional State

Today CNCF has:

- `AggregateDefinition`
- `ViewDefinition`
- `EntityDescriptor`
- `EntityRuntimePlanProvider`

The gap is that entity bootstrap still depends on a program-side provider
(`EntityRuntimePlanProvider`) or on weak runtime recovery.

That is acceptable only as a transition.

## Preferred End State

The descriptor should be treated as a deployable artifact, not as Scala
program metadata.

That means:

- `ComponentDescriptor` is packaged in CAR
- generated action/program code stays separate
- CNCF bootstrap resolves `ComponentDescriptor` first, then wires runtime

This is stronger than exposing extra descriptor methods on generated
`Component` classes because it keeps static deployment metadata outside the
program layer.

The local directory override should therefore mirror CAR layout, with `car.d`
as the default development/test location.

### Canonical Local Layout

The preferred local override path is:

- `car.d/meta/component-descriptor.yaml`

The target is a single component descriptor file in CAR-style `meta/`.

## Recommendation

Prefer the descriptor artifact route.

Reason:

- descriptor metadata belongs to deployment artifacts
- it avoids runtime reflection
- it removes pressure to extend generated Scala programs with static wiring
- it matches the user-facing rule that `ComponentDescriptor` is stored in CAR

`EntityRuntimePlanProvider` should then be removed rather than preserved as a
long-lived compatibility hook.

## Implication For Current Samples

Current target state:

- `02.a-crud-seed-import-lab` works without a local wrapper factory
- `textus-user-account` works without a local wrapper factory
- CNCF bootstrap consumes descriptor-defined entity runtime metadata directly
- sample and test environments can override descriptor resolution through local
  CAR-style deployment directories when needed

## Next CNCF Work

1. Define `ComponentDescriptor` as the canonical static descriptor model.
2. Resolve it from CAR, runtime parameter override, or local `car.d/meta/`.
3. Change CNCF bootstrap to consume descriptor-defined entity runtime metadata.
4. Remove dependence on `EntityRuntimePlanProvider` in generated applications.
5. Remove reflection-based fallback paths instead of preserving them for compatibility.
