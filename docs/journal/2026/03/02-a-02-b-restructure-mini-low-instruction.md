# 02.a / 02.b Restructure Mini-Low Instruction

Status: `Active Instruction`

Reviewed and updated on 2026-03-27.

## Purpose

Restructure the CRUD labs after `02-crud` so that:

- the first lab uses the new CNCF initial test-data import feature
- that first lab can verify `load` and `search` behavior against preloaded data
- the current `02.a-simpleentity-crud-lab` becomes `02.b-simpleentity-crud-lab`

The goal is to make the first follow-up lab more practical:

- `02.a` = preloaded data / load / search verification
- `02.b` = `SimpleEntity`-focused variation

## Repository Context

- samples repo:
  - `/Users/asami/src/dev2026/cncf-samples`
- CNCF design note:
  - `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/test-data-import-design-2026-03-27.md`

## Read First

- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/test-data-import-design-2026-03-27.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/02-crud.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/02.b-simpleentity-crud-lab.md`
- `/Users/asami/src/dev2026/cncf-samples/README.md`

## Problem Statement

Today:

- `02-crud` shows the model-driven CRUD surface
- `02.b-simpleentity-crud-lab` shows the `SimpleEntity` variation

But we now have a CNCF-side test-data import feature.

That means the first lab after `02-crud` should be able to preload data and verify:

- `load`
- `search`

This is a better first practical lab than the current ordering.

## Required New Structure

Make the sequence become:

- `02-crud`
- `02.a-crud-seed-import-lab`
- `02.b-simpleentity-crud-lab`

Interpretation:

- `02.a-crud-seed-import-lab`
  - new first lab
  - uses CNCF initial data import
  - verifies preloaded data with `load` / `search`
- `02.b-simpleentity-crud-lab`
  - current `02.a-simpleentity-crud-lab`, renamed and repositioned as `02.b-simpleentity-crud-lab`
  - keeps the `SimpleEntity` focus

## Main Task

### Part 1. Rename the current lab

Rename:

- `samples/02.a-simpleentity-crud-lab`
- `docs/phase/samples/02.a-simpleentity-crud-lab.md`

to:

- `samples/02.b-simpleentity-crud-lab`
- `docs/phase/samples/02.b-simpleentity-crud-lab.md`

Update all references in:

- root README
- phase docs
- related journal documents

### Part 2. Create the new 02.a lab

Create a new lab:

- `samples/02.a-crud-seed-import-lab`

This lab should:

- follow the same CML/cozy/model-driven method as `02-crud`
- use the new CNCF initial data import capability
- preload data before runtime verification
- demonstrate `load` and `search` behavior against preloaded data

### Part 3. Documentation

Add or update:

- sample README
- phase checklist
- implementation record or work record if needed

The new README must clearly explain:

- this lab exists to demonstrate initial data import
- data is preloaded before runtime verification
- the main checks are `load` and `search`
- this is different from `02-crud`
- this is different from `02.b-simpleentity-crud-lab`

## Do

1. Keep `02-crud` as the base model-driven CRUD sample.
2. Make the new `02.a` a load/search lab that depends on initial data import.
3. Reposition the current `SimpleEntity` lab as `02.b`.
4. Update sample order everywhere it appears.
5. Keep the docs in English.

## Do Not

- Do not change `02-crud` back into a hand-written CRUD sample.
- Do not make `02.a` about DB integration.
- Do not skip the rename and leave both labs as `02.a`.
- Do not mark the new `02.a` or renamed `02.b` as `DONE` unless their docs and checklists are actually updated.

## Minimum Expected Shape

For the new `02.a-crud-seed-import-lab`, the minimal result should include:

- `README.md`
- `build.sbt`
- `project/build.properties`
- `project/plugins.sbt`
- `src/main/cozy/*.cml`
- any minimal lab-local test-data file needed by the new import feature
- phase checklist document

## Acceptance Criteria

- current `02.a-simpleentity-crud-lab` is renamed to `02.b-simpleentity-crud-lab`
- all visible references use the new `02.a` / `02.b` order
- a new `02.a-crud-seed-import-lab` exists
- the new `02.a` README explains initial data import and `load` / `search`
- the new `02.a` is clearly separated from the `SimpleEntity` lab
- the new `02.b` README still explains the `SimpleEntity` purpose

## Report Back

Report only these facts:

- what was renamed
- what was created for the new `02.a`
- what documents were updated
- what is still unfinished, if anything
