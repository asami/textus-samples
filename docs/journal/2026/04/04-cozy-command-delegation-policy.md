# Cozy Command Delegation Policy

- date: 2026-04-04
- status: active

## Purpose

Fix the `sbt-cozy` and `cozy` invocation path for `cncf-samples` so that:

- version selection is controlled from one place
- sample builds do not hard-code a developer-specific `cozy` source path
- local development remains fast
- the invocation shape can later move to packaged runtime or Docker without changing each sample again

## Fixed Current Shape

The current repository-wide rule is:

1. `sbt-cozy` plugin version is read from:
   - [sbt-cozy-version.conf](/Users/asami/src/dev2026/cncf-samples/versions/sbt-cozy-version.conf)
2. `cozy` command target version is read from:
   - [cozy-version.conf](/Users/asami/src/dev2026/cncf-samples/versions/cozy-version.conf)
3. each sample uses:
   - `cozyDelegateProjectDir := None`
   - `cozyDelegateCommand := Seq(<repo>/bin/cozy)`
4. [bin/cozy](/Users/asami/src/dev2026/cncf-samples/bin/cozy) resolves the actual execution target
5. `sbt-cozy` delegated generation executes the command directly when `cozyDelegateProjectDir` is not set

## Why This Shape Is Fixed For Now

This is not yet the final end-user distribution shape.
It is the current development-stage shape.

The reason for fixing it now is:

- sample build files no longer embed a direct `/Users/.../cozy` path
- `sbt-cozy` version drift across samples is removed
- `cozy` version drift across samples is removed
- developers can still edit `cozy` locally and immediately re-run samples
- released `sbt-cozy` can be resolved as a normal published plugin
- development-time plugin resolution can override that through Ivy local publish

In other words:

- the source of truth for versions is centralized
- the execution boundary is now a command
- the implementation behind that command is still optimized for development efficiency

## Current `bin/cozy` Resolution

`bin/cozy` currently chooses a local `cozy` workspace in this order:

1. `COZY_PROJECT_DIR`
2. `~/src/dev2025/cozy`
3. `~/src/dev2026/cozy`

The selected workspace must satisfy:

- it exists
- it has a `build.sbt`
- its `version := ...` matches [cozy-version.conf](/Users/asami/src/dev2026/cncf-samples/versions/cozy-version.conf)

Once matched, the script runs:

- `sbt --batch "runMain cozy.Cozy ..."`

inside that workspace.

## Intended Future Stages

The intended progression is:

1. current stage
   - command boundary exists
   - local workspace execution is allowed
2. next stage
   - command boundary remains
   - packaged local runtime or published artifact becomes the default implementation
   - workspace fallback is development-only
3. final stage
   - command boundary remains
   - `bin/cozy` becomes a Docker-backed launcher

The key rule is:

- samples should continue to call a command
- they should not need to know whether the implementation is local workspace, packaged runtime, or Docker

## Operational Consequence

For current development:

- changing `cozy` source code can still be reflected quickly
- sample-side `plugins.sbt` and `build.sbt` stay stable
- `sbt-cozy` development can use `publishLocal` and `Resolver.defaultLocal`

For normal consumption:

- released `sbt-cozy` versions should come from Maven/sbt plugin repositories
- sample users do not need a local `sbt-cozy` workspace

For future distribution:

- the repository already has the correct abstraction boundary
- replacing the implementation behind `bin/cozy` will not require sample-wide rewrite
