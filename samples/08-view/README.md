# 08-view

## Overview

This sample family is the first dedicated view-oriented slot after `07-aggregate`.

Its purpose is to show view/read-model shaped access as an independent structural topic,
separate from aggregate and separate from plain CRUD.

## What It Is For

- view-oriented model shape
- view load access
- view search access
- read-model style access and presentation-oriented retrieval

## First Completion Line

The first completion line is:

1. one view-oriented sample model exists
2. one view load route works
3. one view search route works
4. the README explains why this is view-oriented rather than aggregate-oriented access

## Status

Implemented to the first view line.

## Current Line

The current first line is:

1. one view-oriented sample model exists
2. one view load route works
3. one view search route works
4. the README explains why this is view-oriented rather than aggregate-oriented access

## How To Run

Use:

```bash
bash run.sh
```

This checks:

- `command help view-sample.view.load-person`
- `command view-sample.view.load-person --id tokyo-sales-entity-person-1742198400000-abcd1234`
- `command view-sample.view.search-person-record --name Alice`

The sample uses:

- generated `ENTITY > VIEW` metadata
- imported seed data under `entity.d`
- local descriptor metadata under `car.d/meta`

Observed results:

- `load-person` returns the seeded `Alice` view record
- `search-person-record --name Alice` returns one matching view record

## Why This Is View-Oriented

This sample is not aggregate-oriented because the runtime surface is a read-model projection.
The `view` service loads and searches `entity.view.Person`, not an aggregate assembled from multiple entities.

The structural point is:

- source persistence remains in `ENTITY`
- read access goes through generated `VIEW`
- runtime projection converts entity records into view records before returning them

The active work order remains:

- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/08-view-development-instruction.md`
