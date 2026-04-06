# 02.a-crud-seed-import-lab implementation record

## Scope

This line extends `02-crud` with descriptor-first metadata and imported seed data.

The first completion target is:

1. one descriptor-first CRUD sample is modeled
2. one seeded entity can be loaded through the generated entity service
3. one seeded entity can be searched through the generated entity search route
4. one metadata path explains the runtime surface

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab`
- setup-first command flow
  - `../../bin/setup cozy`
- descriptor-first layout
  - `car.d/meta/component-descriptor.yaml`
- imported records under
  - `entity.d/crud.yaml`
- shell-first runtime commands through `bin/cncf`
  - `command help crud.entity.load-item`
  - `command help crud.entity.search-item-record`
  - `command crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111`
  - `command crud.entity.search-item-record --name alpha`
  - `command crud.meta.describe --format yaml`

## Verified Result

- `sbt --batch clean compile` succeeds
- load help succeeds
- search help succeeds
- seeded load succeeds
- seeded search succeeds
- metadata describe succeeds
- `run.sh` succeeds
- `cozy` scripted fixture succeeds with `CRUD_SEED_IMPORT_OK`

## Position

This sample is the first runtime CRUD verification line after `02-crud`.

It proves that descriptor-first wiring and `entity.d` import work together, before the later CRUD labs add other variations.

Its seed-import verification line is also covered by `cozy` scripted under `crud-seed-import`.
