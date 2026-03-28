# 02.e-crud-explicit-sync-lab mini low instruction

## Purpose

Create the first CRUD lab that shows explicit synchronous execution as a runtime
option.

This lab is different from:

- `02.d-crud-server-memory-lab`
  - standard server/client CRUD path
  - command create remains job-backed
- `03.b-test-sync-command-lab`
  - hidden sync behind an async/job-shaped interface

`02.e` is for the other shape:

- the caller explicitly requests synchronous execution
- the command returns the actual result immediately
- the result is not a job id

## Scope

Keep the lab small.

Show only:

1. one CRUD create-style route
2. explicit synchronous execution option
3. immediate returned result
4. one follow-up load or search confirmation route

## Required direction

- keep the same model-driven Cozy/CML CRUD direction as `02-crud`
- do not add handwritten repository logic
- do not redesign CRUD semantics
- do not turn this into a job-control sample

## Runtime direction

Use explicit synchronous runtime execution.

The key point is:

- sync is requested explicitly by runtime parameter
- the caller receives the result directly

This is not hidden sync.

## Minimum expected sample shape

- sample name:
  - `02.e-crud-explicit-sync-lab`
- model:
  - same small CRUD-style item/entity shape as `02-crud`
- runtime:
  - one explicit sync create command
  - one load/search confirmation command

## Verification target

At minimum confirm:

- build succeeds
- help path resolves
- one explicit sync create command succeeds
- the explicit sync create command returns an immediate create result, not a job id
- one load or search confirmation route succeeds

## Documentation expectation

README must clearly explain:

- this lab is about explicit sync execution
- the sync behavior is requested by runtime option
- this differs from:
  - normal CRUD/job-backed command use
  - hidden sync for test/local use

## Stop conditions

Stop immediately if any of these becomes necessary:

- a major CNCF redesign
- new Cozy/CML language features
- handwritten CRUD repository logic
- broad framework work unrelated to explicit sync execution

If blocked, report only:

- the exact missing capability
- the exact command or path where it blocked
- which files were changed before stopping
