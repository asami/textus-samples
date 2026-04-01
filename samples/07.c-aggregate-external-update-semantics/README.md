# 07.c-aggregate-external-update-semantics

## Overview

This sample track is reserved for update-side semantics of:

- `aggregation + external`

The focus is not aggregate assembly itself.

The focus is how externally related structures participate in:

- behavior
- invariant / guard
- follow-up update
- event / compensation
- cascade delete

## Position

- `07-aggregate`
  - application-join aggregate
- `07.a-aggregate-single-record-lab`
  - single-record aggregate
- `07.b-aggregate-relation-boundary-model`
  - relation / boundary / join model
- `07.c-aggregate-external-update-semantics`
  - update semantics for `aggregation + external`

## Intended Direction

The intended example line is:

- root aggregate
- one `aggregation + external` related structure
- one `association + external` reference

The sample should show that `aggregation + external` is stronger than a plain association
on the update side.

## Current State

This is a preparatory slot only.

Implementation is not started yet.
