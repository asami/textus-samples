# 07.b-aggregate-relation-boundary-model Mini Low Instruction

## Goal

Prepare the documentation groundwork for the next aggregate model extension.

This is a documentation-only task.

The implementation of the mechanism itself will stay in the main thread.

## Background

Current aggregate samples already cover:

- `07-aggregate`
  - application-join aggregate
- `07.a-aggregate-single-record-lab`
  - single-record aggregate with embedded value objects

The next line wants to distinguish two independent dimensions:

- relation
  - `composition`
  - `aggregation`
  - `association`
- boundary
  - `internal`
  - `external`

This separation is needed because:

- `OrderLine` can be modeled as `composition + internal`
- `User` can be modeled as `association + external`
- `ShipmentOrder` can be modeled as `aggregation + external`

`ShipmentOrder` is structurally stronger than a plain association, but it is not inside the DDD aggregate transaction boundary.

## Scope

Allowed:

- documentation groundwork
- README placeholder updates
- phase/checklist placeholder updates
- implementation-plan placeholder updates
- sample sequence/index updates if needed

Not allowed:

- framework changes
- Cozy/CML changes
- generator changes
- runtime changes
- parser/model changes
- new executable sample logic

## Files To Read First

- `/Users/asami/src/dev2026/cncf-samples/samples/09-aggregate/README.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/09.a-aggregate-single-record-lab/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/09-aggregate.md`
- `/Users/asami/src/dev2026/cncf-samples/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/README.md`

## What To Prepare

Prepare a documentation slot for the next aggregate extension line.

The documentation should make clear:

1. relation and boundary are different axes
2. `aggregation + external` is a valid target concept
3. this is different from:
   - embedded value objects in `06.a`
   - application-join internal aggregate members in `06`

## Recommended Example Story

- `Order`
- `OrderLine`
- `ShipmentOrder`
- `User`

Expected conceptual mapping:

- `OrderLine`
  - `composition + internal`
- `ShipmentOrder`
  - `aggregation + external`
- `User`
  - `association + external`

## Suggested Documentation Output

If helpful, create placeholders for:

- a future `06.b` sample README
- a future phase checklist entry
- a short implementation-plan note

Keep them clearly marked as preparatory, not implemented.

## Success Criteria

- readers can understand the new two-axis model
- `ShipmentOrder` and `User` are clearly distinguished
- no behavior is claimed as implemented unless already verified

## Report Format

If successful, report only:

- files changed
- what documentation groundwork was added
- how the new model is explained
- anything still unclear

If blocked, report only:

- exact file or section that is unclear
- what information was missing
