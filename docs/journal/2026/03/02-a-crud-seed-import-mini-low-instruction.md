# 02.a CRUD Seed Import Mini-Low Instruction

Status: `Active Instruction`

Reviewed and updated on 2026-03-27.

## Purpose

Implement `02.a-crud-seed-import-lab` using the new CNCF test-data placement feature.

The important change is:

- CNCF can now initialize test data from `entity.d` and `data.d`

This lab must use that capability directly.

## Repository Context

- samples repo:
  - `/Users/asami/src/dev2026/cncf-samples`
- CNCF design note:
  - `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/test-data-import-design-2026-03-27.md`

## Read First

- `/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/02.a-crud-seed-import-lab.md`
- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/test-data-import-design-2026-03-27.md`

## Goal

Make `02.a-crud-seed-import-lab` demonstrate this flow:

1. define the CRUD model in Cozy/CML
2. place test data in `entity.d` and/or `data.d`
3. let CNCF initialize the test data from that placement
4. verify `load` and `search`
5. obtain and inspect the resulting data through runtime commands

This lab should be the first practical post-`02-crud` lab.

## Required Lab Shape

The sample should end up with a structure like this:

- `README.md`
- `build.sbt`
- `project/build.properties`
- `project/plugins.sbt`
- `src/main/cozy/*.cml`
- `entity.d/` and/or `data.d/`

Use whichever of `entity.d` or `data.d` is appropriate for the current CNCF feature.

If both are needed for a minimal example, use both.

## Main Task

### Part 1. Model

Keep the sample model-driven.

- use Cozy/CML
- do not add hand-written CRUD repository logic
- keep it aligned with `02-crud`

### Part 2. Seed Placement

Add initial test data in the new placement form:

- `entity.d`
- `data.d`

The lab must rely on CNCF's initialization behavior from those directories.

Do not introduce a different custom seed loader in the sample.

### Part 3. Runtime Verification

Verify through `CncfMain --discover=classes`.

Minimum expected runtime confirmations:

- component help
- service help for the relevant load/search service
- operation help for a `load` target
- operation help for a `search` target

If runtime execution for real load/search results is available, verify that too.

If the current CNCF surface only supports help-level confirmation at this step,
record that honestly and do not overclaim.

### Part 4. README

Update the README so it clearly explains:

- this lab uses CNCF initial data placement
- the placement is in `entity.d` and/or `data.d`
- the lab focuses on `load` and `search`
- this is different from `02-crud`
- this is different from `02.b-simpleentity-crud-lab`
- the actual commands used for verification

## Do

1. Keep the sample model-driven.
2. Use CNCF initial data placement from `entity.d` / `data.d`.
3. Verify with `--discover=classes`.
4. Update the phase checklist only for facts actually confirmed.
5. Keep everything in English.

## Do Not

- Do not turn `02.a` into a DB lab.
- Do not add a hand-written repository or TSV store.
- Do not invent a sample-local seed mechanism if CNCF already provides the placement feature.
- Do not change the role of `02.b-simpleentity-crud-lab`.
- Do not mark `02.a` as `DONE` unless seed initialization and runtime `load` / `search` confirmation are actually completed.

## Acceptance Criteria

- `02.a-crud-seed-import-lab` uses `entity.d` and/or `data.d`
- the README explains the seed placement clearly
- `cozyGenerate` succeeds
- `compile` succeeds
- runtime `load` / `search` confirmation is recorded honestly
- the phase checklist reflects the actual verification state

## Report Back

Report only these facts:

- what files were added or changed
- whether `entity.d`, `data.d`, or both were used
- what runtime commands succeeded
- whether real `load` / `search` results were confirmed, or only help-level confirmation
- what still remains, if anything
