# 06-aggregate Implementation Record

date=2026-03-30
status=done

## Confirmed

- `sbt --batch cozyGenerate` succeeds in `/Users/asami/src/dev2026/cncf-samples/samples/06-aggregate`
- `sbt --batch compile` succeeds
- `command help aggregate-sample.order.load-order-aggregate` resolves
- `bash run.sh` succeeds
- `addLine` executes through delegated `AggregateBehavior`
- invalid quantity fails with `Conclusion(quantityPositive: quantity must be > 0)`
- generated `entity.aggregate.Order` now includes `lines: Vector[OrderLine]`
- `load` returns joined member `lines`
- `search` returns one aggregate hit through aggregate-internal visibility
- direct entity search with explicit lifecycle constraints is now consistent again
- reflection-based aggregate attach is removed
- generated aggregate companion now implements `AggregateAssembler`
- framework default aggregate collection test passes again after the structural change

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
- framework default aggregate collection binding for aggregate read construction

Aggregate read construction is no longer sample-local.
The sample factory remains responsible for delegated behavior only.
Aggregate member attach is no longer reflection-based; it now goes through generated `AggregateAssembler`.

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

## Current Limit

Current generated aggregate metadata is richer and now includes:

- `members`
- `commands`
- `state`
- `invariants`

Current member resolution uses `ExecutionContext.entitySpace` first and falls back to `EntityStore`.
This restores resident-entity application join without going directly through `DataStoreSpace`.

The current first-line framework state is:

- aggregate-internal visibility exists as a dedicated execution-context flag
- the sample no longer needs content-manager privilege just to build one aggregate from draft root/member entities
- aggregate load/search go through framework default aggregate collection binding in the generated mainline
- direct entity search with explicit lifecycle parameters is consistent again

What remains is broader generalization work, not a first-line blocker.

## Next

- generalize aggregate collection binding beyond the current first-line sample shape
- refine the minimal runtime rule for aggregate-internal visibility without overexposing normal entity search
- extend framework tests for more aggregate member shapes if needed
