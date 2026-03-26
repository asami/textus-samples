# 01-minimal Implementation Record

Date: 2026-03-26

Status: `Implementation Record`

This document records implementation work only.
It is not the status authority for `01-minimal`.

Status authority:

- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md)

Active instruction:

- [`docs/journal/2026/03/01-minimal-completion-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/01-minimal-completion-instruction.md)

## Summary

`01-minimal` has been substantially implemented under the current CNCF execution model.
The sample resolves `minimal.main.hello` through the CNCF runtime artifact path, and this document records the implementation-side changes only.

## What Changed

- The sample component is implemented in [`samples/01-minimal/src/main/scala/minimal/MinimalComponent.scala`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/src/main/scala/minimal/MinimalComponent.scala).
- `object MinimalComponent` now acts as the component factory and exposes the CNCF component name `minimal` with the `main` service / `hello` operation definition.
- [`samples/01-minimal/run.sh`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/run.sh) uses `--discover-classes` for development-time loading.
- [`samples/01-minimal/invoke.sh`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/invoke.sh) packages the sample jar into `samples/component-repository.d/MinimalComponent.jar` and then loads it through `component-dir`.
- [`samples/01-minimal/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/README.md) was updated to match the actual execution model.

## Framework-Side Adjustments

The CNCF runtime was updated to support the sample:

- `CncfMain` now combines class discovery and repository discovery according to the active mode.
- `CncfMain` now routes discovered classes through `ComponentProvider`.
- `ComponentProvider` now prefers the companion factory before falling back to no-arg instantiation.

These changes were made so the sample can resolve the intended component/service/operation shape without reintroducing the old shim approach.

## Verification

Confirmed locally:

- `./invoke.sh` succeeds from `samples/01-minimal`
- the intended command path is `minimal.main.hello`
- the runtime-side duplicate logical component ambiguity was resolved

Not decided by this record:

- final `./run.sh` completion status
- final visible output contract for `01-minimal`
- final `DONE` judgment in the phase checklist

## Notes

- The sample dependency version is controlled by `versions/cncf-version.conf`, with `CNCF_VERSION` as an override.
- In this workspace, that version currently resolves to `0.3.14-SNAPSHOT`.
- For a release cut, update the shared version file to the CNCF release version.
- In this workspace, the artifact can be made available with `publishLocal` from the CNCF framework repo.
- The current implementation keeps the sample minimal: one component, one service, one operation.

## Scope Note

This document records implementation progress and framework-side changes.
Completion status must be judged by:

- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md)
- [`docs/journal/2026/03/01-minimal-completion-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/01-minimal-completion-instruction.md)

The CNCF-side ambiguity was resolved by collapsing duplicate logical components such as `Minimal` and `minimal` at the runtime component integration boundary, but that framework-side fix does not by itself mark the sample complete.
