# 01-minimal Implementation Record

Date: 2026-03-26

## Summary

`01-minimal` has been brought to a working state under the current CNCF execution model.
The sample now resolves `minimal.main.hello` through the CNCF runtime artifact and produces the expected `Hello CNCF` behavior.

## What Changed

- The sample component is implemented in [`samples/01-minimal/src/main/scala/minimal/minimal.scala`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/src/main/scala/minimal/minimal.scala).
- `object minimal` now acts as the component factory and carries the `main` service / `hello` operation definition.
- [`samples/01-minimal/run.sh`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/run.sh) uses `--discover-classes` for development-time loading.
- [`samples/01-minimal/invoke.sh`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/invoke.sh) packages the sample jar into `samples/component-repository.d/minimal.jar` and then loads it through `component-dir`.
- [`samples/01-minimal/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/01-minimal/README.md) was updated to match the actual execution model.

## Framework-Side Adjustments

The CNCF runtime was updated to support the sample:

- `CncfMain` now combines class discovery and repository discovery according to the active mode.
- `CncfMain` now routes discovered classes through `ComponentProvider`.
- `ComponentProvider` now prefers the companion factory before falling back to no-arg instantiation.

These changes were made so the sample can resolve the intended component/service/operation shape without reintroducing the old shim approach.

## Verification

Confirmed locally:

- `./run.sh` succeeds from `samples/01-minimal`
- `./invoke.sh` succeeds from `samples/01-minimal`
- the intended command path is `minimal.main.hello`
- the sample behavior remains `Hello CNCF`

## Notes

- The sample dependency version is controlled by `versions/cncf-version.conf`, with `CNCF_VERSION` as an override.
- In this workspace, that version currently resolves to `0.3.14-SNAPSHOT`.
- For a release cut, update the shared version file to the CNCF release version.
- In this workspace, the artifact can be made available with `publishLocal` from the CNCF framework repo.
- The current implementation keeps the sample minimal: one component, one service, one operation.
