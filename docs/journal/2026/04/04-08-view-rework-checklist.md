# 08-view rework checklist

## Goal

Rework `08-view` so that:

- it serves as the first user-facing explanation of view as the CQRS read side
- the sample is shell-first
- the sample shows CNCF usage directly, without internal framework-centric explanation

## Checklist

### A. Positioning

- [x] explain view as the read model corresponding to a CQRS query
- [x] explain UI-facing read access as the main intended use case
- [x] explain why this is view-oriented rather than aggregate-oriented

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

### D. Verification

- [x] help command runs successfully
- [x] load command runs successfully
- [x] search command runs successfully
- [x] `run.sh` runs successfully

## Completion condition

- [x] `08-view` can be used directly by sample users as the first view/read-model reference
- [x] the shell command flow is explicit and reproducible
- [x] later view samples can refer back to this sample for the base concept

## Current Status

- [x] sample-side rework completed
- [x] README synchronized with parameter and return-shape explanation
- [x] shell-first command flow committed
