# 07.c-aggregate-external-update-semantics implementation record

## Summary

`07.c` was changed from a same-JVM demo sample into a shell-first explanatory sample for aggregate-facing external update semantics.

The sample now focuses on:

- the generated component surface
- the generated aggregate-facing `cancelOrder` command
- the generated metadata shape
- the conceptual distinction between external aggregated follow-up updates and plain external associations

## Relocation Rule

The same-JVM proof that `cancelOrder` updates:

- the root `Order`
- the external aggregated `ShipmentOrder`
- but not the associated `User`

is no longer part of the user-facing sample path.

That proof belongs in `cozy` scripted because it is an internal runtime assertion rather than the main shell-facing user flow.

The relocated proof now lives in:

- `cozy/src/sbt-test/cozy/aggregate-external-update-proof`

Verification result:

- `AGGREGATE_EXTERNAL_UPDATE_PROOF_OK`
