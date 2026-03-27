# CML Operation `IMPLEMENTATION = blocking-task` Direction

## Intent

`05.a-job-control-lab` needs a controllable long-running job without falling back to
sample-local handwritten `JobTask` code.

The right place for that typical behavior is the `IMPLEMENTATION` directive.

`blocking-task` is the minimal pattern for:

- creating a job-backed command from a generated operation
- keeping the job alive long enough for cancel / suspend / resume
- avoiding handwritten task construction in the sample

## Position

- `echo-record`
  - returns quickly
  - useful for job submission demos
- `blocking-task`
  - stays running for a controllable amount of time
  - useful for job control demos

## Meaning

`IMPLEMENTATION = blocking-task` means:

- the operation is still modeled as a normal command
- the generated action body delegates to a standard runtime task pattern
- the command should run long enough that job control can observe and affect it

It is not:

- a business-domain implementation
- a distributed scheduler feature
- a sample-local workaround

## Expected Runtime Shape

Typical flow:

1. generated command is invoked
2. the command becomes a job-backed execution
3. the task remains running for a bounded interval
4. job control can issue cancel / suspend / resume while it is active

## Minimal Behavior

The first version should be deliberately small.

It should support:

- a command that stays running for a short fixed interval
- optional request-driven duration if the runtime already supports that cleanly
- a normal success result when not cancelled

The first version does not need:

- arbitrary scripting
- domain-specific business logic
- distributed execution

## Observation Goal

`blocking-task` exists so that a later sample or lab can confirm:

- submitted
- running
- suspended
- resumed
- cancelled
- succeeded

through job status and/or timeline.

## Responsibility Split

- CML
  - selects `blocking-task`
- generator
  - emits the action body or metadata needed for the runtime path
- CNCF runtime
  - executes the standard blocking task pattern
- sample
  - only calls the generated command and job control/query routes

## Relationship To Job Services

This directive supports the future split between:

- application-facing job service
  - submit, own-job status/result
- admin-facing job service
  - cancel, suspend, resume, timeline/debug

`blocking-task` is primarily for the admin-facing job-control lab.
