# 08.c-view-cache-lab

## Overview

This lab extends the `08-view` line toward UI list rendering.

Its purpose is to show that the main read path for list screens is view search,
and that the runtime now treats paged search as a cache-aware path.

## Position

- `08-view`
  - default generated view load/search
- `08.a-view-definition-lab`
  - named view definition
- `08.b-simpleentity-view-lab`
  - `SimpleEntity` variant
- `08.c-view-cache-lab`
  - paged view search for UI lists

## First Completion Line

The first completion line is:

1. one view sample is modeled for repeated list access
2. one runtime demo shows chunked page reuse
3. one runtime demo shows small unbounded-query reuse
4. one metrics path makes cache hit/miss visible
5. the README explains the current cache policy

## Status

Implemented to the first UI-list cache line.

## How To Run

Use:

```bash
bash run.sh
```

This runs one single-process demo.

The demo shows:

- page 1
- page 2
- page 3
- one small unbounded query executed twice
- metrics snapshot at the end

Observed output:

- page 1 returns `Alice`, `Bella`
- page 2 returns `Bella`, `Chloe`
- page 3 returns `Chloe`, `Diana`
- the backend query count stops increasing on page 3
- the small unbounded query returns `Bob` twice
- metrics show chunk hit/miss and small-query hit/miss

## Runtime Cache Policy

The current runtime line is:

- view load is cached by id
- view search with `offset/limit` is cached by query condition and chunk
- `queryChunkSize` is a variation point
- search without `offset/limit` is cached only when the result size is at most `queryChunkSize`
- entity writes invalidate cached view results

## Metrics

The current runtime metrics line is:

- `view.query.chunk.hit`
- `view.query.chunk.miss`
- `view.query.small.hit`
- `view.query.small.miss`
- `view.query.small.bypass`
- `view.load.hit`
- `view.load.miss`
- `view.invalidate`

This sample uses metrics rather than timing.
Its role is to make the paged UI-list cache policy explicit and observable.
