# 06.b-aggregate-relation-boundary-model

## Overview

This sample extends the aggregate line by separating three axes:

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

The purpose is to show that:

- structural relation
- aggregate transaction boundary
- join direction

are not the same thing.

## Example Mapping

- `OrderLine`
  - `composition + internal`
- `ShipmentOrder`
  - `aggregation + external + reverse`
- `User`
  - `association + external + direct`

This makes `ShipmentOrder` different from `User`.

- `User` is a plain external association.
- `ShipmentOrder` is an external related structure that is still stronger than a plain association.

In runtime terms, `ShipmentOrder` is intended to matter on the update side as well.

- it may be referenced by behavior
- it may be referenced by invariant / guard
- it may participate in follow-up update or cascade semantics

`User` is not intended to carry that same weight.

## Intended Point

This sample is for aggregate assembly where:

- some elements belong to the aggregate boundary
- some elements are external but structurally relevant
- some elements are only associated reference context

## Current Line

The current first line is:

1. aggregate member metadata supports both `kind` and `boundary`
2. aggregate member metadata also supports `join`
3. generated component metadata carries all three axes
4. runtime aggregate assembly reads `join` explicitly
   - `direct`
   - `reverse`
5. a runnable aggregate load/search uses actual:
   - `Order`
   - `OrderLine`
   - `ShipmentOrder`
   - `User`
6. aggregate `search` uses the same relation/boundary/join model and returns structured JSON

Use:

```bash
bash run.sh
```

The current output confirms:

- one `Order` aggregate is loaded
- aggregate `search` returns one `Order` aggregate in structured JSON
- `lines` contains embedded `OrderLine`
- `shipment_orders` is attached from external `ShipmentOrder` using `reverse` join
- `user` is attached from external `User` using `direct` join
- `ShipmentOrder` is not treated as the same category as `User`

This first line is complete.

The next extension is `06.c-aggregate-external-update-semantics`.
