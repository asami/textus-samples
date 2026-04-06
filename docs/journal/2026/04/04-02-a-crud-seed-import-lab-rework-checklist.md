# 02.a-crud-seed-import-lab rework checklist

## Goal

Rework `02.a-crud-seed-import-lab` so that:

- it stays user-facing and shell-first
- it explains seed import as the first runtime CRUD extension after `02-crud`
- it verifies imported records through generated CNCF commands

## Checklist

### A. Positioning

- [x] explain how `02.a` extends `02-crud`
- [x] explain descriptor-first runtime metadata
- [x] explain seed import from `entity.d`
- [x] explain why this lab focuses on imported data verification

### B. Sample execution shape

- [x] run from the sample directory
- [x] use `bin/cncf`
- [x] keep the sample command-based
- [x] add `run.sh` as a batch wrapper around explicit shell commands

### C. README

- [x] add intended use case
- [x] add prepare step for `bin/setup cozy`
- [x] add “run the whole scenario”
- [x] add shell command walkthrough
- [x] explain parameters and command meaning
- [x] show expected output
- [x] explain the difference from `02-crud`

### D. Verification

- [x] load help runs successfully
- [x] search help runs successfully
- [x] seeded load runs successfully
- [x] seeded search runs successfully
- [x] metadata describe runs successfully
- [x] `run.sh` runs successfully

### E. Scripted relocation

- [x] add a `cozy` scripted fixture for seed import verification
- [x] use direct `cozy.Cozy modeler-scala --save=out.d` in the fixture
- [x] compile the generated fixture successfully
- [x] assert load help / search help
- [x] assert seeded load / seeded search
- [x] assert metadata describe

## Completion condition

- [x] `02.a` can be used directly as the first seed-import runtime CRUD sample
- [x] the shell command flow is explicit and reproducible
- [x] the same seed-import verification line is covered by `cozy` scripted
