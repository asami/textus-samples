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
- expose the sample through shell-visible `bin/cncf` commands
- keep `run.sh` as a batch wrapper around explicit shell commands
- avoid handwritten repository code

## Verification

Verified with:

- `../../bin/setup cozy`
- `command help view-sample.view.load-person`
- `command view-sample.view.load-person --id tokyo-sales-entity-person-1742198400000-abcd1234`
- `command view-sample.view.search-person-record --name Alice`
- `bash run.sh`

Observed results:

- help resolves for the generated `view` service
- `load-person` returns the seeded `Alice` record
- `search-person-record --name Alice` returns one projected view row
- `run.sh` reproduces the same shell command flow from the sample directory

## Runtime Note

The first line required a framework mainline fix:

- default view bootstrap now projects source entity records into generated `entity.view.*` objects
- default view browser now supports query-based search instead of `Browser.query is not supported`

The current runtime line also includes:

- view load cache
- view query cache
- invalidation of cached view results on entity write operations

## Sample Position

`08-view` is the base view/read-model sample.

- later view samples, including `08.c`, refer back to this line for the basic CQRS query/view explanation
- this sample keeps the focus on the simplest load/search shape before adding paging or cache-policy detail
