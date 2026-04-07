# 07-aggregate implementation record

## Summary

`07-aggregate` was shifted from a same-JVM demo entry point to a shell-first aggregate sample.

The active sample line now demonstrates:

- root entity creation
- job result resolution
- aggregate command execution through a preferred impl factory
- aggregate load returning root plus member lines

## Runtime Notes

- The preferred impl factory must be loaded through `org.sample.aggregate.impl.AggregateSampleComponentFactory`.
- `add-line` needs CLI key normalization from `order-id` / `line-name` to `orderId` / `lineName`.
- `load-order-aggregate` needs `id`-based lookup rather than `orderId` lookup.

## Verified Flow

1. `aggregate-sample.entity.create-order-record`
2. `job-control.job.await-job-result`
3. `aggregate-sample.order.add-line`
4. `aggregate-sample.order.load-order-aggregate`

Verified result shape:

- root `Order`
- member `OrderLine`

## Outstanding Note

`search-order-aggregate` still needs separate treatment.
The shell-first sample is intentionally anchored on `create -> await -> add-line -> load`, which is the stable aggregate path.
Search should be revisited together with aggregate visibility semantics in a later sample step.

The former same-JVM proof was removed from the sample path and moved to `cozy` scripted as `aggregate-demo-proof`.
