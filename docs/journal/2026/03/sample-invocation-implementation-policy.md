# Sample Invocation Implementation Policy

## Context

This project needs two different execution viewpoints:

- local verification during development
- invocation style after deployment or packaging

And within development, it should distinguish at least:

- programming-time execution through `sbt`
- script-based verification through repository-standard wrappers

Those two viewpoints are related, but they are not the same concern.
The implementation should keep them aligned without duplicating shell logic per sample.

## Primary Policy

Use the CNCF library-side main class first.

Preferred pattern:

1. Invoke the CNCF library main.
2. Pass the sample-specific command path.
3. Let the sample provide `cwd/component.d` and implementation classes.

Fallback pattern:

1. Introduce a sample-local main class only when the CNCF library main is insufficient.
2. Keep that decision explicit and documented in the sample README and checklist.

In short:

- default: CNCF main
- exception: sample-local main

For final deployment, use a repository-based artifact loading model first.

In short:

- default deployment source: remote Component Repository
- alternative deployment source: local repository or shared component directory

## Why This Policy Exists

Using the CNCF library main as the default gives the project a cleaner structure:

- sample shell scripts stay small
- invocation style stays consistent across samples
- CLI behavior is tested against the same entry point that real users are expected to use
- framework gaps become visible early

If the sample cannot be expressed cleanly through the CNCF main, that is a valid signal.
In such cases, extending CNCF is preferable to introducing sample-local main classes too early.

The same preference applies to deployment:

- prefer the standard repository-based operating model
- allow local repository deployment where operationally required

## Shared Shell Structure

The repository should centralize invocation logic under `scripts/`.

Current shared utilities:

- `scripts/cncf-common.sh`
  - common path and validation helpers
- `scripts/cncf-run-main.sh`
  - runs `org.goldenport.cncf.CncfMain` by default via `sbt runMain`
- `scripts/sample-runner.sh`
  - resolves the calling sample directory and delegates to `cncf-run-main.sh`

These shared scripts are convenience wrappers.
They do not replace the primary programming-time execution model, which should remain `sbt`-based.

## Sample-Side Shell Policy

Each sample may expose two shell entry points:

- `run.sh`
  - local verification entry point
- `invoke.sh`
  - deployment-style invocation entry point

These scripts should remain thin wrappers.
They should declare only:

- which command path to call
- whether the sample uses the default CNCF main
- whether an exceptional sample-local main is required

They should not duplicate:

- repository path discovery
- `sbt runMain` assembly
- generic argument forwarding logic

They are primarily for:

- repeatable verification
- consistent local invocation
- user-facing examples

They are not a replacement for normal `sbt`-based programming-time execution.

## Default Implementation Pattern

The normal sample pattern is:

```bash
#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --command-path sample.service.operation \
  -- \
  "$@"
```

This means:

- use `org.goldenport.cncf.CncfMain`
- run from the sample directory
- treat `cwd/component.d` as the primary development-time component source
- rely on `component.d` and sample classes for discovery and execution

## Exceptional Pattern

Only when necessary, a sample may specify its own main class:

```bash
#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --sample-main-class com.example.SampleCommandMain \
  --command-path sample.service.operation \
  -- \
  "$@"
```

This pattern must be justified.

Acceptable reasons include:

- CNCF main cannot yet express the required runtime behavior
- the sample is explicitly exercising a framework extension point
- temporary framework limitations are being isolated while CNCF is extended

## Guidance For Implementers

When implementing a sample, follow this order:

1. During programming, run from the sample directory through `sbt`.
2. Use `cwd/component.d` as the primary development-time component source.
3. Try to make the sample work with `org.goldenport.cncf.CncfMain`.
4. Keep `run.sh` and `invoke.sh` as thin wrappers.
5. Put sample behavior into `component.d` and Scala implementation classes.
6. If the sample still cannot run cleanly, identify the CNCF gap.
7. Extend CNCF if the gap belongs in the framework.
8. Introduce a sample-local main only if the framework change is not yet available or the sample truly requires a custom entry point.

## Stage Model

Implementers should think in the following stages:

1. Programming-time execution
   - run from the sample directory
   - use `sbt runMain`
   - use `cwd/component.d`
2. Local verification
   - use `run.sh` or `run-sample.sh`
   - confirm documented command behavior
3. Local deployment
   - use `invoke.sh`
   - load from a local repository or shared component directory when needed
4. Final deployment
   - prefer remote Component Repository loading
   - keep the command path stable while changing artifact source

## Documentation Requirements

If a sample uses the default CNCF main:

- the README should say so plainly
- the shell script should not mention a sample-local main

If a sample uses a sample-local main:

- the README must explain why
- the phase checklist should mention the exception
- the journal should record the framework limitation or design reason

## Initial Project Direction

For the current repository phase:

- `01-minimal` should aim to run through `org.goldenport.cncf.CncfMain`
- later samples should keep the same default unless a concrete limitation appears
- framework extension on the CNCF side is an acceptable and preferred response when needed
- final deployment should assume remote repository loading by default
- local repository loading should remain a supported option

## Decision Summary

The project standard is:

- use CNCF main first
- centralize shell invocation logic
- keep sample scripts declarative
- extend CNCF when necessary
- treat sample-local main classes as exceptions, not defaults
- treat remote Component Repository loading as the default deployment model
- keep local repository deployment available as an alternative
