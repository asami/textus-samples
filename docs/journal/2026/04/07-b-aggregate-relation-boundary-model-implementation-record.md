# 07.b-aggregate-relation-boundary-model implementation record

## Summary

`07.b` was changed from a same-JVM demo sample into a shell-first explanatory sample for the relation/boundary/join model around aggregate assembly.

The sample now focuses on:

- the generated component surface
- the generated aggregate service surface
- the generated metadata shape
- the conceptual distinction between relation kind, boundary, and join

## Relocation Rule

The same-JVM proof that assembles one aggregate from:

- embedded internal members
- reverse-joined external related records
- direct-joined external associated records

is no longer part of the user-facing sample path.

That proof belongs in `cozy` scripted because it is an internal runtime assertion rather than the main shell-facing user flow.

The relocated proof now lives in:

- `cozy/src/sbt-test/cozy/aggregate-relation-boundary-proof`

Verification result:

- `AGGREGATE_RELATION_BOUNDARY_PROOF_OK`
