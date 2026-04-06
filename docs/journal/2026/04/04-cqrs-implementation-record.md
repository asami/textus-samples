# 04-cqrs Implementation Record

## Summary

`04-cqrs` was normalized as the first explicit CQRS runtime sample.

The sample now verifies both:

- the command-side contract and job-backed execution shape
- the query-side immediate read path after the write

## What Changed

- rewrote the README as a shell-first CQRS sample
- replaced the old sample-runner wrapper with direct `bin/cncf` commands
- removed the unused seed import line so the sample stays focused on command-to-query flow
- updated `run.sh` to cover:
  - command-side help
  - entity write help
  - metadata describe
  - server/client write flow
  - await-job-result
  - query-side load
- added a `cozy` scripted fixture for the same flow

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help cqrs.item.create-item`
- `bash ../../bin/cncf --discover=classes command help cqrs.entity.create-item-record`
- `bash ../../bin/cncf --discover=classes command cqrs.meta.describe --format yaml`
- `bash ../../bin/cncf --discover=classes client cqrs.entity.create-item-record --id org-sample-entity-item-20260406000000-gamma111 --name gamma --title Gamma`
- `bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id <job-id>`
- `bash ../../bin/cncf --discover=classes client cqrs.entity.load-item --id org-sample-entity-item-20260406000000-gamma111`
- `bash run.sh`
- `sh check-cqrs-split.sh`

## Observed Output

Command-side help confirms:

- `service: Item`
- `name: createItem`
- `returns: CreateItemResult`

Entity write help confirms:

- `service: entity`
- `name: createItemRecord`
- `returns: unit`

Metadata confirms:

- `createItem`
  - `kind: COMMAND`
  - `input_type: CreateItem`
  - `output_type: CreateItemResult`
- `getItem`
  - `kind: QUERY`
  - `input_type: GetItem`
  - `output_type: ItemResult`

Write execution confirms:

- submit returns a job id first
- await returns `{"id":"org-sample-entity-item-20260406000000-gamma111"}`

Read execution confirms:

- load returns `{"id":"org-sample-entity-item-20260406000000-gamma111","name":"gamma","title":"Gamma"}`

## Main Point

`04-cqrs` makes the execution split visible:

- writes are asynchronous and job-backed
- reads are immediate and query-oriented

That visible difference is the user-facing point of the sample.
