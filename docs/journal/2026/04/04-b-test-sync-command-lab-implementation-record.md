# 04.b-test-sync-command-lab Implementation Record

## Summary

`06.b-test-sync-command-lab` was normalized as the sample for runtime test sync.

The sample now shows:

- the default async/job-backed command shape
- the runtime sync override
- preservation of the same external job interface

## What Changed

- rewrote the README as a shell-first test-sync sample
- replaced the old sample-runner wrappers with direct `bin/cncf` commands
- split the flow into:
  - `run-default.sh`
  - `run-sync.sh`
  - `run.sh`
- updated the explanation so `04.a` and `04.b` work as a pair:
  - `04.a` for designed sync
  - `04.b` for test/local sync

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help test-sync.item.create-item`
- `bash ../../bin/cncf --discover=classes command test-sync.meta.describe --format yaml`
- `bash ../../bin/cncf --discover=classes command TestSync.Item.createItem --name beta --title Beta`
- `bash ../../bin/cncf --discover=classes command --textus.runtime.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta`
- `bash ../../bin/cncf --discover=classes command TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml`
- `bash ../../bin/cncf --discover=classes command --textus.runtime.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml`

## Observed Output

Command help confirms:

- `service: Item`
- `name: createItem`
- `returns: CreateItemResult`

Metadata confirms:

- `createItem`
  - `kind: COMMAND`
  - `input_type: CreateItem`
  - `output_type: CreateItemResult`

Default execution confirms:

- the command returns `cncf-job-...`
- envelope shows `textus-execution.interface-shape: job`

Runtime sync override confirms:

- the command still returns `cncf-job-...`
- envelope still shows `interface-shape: job`
- envelope adds `requested-mode: sync-job-async-interface`

## Main Point

`04.b` is not designed sync.

It keeps the command async by contract and uses sync only as a runtime testing/debugging aid.
