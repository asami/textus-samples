# 06.b-job-control-demo-lab Implementation Record

## Summary

`08.b-job-control-demo-lab` originally exposed a direct-framework same-JVM demo through `JobControlDemo.scala`.

That demo used internal CNCF runtime APIs directly:

- `DefaultSubsystemFactory`
- `component.logic.submitJob(...)`
- `component.logic.controlJob(...)`
- `jobEngine.query(...)`
- `jobEngine.queryTimeline(...)`
- `eventStore.query(...)`

This is useful as runtime verification, but it is not appropriate as the user-facing sample path.

## Rework Decision

The direct-framework proof was removed from the sample path and is being relocated to `cozy` scripted.

The sample now keeps only the shell-first inspection surface:

- component help
- operation help
- metadata describe

That keeps `06.b` aligned with the sample-first rule:

- samples are for users
- internal runtime assertions belong in `cozy` scripted

## Current Position

- `08-job`
  - job observation sample
- `08.a-job-control-lab`
  - user-facing job control sample
- `08.b-job-control-demo-lab`
  - relocation point for the lower-level framework proof

## Scripted Relocation

The relocated direct-framework proof now runs in `cozy` scripted.

The scripted fixture verifies:

- direct same-JVM bootstrap
- submit
- suspend and resume
- cancel
- direct history inspection
- direct event-store inspection

Result:

- `JOB_CONTROL_DEMO_PROOF_OK`
