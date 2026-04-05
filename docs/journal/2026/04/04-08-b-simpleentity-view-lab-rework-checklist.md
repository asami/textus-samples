# 08.b-simpleentity-view-lab rework checklist

## Goal

Rework `08.b-simpleentity-view-lab` so that:

- it stays user-facing and shell-first
- it explains the `SimpleEntity` inheritance variant of the default `08-view` line
- it shows CNCF usage directly, without framework-internal explanation

## Checklist

### A. Positioning

- [x] explain how `08.b` extends `08-view`
- [x] explain that the sample keeps the default generated view line
- [x] explain that `SimpleEntity` provides inherited common fields
- [x] explain why this line is still view-oriented rather than aggregate-oriented

### B. Sample execution shape

- [x] keep the sample command-based
- [x] run from the sample directory
- [x] use `bin/cncf`
- [x] keep `run.sh` as a batch wrapper around explicit shell commands

### C. README

- [x] add intended use case
- [x] add prepare step for `bin/setup cozy`
- [x] add “run the whole scenario”
- [x] add shell command walkthrough
- [x] explain parameters and command meaning
- [x] show expected output
- [x] explain the difference from `08-view`

### D. Verification

- [x] help command runs successfully
- [x] load command runs successfully
- [x] search command runs successfully
- [x] metadata describe runs successfully
- [x] `run.sh` runs successfully

### E. Scripted relocation

- [x] add a `cozy` scripted fixture for the `SimpleEntity` view line
- [x] use direct `cozy.Cozy modeler-scala --save=out.d` in the fixture
- [x] compile the generated fixture successfully
- [x] assert help / load / search / metadata describe
- [x] assert inherited `SimpleEntity` fields are visible on the returned view rows

## Completion condition

- [x] `08.b` can be used directly by sample users as the `SimpleEntity` variant of the view line
- [x] the shell command flow is explicit and reproducible
- [x] the README shows inherited `SimpleEntity` fields through CNCF commands
- [x] the same verification line is covered by `cozy` scripted
