# 07.a-aggregate-single-record-lab implementation record

## Summary

`07.a` was changed from a same-JVM demo sample into a shell-first explanatory sample for the single-record aggregate pattern.

The sample now focuses on:

- the generated component surface
- the generated aggregate load surface
- the generated metadata shape
- the conceptual contrast with `09-aggregate`

## Relocation Rule

The following proofs are no longer part of the user-facing sample path:

- `toRecord -> createC` roundtrip
- datastore roundtrip for embedded value objects

Those proofs belong in `cozy` scripted because they are internal runtime assertions rather than the main shell-facing user flow.

The relocated proof now lives in:

- `cozy/src/sbt-test/cozy/aggregate-single-record-proof`

Verification result:

- `AGGREGATE_SINGLE_RECORD_PROOF_OK`
