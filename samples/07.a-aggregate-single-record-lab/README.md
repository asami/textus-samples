# 07.a-aggregate-single-record-lab

## Overview

This lab explains the single-record aggregate pattern as a concrete companion to [07-aggregate](/Users/asami/src/dev2026/cncf-samples/samples/07-aggregate/README.md).

The main line in `07-aggregate` is application-join:

- `Order` root
- `OrderLine` member entity
- aggregate is constructed from multiple persisted entities

This lab shows the other common pattern:

- `Order` is one persisted `Entity`
- `OrderLine` is a `Value Object`
- `OrderLine` is stored inside `Order`
- the aggregate is restored from one record

## What This Lab Shows

- one-record aggregate encoding
- `OrderLine` as embedded value object
- no application join during aggregate restore
- roundtrip from generated entity -> `Record` -> generated entity

## Model

- component: `aggregate-single-record-sample`
- entity: `Order`
- value object: `OrderLine`
- persistence shape: one `Order` record containing `lines`

## How To Run

```bash
bash run.sh
```

```bash
bash run-datastore.sh
```

The demo:

1. creates two `OrderLine` value objects
2. creates one `Order` entity that embeds them
3. converts `Order` to one `Record`
4. restores `Order` from that same `Record`
5. prints the encoded and restored shapes

Example output shape:

```json
{
  "pattern": "single-record-aggregate",
  "entity": "Order",
  "value-object": "OrderLine",
  "record": {
    "id": "major-minor-entity-order-20260330000000-aaa111",
    "name": "Alpha",
    "status": "Active",
    "lines": [
      { "name": "Widget", "quantity": 2 },
      { "name": "Cable", "quantity": 1 }
    ]
  },
  "restored": {
    "id": "major-minor-entity-order-20260330000000-aaa111",
    "name": "Alpha",
    "status": "Active",
    "lines": [
      { "name": "Widget", "quantity": 2 },
      { "name": "Cable", "quantity": 1 }
    ]
  },
  "line-count": 2
}
```

What this proves:

- `OrderLine` is encoded as embedded records inside `Order.lines`
- restore from one record succeeds
- the restored aggregate keeps both value objects

The datastore demo proves the same shape survives the framework entity-store path:

- `EntityStoreSpace.create`
- `EntityStoreSpace.load`
- embedded `OrderLine` values remain embedded records after persistence roundtrip

## Why It Matters

This is often the natural implementation for aggregates whose lifecycle is strongly shared.

For `Order` / `OrderLine`, this means:

- `OrderLine` is not persisted as an independent entity
- the transactional and lifecycle boundary is one `Order`
- loading the aggregate does not require framework-side application join

That is different from the main `07-aggregate` sample, where `OrderLine` is a separate entity and the framework builds the aggregate by joining persisted members.
