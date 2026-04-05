# 02-crud implementation record

## Scope

This line is the base model-driven CRUD sample for the `02-*` family.

The first completion target is:

1. one CML model generates a CRUD-oriented component
2. one shell-visible component help route works
3. one shell-visible service help route works
4. one shell-visible operation help route works
5. one metadata route explains the generated runtime surface

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/02-crud`
- setup-first command flow
  - `../../bin/setup cozy`
- shell-first runtime commands through `bin/cncf`
  - `command help crud`
  - `command help crud.item`
  - `command help crud.item.create-item`
  - `command crud.meta.describe --format yaml`

## Verified Result

- `sbt --batch clean compile` succeeds
- component help succeeds
- service help succeeds
- operation help succeeds
- metadata describe succeeds
- `run.sh` succeeds
- `cozy` scripted fixture succeeds with `CRUD_SURFACE_OK`

## Position

This sample is the first CRUD/reference point.

It does not yet verify preloaded records or storage behavior.
Those concerns move to later `02.*` labs, starting with `02.a-crud-seed-import-lab`.

Its generated-surface inspection line is also covered by `cozy` scripted under `crud-surface`.
