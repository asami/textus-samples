# 06.b-aggregate-relation-boundary-model Implementation Record

- status: done
- date: 2026-03-31

## Scope

First line for separating aggregate relation, boundary, and join as independent axes.

## Model

- relation
  - `composition`
  - `aggregation`
  - `association`
- boundary
  - `internal`
  - `external`
- join
  - `direct`
  - `reverse`
  - `through`

## Confirmed

- aggregate member metadata now carries `kind`, `boundary`, and `join`
- generated component metadata carries all three axes
- runtime aggregate assembly now treats `association + external` differently from root-joined members
- runtime aggregate assembly now reads explicit `join`
- `bash run.sh` succeeds
- one aggregate load route works with actual entities
- aggregate search works with the same relation/boundary model
- the current demo confirms:
  - `OrderLine = composition + internal`
  - `ShipmentOrder = aggregation + external + reverse`
  - `User = association + external + direct`
  - `OrderLine` is embedded in `Order`
  - `ShipmentOrder` is attached by external aggregation
  - `User` is attached by external association
  - aggregate `load` returns structured JSON
  - aggregate `search` returns structured JSON

## Notes

- the metadata and runtime first step are in place
- `06.b` now has a real runnable aggregate load/search line
- stronger update-side semantics are deferred to `06.c`
