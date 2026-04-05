# 08.a-view-definition-lab rework checklist

## Goal

Rework `08.a-view-definition-lab` so that:

- it stays user-facing and shell-first
- it explains named view definition as the first extension after `08-view`
- it shows CNCF usage directly, without framework-internal explanation

## Checklist

### A. Positioning

- [x] explain how `08.a` extends `08-view`
- [x] explain named view aliases such as `summary` and `detail`
- [x] explain custom `VIEW > QUERY` aliases
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
- [x] summary load runs successfully
- [x] summary search runs successfully
- [x] custom query search runs successfully
- [x] detail load runs successfully
- [x] metadata describe runs successfully
- [x] `run.sh` runs successfully

## Completion condition

- [x] `08.a` can be used directly by sample users as the first named-view reference
- [x] the shell command flow is explicit and reproducible
- [x] the README shows named view aliases and custom query aliases through CNCF commands
