# 07.a-aggregate-single-record-lab Mini Low Instruction

## Goal

Polish the documentation around `07.a-aggregate-single-record-lab`.

This is a documentation-focused follow-up task.

The framework and sample first line are already working.

Do not redesign the sample.

## What 06.a Means

`07-aggregate` shows the application-join aggregate pattern:

- root entity and member entities are persisted separately
- the framework reconstructs the aggregate by joining persisted members

`07.a-aggregate-single-record-lab` shows the single-record aggregate pattern:

- `Order` is one persisted `Entity`
- `OrderLine` is a `Value Object`
- `OrderLine` is embedded inside `Order`
- the aggregate is restored from one record

## Scope

Allowed:

- README updates
- phase/checklist updates
- sample index / root README updates
- small wording alignment across docs

Not allowed:

- framework changes
- Cozy/CML changes
- generator changes
- runtime behavior changes
- new sample logic

## Files To Read First

- `/Users/asami/src/dev2026/cncf-samples/samples/09.a-aggregate-single-record-lab/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/09.a-aggregate-single-record-lab.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/30-06-a-aggregate-single-record-lab-implementation-record.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/09-aggregate/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/09-aggregate.md`
- `/Users/asami/src/dev2026/cncf-samples/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/README.md`

## Required Updates

1. Ensure `06.a` is clearly visible in the sample sequence.
2. Make the distinction between `07-aggregate` and `07.a-aggregate-single-record-lab` explicit.
3. Ensure the root sample index and phase index include `06.a`.
4. Keep the explanation short and concrete.
5. Only mark facts that are already verified.

## Verified Facts You May Rely On

- `sbt --batch clean compile` succeeds in `06.a`
- `bash run.sh` succeeds in `06.a`
- output shows:
  - one `Order` record
  - embedded `lines`
  - restored `lines`
  - `line-count = 2`

## Success Criteria

- `06.a` appears in the top-level sample structure
- readers can understand when to use `06` vs `06.a`
- no code or framework behavior changes are introduced

## Report Format

If successful, report only:

- files changed
- what documentation was clarified
- what index/listing was updated
- anything still unclear, if applicable

If blocked, report only:

- exact file or section that is unclear
- what information was missing
