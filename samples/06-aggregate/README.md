# 06-aggregate

## Overview

This sample family is the first dedicated aggregate-oriented slot after `05-job`.

Its purpose is to show aggregate-shaped access as an independent structural topic,
not only as a side note of CRUD or CQRS.

## What It Is For

- aggregate-oriented model shape
- aggregate load access
- aggregate search access
- comparison with plain entity-oriented access when useful
- application-provided delegated aggregate behavior

This slot is intended to use the application-join aggregate pattern:

- aggregate is built from multiple entities at application/runtime level

This sample is not intended to use the single-record encoded-object pattern as its main line.
That pattern is also valid in real applications, but it is not the primary explanatory shape here.
The companion sample [06.a-aggregate-single-record-lab](/Users/asami/src/dev2026/cncf-samples/samples/06.a-aggregate-single-record-lab/README.md)
shows that single-record shape directly.

## Current Line

The current first line is:

1. `Order` root and `OrderLine` member are modeled in Cozy
2. `loadOrderAggregate` works
3. `searchOrderAggregate` works
4. `addLine` runs through delegated `AggregateBehavior`
5. invalid quantity is surfaced as invariant failure
6. application-provided logic remains in delegated `AggregateBehavior`
7. generated aggregate metadata for members/commands/invariants is consumed by the framework mainline
8. aggregate-internal visibility is used so draft root/member entities remain observable during aggregate construction
9. generated aggregate load/search mainline uses framework default aggregate collection binding
10. generated aggregate companion implements `AggregateAssembler`, so member attach is structural rather than reflection-based

Preparatory note for the next extension line:

- `06.b-aggregate-relation-boundary-model` will document relation and boundary as separate axes
- relation categories: `composition`, `aggregation`, `association`
- boundary categories: `internal`, `external`
- the example mapping will distinguish `OrderLine`, `ShipmentOrder`, and `User`

## How To Run

Use:

```bash
bash run.sh
```

This runs [OrderAggregateDemo.scala](/Users/asami/src/dev2026/cncf-samples/samples/06-aggregate/src/main/scala/org/sample/aggregate/OrderAggregateDemo.scala), which:

1. creates one `Order`
2. executes `AggregateSample.Order.addLine`
3. checks one invalid `addLine` case with `quantity = 0`
4. loads the aggregate through `AggregateSample.Order.loadOrderAggregate`
5. searches aggregates through `AggregateSample.Order.searchOrderAggregate`

The current output is one JSON object with:

- `orderId`
- `addLine`
- `invalidAddLine`
- `load`
- `search`

`addLine` and `load` show the joined aggregate shape: root order plus member lines.
`invalidAddLine` shows the surfaced invariant failure.
The current implementation uses generated aggregate metadata to resolve:

- member entity name
- member join field name
- delegated command name
- invariant name

## Why This Is Aggregate-Oriented

This sample is not plain CRUD because the read surface is not an entity record.
`loadOrderAggregate` and `searchOrderAggregate` return an aggregate-shaped result built from:

- `Order`
- `OrderLine`

The first line intentionally keeps persistence truth in entities and performs the aggregate join in framework aggregate collection logic.
Generated aggregate metadata now supplies the join/member description, and the framework resolves members through `ExecutionContext.entitySpace` first with `EntityStore` fallback.
The current first-line demo uses aggregate-internal visibility so draft root/member entities remain observable during aggregate construction without broadening normal entity search visibility.
Aggregate member attach is now handled through generated `AggregateAssembler` instead of runtime reflection.

## Status

Implemented to the first aggregate line including one delegated behavior, one invariant failure, generated aggregate metadata consumption, framework default aggregate collection binding, `entitySpace`-based member resolution, aggregate-internal visibility, and structural `AggregateAssembler`-based member attach.

The active work order is:

- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/06-aggregate-development-instruction.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/06-aggregate-work-checklist.md`

Planned extension slot:

- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/06-b-aggregate-relation-boundary-model-mini-low-instruction.md`
