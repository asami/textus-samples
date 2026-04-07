# 03 Operation Implementation Record

## Summary

`05-operation` was normalized as the base operation contract sample.

The sample is intentionally inspection-oriented.
It shows the generated help and metadata surface for one minimal `QUERY` operation contract.

## What Changed

- rewrote the README as a shell-first inspection sample
- simplified `run.sh` to direct `bin/cncf` commands
- made the main verification line:
  - operation help
  - metadata describe

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help operation-contract-sample.greeting.greeting`
- `bash ../../bin/cncf --discover=classes command operation-contract-sample.meta.describe --format yaml`
- `bash run.sh`

## Observed Output

Help confirms:

- `service: Greeting`
- `name: greeting`
- `returns: GreetingResult`

Metadata confirms:

- `runtime_name: greeting`
- `kind: QUERY`
- `input_type: GreetingQuery`
- `output_type: GreetingResult`

## Main Point

`05-operation` is the user-facing contract-definition sample.
It stays minimal on purpose so the later `03.a`, `03.b`, and `04-*` samples can build on a clear operation-modeling baseline.
