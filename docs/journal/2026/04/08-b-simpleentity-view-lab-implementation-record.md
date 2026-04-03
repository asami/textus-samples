# 08.b-simpleentity-view-lab implementation record

## Scope

This sample is the `Entity extends SimpleEntity` variant of `08-view`.

## Intended first line

1. generated `ENTITY > VIEW` works for an entity that extends `SimpleEntity`
2. one help path resolves
3. one load route works
4. one search route works

## Notes

- The base entity is modeled explicitly as `SimpleEntity`.
- The concrete entity is `Person`.
- The generated view line should inherit `id`, `name`, and `title` from the base entity and add `city` from the concrete entity.
- Verified commands:
  - `command help simple-entity-view-sample.view.load-person`
  - `command simple-entity-view-sample.view.load-person --id tokyo-sales-entity-person-1742198400000-abcd1234`
  - `command simple-entity-view-sample.view.search-person-record --name Alice`
- Verified output fields:
  - `id`
  - `name`
  - `title`
  - `city`
- Runtime note:
  - view load/search uses the shared runtime view cache
  - entity writes invalidate cached view results
