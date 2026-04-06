# 02.b-simpleentity-crud-lab rework checklist

## Goal

Rework `02.b-simpleentity-crud-lab` so that:

- it stays user-facing and shell-first
- it explains the `SimpleEntity` variant of the base CRUD line
- it shows the generated CRUD surface directly through `bin/cncf`

## Checklist

### A. Positioning

- [x] explain how `02.b` extends `02-crud`
- [x] explain that the sample focuses on the `SimpleEntity` variant
- [x] explain that this sample stays on generated-surface inspection

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

- [x] component help runs successfully
- [x] service help runs successfully
- [x] operation help runs successfully
- [x] metadata describe runs successfully
- [x] `run.sh` runs successfully

### E. Scripted relocation

- [x] add a `cozy` scripted fixture for the `SimpleEntity` CRUD surface
- [x] use direct `cozy.Cozy modeler-scala --save=out.d` in the fixture
- [x] compile the generated fixture successfully
- [x] assert component / service / operation help
- [x] assert metadata describe

## Completion condition

- [x] `02.b` can be used directly as the `SimpleEntity` CRUD variant of `02-crud`
- [x] the shell command flow is explicit and reproducible
- [x] the same generated-surface verification line is covered by `cozy` scripted
