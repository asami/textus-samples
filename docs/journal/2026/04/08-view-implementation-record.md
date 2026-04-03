# 08-view Implementation Record

## Goal

Establish the first dedicated view/read-model sample after `07-aggregate`.

## First Line

The first line is:

1. one view-oriented model exists
2. one view load route is runnable
3. one view search route is runnable
4. the README explains why the sample is view-oriented rather than aggregate-oriented

## Current Approach

- use `ENTITY > VIEW` as the structural source
- use generated `view` service operations
- use imported seed data from `entity.d`
- use local descriptor metadata from `car.d/meta`
- keep the runtime local and model-driven
- avoid handwritten repository code

## Verification

Verified with:

- `command help view-sample.view.load-person`
- `command view-sample.view.load-person --id tokyo-sales-entity-person-1742198400000-abcd1234`
- `command view-sample.view.search-person-record --name Alice`

Observed results:

- help resolves for the generated `view` service
- `load-person` returns the seeded `Alice` record
- `search-person-record --name Alice` returns one projected view row

## Runtime Note

The first line required a framework mainline fix:

- default view bootstrap now projects source entity records into generated `entity.view.*` objects
- default view browser now supports query-based search instead of `Browser.query is not supported`

The current runtime line also includes:

- view load cache
- view query cache
- invalidation of cached view results on entity write operations
