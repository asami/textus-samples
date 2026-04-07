# 03.a Operation Command Lab Implementation Record

## Summary

`05.a-operation-command-lab` was normalized as the first command-oriented contract sample.

The sample now verifies both:

- the generated help and metadata surface for one minimal `COMMAND` operation contract
- the minimal async command flow of `job id -> await-job-result`

## What Changed

- rewrote the README as a shell-first command sample
- added a sample-specific factory to provide the smallest executable command behavior
- updated `run.sh` to cover:
  - operation help
  - metadata describe
  - command submission
  - job await

## Verified Commands

- `bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory command help operation-command-contract-sample.greeting.submit-greeting`
- `bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory command operation-command-contract-sample.meta.describe --format yaml`
- `bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory client operation-command-contract-sample.greeting.submit-greeting --name Alice`
- `bash ../../bin/cncf --component-factory-class org.sample.operationcommand.OperationCommandContractSampleFactory client job-control.job.await-job-result --id <job-id>`
- `bash run.sh`

## Observed Output

Help confirms:

- `service: Greeting`
- `name: submitGreeting`
- `returns: GreetingAccepted`

Metadata confirms:

- `runtime_name: greeting`
- `kind: COMMAND`
- `input_type: GreetingCommand`
- `output_type: GreetingAccepted`
- `input_value_kind: COMMAND_VALUE`

Command execution confirms:

- submit returns a job id first
- await returns `{"status":"accepted","name":"Alice"}`

## Main Point

`03.a` is the first CQRS-`C` sample.
It makes the command-oriented operation surface explicit and shows why CNCF treats command execution as an async job-backed path by default.
