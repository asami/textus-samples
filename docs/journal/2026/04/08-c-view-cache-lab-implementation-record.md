# 08.c-view-cache-lab implementation record

## Scope

This line extends `08-view` toward UI list rendering.

The first completion target is:

1. one view sample is modeled for repeated list access
2. one shell-visible paged search route works with `query.limit`
3. one shell-visible paged search route works with `query.offset`
4. one metadata path explains the list-oriented view line
5. the README explains the current shell invocation path

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/08.c-view-cache-lab`
- setup-first command flow
  - `../../bin/setup cozy`
- paged summary search route
  - `command view-cache-sample.view.search-person-summary-record --city Tokyo --query.limit 2 --query.offset 0`
  - `command view-cache-sample.view.search-person-summary-record --city Tokyo --query.limit 2 --query.offset 1`
  - `command view-cache-sample.view.search-person-summary-record --city Tokyo --query.limit 2 --query.offset 2`
- metadata projection
  - `command view-cache-sample.meta.describe --format yaml`

## Runtime Note

The current runtime line is:

- view load is cached by id
- paged view search is cached by query condition and chunk
- named view browser now uses the same `ViewCollection` path, so paging and cache behavior also apply to `summary`
- `queryChunkSize` is a variation point
- unbounded search is cached only when the result size is at most `queryChunkSize`
- entity writes invalidate cached view results

## Verified Result

- `run.sh` succeeds
- page 1 returns `Emma`, `Bella`
- page 2 returns `Bella`, `Alice`
- page 3 returns `Alice`, `Diana`
- `query.limit` and `query.offset` are preserved in the result metadata

## Position

This sample is not an aggregate line.
It is the first view sample explicitly shaped for repeated list access in a UI.
Internal cache-proof logic is being moved out of the sample path and into `cozy` scripted verification.

Current scripted status:

- `view-cache-metrics` now contains the direct `ViewCollection` cache proof
- fixture generation succeeds
- `check-view-cache.sh` succeeds and prints `VIEW_CACHE_OK`
- the standalone scripted fixture is the accepted verification line for this sample family
