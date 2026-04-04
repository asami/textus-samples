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

- [ ] list what remains in the sample
- [ ] list what moves to `cozy` scripted
- [ ] confirm the split before editing

### B. Sample redesign

- [ ] remove the Scala demo main as the primary user path
- [ ] define the shell-first execution sequence
- [ ] decide whether `command` is enough or `server/client` is needed
- [ ] keep Scala code to the minimum practical level

### C. Sample implementation

- [ ] update CML if needed
- [ ] add or reduce Scala code to the minimum needed for the sample purpose
- [ ] rewrite `run.sh` as a batch wrapper for explicit shell commands

### D. Sample documentation

- [ ] README explains the user-facing purpose
- [ ] README shows the exact shell commands
- [ ] README explains expected output
- [ ] README makes it clear that `run.sh` is convenience only
- [ ] journal is updated

### E. Scripted relocation

- [ ] create a new `cozy` scripted case for `08.c` cache assertions
- [ ] move chunk-cache checks there
- [ ] move small-result-cache checks there
- [ ] move metrics checks there

### F. Verification

- [ ] sample commands run successfully
- [ ] sample `run.sh` runs successfully
- [ ] scripted test runs successfully

### G. Close

- [ ] sample remains user-facing
- [ ] internal runtime checks no longer live in the sample
- [ ] related documents updated
- [ ] commit completed

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
  - move cache-proof logic there
  - include metrics assertions there

## Next confirmation point

Before implementation, confirm this split:

1. sample keeps only user-facing shell usage
2. scripted takes over cache-proof assertions
