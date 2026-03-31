# 06.c Aggregate External Update Semantics Plan

- date: 2026-03-31
- status: draft

## Purpose

Prepare the next aggregate line after `06.b`.

`06.b` established:

- relation
- boundary
- join strategy

and demonstrated read-side aggregate assembly.

`06.c` is intended to cover update-side semantics for:

- `aggregation + external`

## Main Question

How should an external aggregated structure participate in update behavior
without being collapsed into the same strict aggregate transaction boundary?

## Intended Topics

- behavior-side access
- invariant / guard access
- follow-up update
- event / compensation
- cascade delete
- same-subsystem transaction vs cross-subsystem saga

## Current Status

Only the documentation slot is prepared.

Implementation work has not started yet.
