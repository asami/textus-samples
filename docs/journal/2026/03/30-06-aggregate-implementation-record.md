# 06-aggregate Implementation Record

date=2026-03-30
status=in-progress

## Confirmed

- `sbt --batch cozyGenerate` succeeds in `/Users/asami/src/dev2026/cncf-samples/samples/06-aggregate`
- `sbt --batch compile` succeeds
- `command help aggregate-sample.order.load-order-aggregate` resolves
- `bash run.sh` succeeds
- `addLine` executes through delegated `AggregateBehavior`
- invalid quantity fails with `Conclusion(quantity must be > 0)`
- generated `entity.aggregate.Order` now includes `lines: Vector[OrderLine]`
- `load` returns joined member `lines`
- `search` returns one aggregate hit through aggregate-internal visibility

## Current First Line

The current first line uses:

- generated Cozy model for `Order` and `OrderLine`
- generated `loadOrderAggregate` / `searchOrderAggregate` surfaces
- generated `addLine` command surface
- application-provided factory override in
  `/Users/asami/src/dev2026/cncf-samples/samples/06-aggregate/src/main/scala/org/sample/aggregate/OrderAggregateFactory.scala`
- delegated `AggregateBehavior` for `addLine`
- generated aggregate metadata for:
  - `members`
  - `commands`
  - `invariants`

The join is application-side for now.

Framework-side preparation also now includes a custom aggregate collection binding hook.
This is intended for a later step where aggregate read construction can be supplied through factory wiring instead of only sample-local read action overrides.

## Observed Result

`bash run.sh` returns one JSON object whose fields confirm:

- `addLine` returns an updated aggregate
- `load` contains one `order` plus one member line under `lines`
- `search` returns one aggregate hit
- `invalidAddLine` returns `Conclusion(quantity must be > 0)`
- `invalidAddLine` now surfaces the generated invariant name as
  `Conclusion(quantityPositive: quantity must be > 0)`

This confirms:

- the application-join aggregate read path
- delegated aggregate behavior execution
- invariant failure visibility
- generated aggregate metadata is usable from application-side aggregate logic

## Known Limit

Current generated aggregate metadata is richer and now includes:

- `members`
- `commands`
- `state`
- `invariants`

The remaining limit is no longer sample-local join state.
Current member resolution uses `ExecutionContext.entitySpace` first and falls back to `EntityStore`.
This restores resident-entity application join without going directly through `DataStoreSpace`.

The remaining framework limit is now narrower:

- aggregate-internal visibility exists as a dedicated execution-context flag
- the sample no longer needs content-manager privilege just to build one aggregate from draft root/member entities
- aggregate load/search now go through framework aggregate collection binding in the generated mainline
- the remaining gap is aggregate member identity/join-key consistency, which still needs closer runtime inspection

## Next

- refine the minimal runtime rule for aggregate-internal visibility without overexposing normal entity search
- tighten aggregate member identity/join-key consistency in runtime construction
- decide the minimal runtime contract needed beyond the current aggregate collection bindings
