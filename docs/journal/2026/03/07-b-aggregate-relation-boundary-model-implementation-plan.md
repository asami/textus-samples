# 07.b-aggregate-relation-boundary-model Implementation Plan

## Goal

Create a runnable sample that shows relation and boundary as separate axes.

## Example Story

- `Order`
- `OrderLine`
- `ShipmentOrder`
- `User`

## Axis Mapping

- `OrderLine`
  - `composition + internal`
- `ShipmentOrder`
  - `aggregation + external`
- `User`
  - `association + external`

## First Runnable Line

1. define aggregate metadata with both `kind` and `boundary`
2. load one `Order` aggregate
3. attach:
   - embedded/internal line information
   - external related shipment information
   - external associated user information
4. confirm that `ShipmentOrder` and `User` are not treated as the same category

## Current Framework Line

- metadata path is ready
- generated component path is ready
- runtime has a first distinction for `association + external`

## Next Work

1. create sample skeleton
2. model `Order`, `ShipmentOrder`, and `User`
3. add one runnable aggregate load/search demo
4. update README and phase with actual verification
