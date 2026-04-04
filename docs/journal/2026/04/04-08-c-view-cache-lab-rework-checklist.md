# 08.c-view-cache-lab rework checklist

## Goal

Rework `08.c-view-cache-lab` so that:

- the sample is user-facing and shell-first
- internal cache assertions move out of the sample
- runtime behavior checks move to `cozy` scripted

## Current Problem

The current `08.c` line is technically useful as a cache behavior demo, but it is not appropriate as a user-facing sample because:

- `run.sh` relies on a Scala demo main class
- the Scala demo directly uses framework primitives
- the sample explains runtime internals more than user operation flow

## Target Shape

### Sample side

The sample should show:

- a view-oriented UI-list use case
- CML that defines the entity, view, and query
- minimal Scala only when needed
- shell commands that a user can run directly
- `run.sh` only as a batch wrapper around the documented shell steps

The preferred execution shape is:

- `command`, if state observation is not essential
- `server` and `client`, if state observation across requests is essential

### Scripted side

The following should move to `cozy` scripted:

- chunk cache reuse assertions
- small-result cache reuse assertions
- metrics hit/miss assertions
- runtime primitive tests that directly instantiate helper classes

## Checklist

### A. Scope split

- [x] list what remains in the sample
- [x] list what moves to `cozy` scripted
- [x] confirm the split before editing

### B. Sample redesign

- [x] remove the Scala demo main as the primary user path
- [x] define the shell-first execution sequence
- [x] decide whether `command` is enough or `server/client` is needed
- [x] keep Scala code to the minimum practical level

### C. Sample implementation

- [x] update CML if needed
- [x] add or reduce Scala code to the minimum needed for the sample purpose
- [x] rewrite `run.sh` as a batch wrapper for explicit shell commands

### D. Sample documentation

- [x] README explains the user-facing purpose
- [x] README shows the exact shell commands
- [x] README explains expected output
- [x] README makes it clear that `run.sh` is convenience only
- [x] journal is updated

### E. Scripted relocation

- [x] create a new `cozy` scripted case for `08.c` cache assertions
- [x] move chunk-cache checks there
- [x] move small-result-cache checks there
- [x] move metrics checks there

### F. Verification

- [x] sample commands run successfully
- [x] sample `run.sh` runs successfully
- [ ] fresh `scripted` test runs successfully
- [x] scripted proof steps run successfully by manual two-step verification

### G. Close

- [x] sample remains user-facing
- [x] internal runtime checks no longer live in the sample
- [x] related documents updated
- [x] commit completed

## Current Status

### Sample side

- [x] shell-first sample path completed
- [x] `bin/cncf` command flow documented
- [x] README synchronized with actual output
- [x] internal demo Scala removed from the sample

### Scripted side

- [x] `cozy` scripted proof fixture created
- [x] chunk reuse proof moved to scripted
- [x] small-result cache proof moved to scripted
- [x] metrics proof moved to scripted
- [ ] fresh `scripted cozy/view-cache-metrics` pass still pending

## Proposed First Split

### Keep in sample

- the CML model
- the idea that view search is the main UI list path
- shell commands that invoke the sample through CNCF
- a simple observable result from those commands
- ordinary `command`-based repeated page access

### Move to scripted

- direct `ViewCollection` demo code
- backend query count assertions
- cache hit/miss metrics assertions
- chunk reuse proof
- small-result cache proof

## Concrete file mapping

### Remove or rewrite on sample side

- [run.sh](/Users/asami/src/dev2026/cncf-samples/samples/08.c-view-cache-lab/run.sh)
  - rewrite as shell-first command sequence
- [ViewCacheDemo.scala](/Users/asami/src/dev2026/cncf-samples/samples/08.c-view-cache-lab/src/main/scala/org/sample/viewcache/ViewCacheDemo.scala)
  - remove from sample path
- [README.md](/Users/asami/src/dev2026/cncf-samples/samples/08.c-view-cache-lab/README.md)
  - rewrite around user-facing shell commands

### Add on scripted side

- new `cozy` scripted case under `src/sbt-test/cozy/...`
  - [x] move cache-proof logic there
  - [x] include metrics assertions there

## Current blocker

- `check-view-cache.sh` now passes with `VIEW_CACHE_OK`
- a fresh `sbt --batch scripted cozy/view-cache-metrics` confirmation is still pending because scripted filtering is broader than expected in the current cozy setup
