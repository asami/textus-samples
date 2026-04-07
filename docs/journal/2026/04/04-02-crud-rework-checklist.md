# 02-crud rework checklist

## Goal

Rework `04-crud` so that:

- it serves as the first shell-first CRUD sample
- it explains the generated CRUD surface before seed import or storage-specific behavior
- it shows CNCF usage directly through `bin/cncf`

## Checklist

### A. Positioning

- [x] explain that `04-crud` is the base model-driven CRUD line
- [x] explain that this sample focuses on generated surface inspection
- [x] explain that later labs add seed data and runtime behavior

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

### D. Verification

- [x] component help runs successfully
- [x] service help runs successfully
- [x] operation help runs successfully
- [x] metadata describe runs successfully
- [x] `run.sh` runs successfully

### E. Scripted relocation

- [x] add a `cozy` scripted fixture for the generated CRUD surface
- [x] use direct `cozy.Cozy modeler-scala --save=out.d` in the fixture
- [x] compile the generated fixture successfully
- [x] assert component / service / operation help
- [x] assert metadata describe

## Completion condition

- [x] `04-crud` can be used directly as the first generated CRUD-surface sample
- [x] the shell command flow is explicit and reproducible
- [x] later `02.*` samples can refer back to this sample as the base line
- [x] the same generated-surface verification line is covered by `cozy` scripted
