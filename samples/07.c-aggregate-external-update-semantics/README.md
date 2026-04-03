# 07.c-aggregate-external-update-semantics

## Overview

This sample continues the aggregate line after:

- `07-aggregate`
- `07.a-aggregate-single-record-lab`
- `07.b-aggregate-relation-boundary-model`

The focus here is the update side of:

- `aggregation + external`

The point is to show that an external aggregated structure can still carry
stronger update semantics than a plain external association.

## First Line

The first line is intentionally narrow:

1. one root aggregate exists
2. one external aggregated related structure exists
3. one plain external association exists
4. one aggregate command is exposed on the root aggregate boundary
5. one delegated `AggregateBehavior` applies the aggregate-side update semantics
6. the delegated behavior updates the external aggregated structure as part of the same semantic line
7. the plain external association is not treated as part of that follow-up update

## Example Mapping

- `Order`
  - root aggregate
- `ShipmentOrder`
  - `aggregation + external`
- `User`
  - `association + external`

In the first line:

- `Order/cancelOrder` is the aggregate-facing command
- `AggregateBehavior` cancels the root `Order`
- the same `AggregateBehavior` cancels related external `ShipmentOrder`
- the associated `User` remains untouched

## Verification

Use:

```bash
bash run.sh
```

The current output confirms:

- one `Order` is created
- one related external `ShipmentOrder` is created
- one external associated `User` is created
- `Order/cancelOrder` runs through delegated `AggregateBehavior`
- the root `Order` becomes `Cancelled`
- the external aggregated `ShipmentOrder` also becomes `Cancelled`
- the `User` remains unchanged

## Note

This sample does not yet attempt:

- compensation
- cascade delete
- event choreography

It fixes only the first update-side semantic line.

In other words, this sample is not a manual two-step update demo.
It is the first runnable line where aggregate-side behavior decides:

- what belongs to the aggregate update semantic
- what external aggregated members follow that semantic
- what external associations remain outside it
