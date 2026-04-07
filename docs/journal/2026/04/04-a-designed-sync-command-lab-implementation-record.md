# 04.a-designed-sync-command-lab Implementation Record

## Summary

`06.a-designed-sync-command-lab` was normalized as the first explicit designed-sync command sample.

The sample now shows:

- a command-side contract
- metadata for the command
- direct synchronous completion without job control

## What Changed

- rewrote the README as a shell-first designed-sync sample
- replaced the old sample-runner wrapper with direct `bin/cncf` commands
- updated `run.sh` to cover:
  - command help
  - metadata describe
  - synchronous command execution

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help designed-sync.item.create-item`
- `bash ../../bin/cncf --discover=classes command designed-sync.meta.describe --format yaml`
- `bash ../../bin/cncf --discover=classes command designed-sync.item.create-item --name beta --title Beta`
- `bash run.sh`

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
  - `input_value_kind: COMMAND_VALUE`

Execution confirms:

- the command returns the created payload directly
- there is no job id
- there is no separate await step

Observed runtime result:

```yaml
name: beta
title: Beta
```

## Main Point

`04.a` shows that CNCF supports a command that is synchronous by design.

The operation remains part of the command side, but the execution contract is immediate rather than job-backed.
