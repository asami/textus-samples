# 08.c-view-cache-lab implementation record

## Scope

This line extends `08-view` toward UI list rendering.

The first completion target is:

1. one view sample is modeled for repeated list access
2. one runtime demo shows chunked page reuse
3. one runtime demo shows small unbounded-query reuse
4. one metrics path makes cache hit/miss visible
5. the README explains the current cache policy

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/08.c-view-cache-lab`
- single-process runtime demo
  - page 1
  - page 2
  - page 3
  - small unbounded query executed twice
- metrics snapshot
  - `view.query.chunk.hit`
  - `view.query.chunk.miss`
  - `view.query.small.hit`
  - `view.query.small.miss`

## Runtime Note

The current runtime line is:

- view load is cached by id
- paged view search is cached by query condition and chunk
- `queryChunkSize` is a variation point
- unbounded search is cached only when the result size is at most `queryChunkSize`
- entity writes invalidate cached view results
- metrics are emitted through `EntityAccessMetricsRegistry`

## Position

This sample is not an aggregate line.
It is the first view sample explicitly shaped for repeated list access in a UI.
It uses framework primitives directly so that chunk-cache behavior and metrics can be observed without unrelated sample runtime noise.
