# 08.b-simpleentity-view-lab implementation record

## Scope

This sample is the `Entity extends SimpleEntity` variant of `08-view`.

## Intended first line

1. generated `ENTITY > VIEW` works for an entity that extends `SimpleEntity`
2. one help path resolves
3. one load route works
4. one search route works
5. the README explains the inherited field shape through shell commands

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/08.b-simpleentity-view-lab`
- shell-first command flow under `bin/cncf`
- `run.sh` as a batch wrapper around explicit shell commands
- scripted fixture under `/Users/asami/src/dev2025/cozy/src/sbt-test/cozy/simpleentity-view`
- inherited `SimpleEntity` field line on the generated `view` surface
- projection-fixed help path
  - `command help simple-entity-view-sample.view.load-person`
- view load route
  - `command simple-entity-view-sample.view.load-person --id tokyo-sales-entity-person-1742198400000-abcd1234`
- view search route
  - `command simple-entity-view-sample.view.search-person-record --name Alice`
- metadata projection
  - `command simple-entity-view-sample.meta.describe --format yaml`
- direct scripted generation path
  - `runMain cozy.Cozy modeler-scala ... --save=out.d`
- scripted verification
  - `sh check-simpleentity-view.sh`

## Observed Results

- help resolves for `load-person`
- `load-person` returns the seeded `Alice` row
- the returned view row exposes:
  - `name`
  - `title`
  - `id`
  - `city`
- `search-person-record --name Alice` returns one matching row
- the search output is structured:
  - `query.condition.name = Alice`
  - `total_count = 1`
  - `fetched_count = 1`
- `meta.describe` exposes:
  - `runtime_name = view`
  - one default `person` view definition
  - no named view aliases
  - no custom query aliases
- scripted fixture completes with:
  - `SIMPLEENTITY_VIEW_OK`

## Notes

- The base entity is modeled explicitly as `SimpleEntity`.
- The concrete entity is `Person`.
- The generated view line should inherit `id`, `name`, and `title` from the base entity and add `city` from the concrete entity.
- the sample originally referenced `simplemodeling-model 0.1.1-SNAPSHOT`; it now follows the current `0.1.2-SNAPSHOT` line so generated view models no longer refer to `Recordable`
- the scripted fixture uses the sample CML directly so the modeled inheritance line stays identical between sample and scripted
- Runtime note:
  - view load/search uses the shared runtime view cache
  - entity writes invalidate cached view results
