# 06.b Aggregate Relation / Boundary / Join Background

- date: 2026-03-31
- status: note

## Purpose

This note records why `07.b-aggregate-relation-boundary-model` was introduced.

The trigger was a need to model an `Order` aggregate that contains multiple
kinds of assembled data with different meanings.

## Example

- `OrderLine`
  - embedded value object
- `ShipmentOrder`
  - external entity
- `User`
  - associated external entity

The important point is that `ShipmentOrder` and `User` are both external,
but they are not the same kind of relationship.

## Working Model

The discussion converged on three axes:

- relation
  - `composition`
  - `aggregation`
  - `association`
- boundary
  - `internal`
  - `external`
- join strategy
  - `direct`
  - `reverse`
  - `through`

Current mapping used by `06.b`:

- `OrderLine`
  - `composition + internal`
- `ShipmentOrder`
  - `aggregation + external`
- `User`
  - `association + external`

## Why `06.b` Exists

`07-aggregate` shows application-join aggregate assembly.

`07.a-aggregate-single-record-lab` shows a single-record aggregate with
embedded value objects.

`07.b-aggregate-relation-boundary-model` exists to show that aggregate
assembly also needs an explicit model for:

- structural relation
- transaction boundary
- join direction

## Current First Line

The current first line in `06.b` confirms:

- aggregate `load`
- aggregate `search`
- embedded `OrderLine`
- external `ShipmentOrder`
- external `User`
- structured JSON output

## Reference

The corresponding CNCF design notes are:

- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/aggregate-relation-boundary-join-model-note.md`
- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/notes/aggregate-relation-boundary-join-model.md`
