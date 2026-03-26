# 02-crud Implementation Record

Status: `Completed`

Implemented on 2026-03-26.

## Summary

`02-crud` is now a small CRUD sample around one entity, `Item`.

It demonstrates:

- one entity type
- minimal repository behavior
- command/query separation at a simple level
- a synchronous CRUD-oriented workflow

## Implementation

- added `crud.Item` and a minimal repository
- added `createItem`, `getItem`, and `listItems`
- kept the sample small and local
- wired `run.sh` to command mode
- updated the README to describe the real commands

## Verification

Fresh demo state:

```bash
rm -rf target
```

- `./run.sh crud.main.createItem item1 apple` succeeded and printed `created: item1 apple`
- `./run.sh crud.main.createItem item2 "green apple"` succeeded and created a second item with a space-containing name
- `./run.sh crud.main.getItem item1` succeeded and printed `item: item1 apple`
- `./run.sh crud.main.listItems` succeeded and printed `items: [item1:apple, item2:green apple]`
- duplicate create is now rejected instead of overwriting:
  - `./run.sh crud.main.createItem item1 apple`
  - output: `item already exists: item1`
- shell argument boundaries are preserved through the wrapper:
  - `./run.sh crud.main.createItem item2 "green apple"`
  - the created item keeps `green apple` as one logical field value

## Notes

- the repository keeps state in memory and synchronizes to a tiny local file so the CLI examples can be run independently
- the documented demo flow now starts by removing `target/` to avoid confusing leftover state
- `createItem` now behaves as create, not upsert
- the sample does not depend on other samples
- this sample is intentionally simpler than later samples
