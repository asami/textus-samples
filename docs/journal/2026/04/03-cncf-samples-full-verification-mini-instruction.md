# cncf-samples Full Verification Mini Instruction

## Goal

Run the samples under `cncf-samples` and classify the current state of:

- samples that succeed
- samples that fail
- samples that are missing execution prerequisites

The purpose of this task is **current-state verification of the full sample set**, not implementation work.

## Scope

The target is the full sample set under:

- `/Users/asami/src/dev2026/cncf-samples/samples`

Priority targets:

- `01-*`
- `02-*`
- `03-*`
- `04-*`
- `05-*`
- `06-*`
- `07-*`
- `08-*`

If `09-*`, `10-*`, or `101-*` exist as real sample directories, include them as well.

## Basic Policy

- Do not make fixes
- Do not commit
- Do not edit files unless it is strictly necessary to record the result
- Check each sample README and `run.sh` first, and prefer the standard execution path
- If a sample has `run.sh`, use `run.sh` first
- If there is no `run.sh`, follow the README procedure
- If the README also lacks a usable procedure, record it as `execution path missing` rather than as a failure

## Execution Policy

- Keep one persistent `sbt` session per repo as the default rule
- Do not spawn unnecessary new `sbt` processes
- If `run.sh`, `run-*.sh`, or `invoke.sh` already exist, prefer them
- For long-running server/client samples, it is acceptable to stop after help output or the minimum successful check
- For job / event / distributed samples, verify up to the minimum success condition described in the README

## Per-sample Checklist

For each sample, verify the following:

1. sample path
2. sample kind
3. standard entrypoint
4. execution result
5. evidence
6. notes

### Sample kind

Classify each sample as one of:

- `run.sh`
- `run-*.sh`
- `invoke.sh`
- `README-only`
- `no-entrypoint`

### Standard entrypoint

Priority:

1. `run.sh`
2. `run-*.sh`
3. `invoke.sh`
4. README command

### Execution result

Record one of:

- `passed`
- `failed`
- `blocked`
- `execution path missing`

### Evidence

Leave at least one short piece of evidence.

Examples:

- help output was shown
- sample command exited with code 0
- expected output keyword appeared
- expected entity/view data was returned
- the main operation described in the README succeeded

### Notes

If needed, record:

- setup prerequisite
- timeout
- flaky
- outdated README
- runtime bug suspect
- generator/model mismatch suspect

## What To Run

Basic rule for each sample:

- if `run.sh` exists, run it first
- for heavier samples, read the README and script and choose the minimum success path
- for command-style samples with `help`, check help plus one primary command
- for CRUD samples, verify the minimum 1-2 commands from create/load/search described in the README
- for view samples, verify the primary 1-2 load/search commands
- for event/job samples, verify the minimum working path via demo/help/log

## Output Format

The final report should contain one summary line per sample and a short aggregate summary at the end.

### Per-sample line format

`<sample> | <result> | <entrypoint> | <short evidence> | <short note>`

Example:

`08.b-simpleentity-view-lab | passed | run.sh | load-person and search-person-record succeeded | none`

### Summary format

- total samples checked
- passed count
- failed count
- blocked count
- execution path missing count
- flaky samples
- top 3 issues worth fixing first

## Reporting Rules

- Do not dump long raw logs
- For failures, record only the first essential error
- Similar failures may be grouped together
- If you infer a cause, prefix it with `suspect:`
- If you add repair suggestions, keep them short at the end

## Stop Conditions

It is acceptable to stop a sample when any of the following happens:

- the main entrypoint clearly fails on the first run
- a server sample waits too long at startup
- an external dependency is missing
- the README and the actual sample contents diverge so much that the execution path cannot be determined

## Deliverable

The deliverable consists of:

1. a per-sample verification summary
2. a repository-level failure clustering summary

Do not perform code fixes as part of this task.
