# 08.b-simpleentity-view-lab

## Overview

This sample is the `Entity extends SimpleEntity` variant of `08-view`.

Its purpose is to show that the default generated `ENTITY > VIEW` surface still works when the concrete entity inherits view-facing fields from `SimpleEntity`.

## What It Is For

- `Entity extends SimpleEntity`
- inherited entity fields on the generated view line
- default view load access
- default view search access

## First Completion Line

The first completion line is:

1. one `SimpleEntity`-based view sample model exists
2. one view help path works
3. one view load route works
4. one view search route works

## Status

Implemented to the first `SimpleEntity` view line.

## How To Run

Use:

```bash
bash run.sh
```

This checks:

- `command help simple-entity-view-sample.view.load-person`
- `command simple-entity-view-sample.view.load-person --id tokyo-sales-entity-person-1742198400000-abcd1234`
- `command simple-entity-view-sample.view.search-person-record --name Alice`

Observed results:

- `load-person` returns the seeded `Alice` view record
- `search-person-record --name Alice` returns one matching view record
- help selector resolves as `simple-entity-view-sample.view.load-person`

## Runtime Cache

The current runtime line includes the same view cache behavior as the other `08` samples:

- repeated view load/search may be served from cache
- entity writes invalidate the cached view results
- inherited `SimpleEntity` fields do not change the cache contract

## Why This Matters

This line confirms that `ENTITY > VIEW` is not limited to flat entities.
The concrete entity is `Person`, but its stable view-facing fields are inherited from `SimpleEntity`.

The structural point is:

- source persistence remains in `ENTITY`
- common fields come from `SimpleEntity`
- read access still goes through generated `ENTITY > VIEW`
