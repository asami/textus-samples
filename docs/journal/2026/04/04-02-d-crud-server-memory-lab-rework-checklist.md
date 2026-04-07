# 02.d-crud-server-memory-lab rework checklist

## Goal

Rework `04.d-crud-server-memory-lab` so that:

- it stays user-facing and shell-first
- it explains the server/client runtime shape as the central point
- it verifies normal job-based command completion against memory-backed server state

## Checklist

### A. Positioning

- [x] explain how `02.d` extends the earlier `02` CRUD line
- [x] explain that `02.d` is a runtime-shape sample rather than a persistence sample
- [x] explain the difference from `04.c-crud-sqlite-lab`

### B. Sample execution shape

- [x] run from the sample directory
- [x] use `bin/cncf`
- [x] keep the sample generated rather than hand-written
- [x] keep the server/client flow explicit
- [x] keep the create -> await -> load confirmation path explicit
- [x] keep `run.sh` as a batch wrapper around explicit shell commands

### C. README

- [x] add intended use case
- [x] add prepare step for `bin/setup cozy`
- [x] add “run the whole scenario”
- [x] add shell command walkthrough
- [x] explain parameters and command meaning
- [x] show expected output

### D. Verification

- [x] component help runs successfully
- [x] service help runs successfully
- [x] create help runs successfully
- [x] await-job-result help runs successfully
- [x] metadata describe runs successfully
- [x] server start runs successfully
- [x] client create returns a job id
- [x] await-job-result returns an entity id
- [x] later client load returns the created record
- [x] `run.sh` runs successfully

### E. Scripted relocation

- [x] add a `cozy` scripted fixture for the memory-backed server/client CRUD line
- [x] use direct `cozy.Cozy modeler-scala --save=out.d` in the fixture
- [x] compile the generated fixture successfully
- [x] assert job id return from create
- [x] assert await-job-result returns the created entity id
- [x] assert later client load sees the same server-held memory state
- [x] assert metadata describe

## Completion condition

- [x] `02.d` can be used directly as the memory-backed server/client CRUD sample
- [x] the shell command flow is explicit and reproducible
- [x] the same server/client verification line is covered by `cozy` scripted
