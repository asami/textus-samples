# 02-crud Runtime Verification Mini-Low Instruction

Status: `Active Instruction`

Reviewed and updated on 2026-03-26.

## Purpose

Finish `02-crud` after the generator/import fix.

The previous blocker was compile failure.
That part has already been resolved locally.
The next task is to verify the generated/runtime CRUD surface honestly and then update the sample records.

## Read First

Read these files before running anything:

1. `/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md`
2. `/Users/asami/src/dev2026/cncf-samples/samples/04-crud/src/main/cozy/crud.cml`
3. `/Users/asami/src/dev2026/cncf-samples/samples/04-crud/build.sbt`
4. `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md`
5. `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/26-02-crud-redesign-record.md`

## Important Notes

- The old compact CML form was wrong. Do not restore it.
- The sample must stay aligned with `textus-user-account`.
- Do not add hand-written CRUD repository logic.
- Do not add local workaround files under `src/main/scala/org/sample/...` again unless absolutely required.
- The redesign record currently contains stale compile-failure notes. Re-check the current state before editing it.
- Do not mark the phase checklist `DONE` unless runtime CRUD confirmation is actually verified.

## Task

Run these steps in order.

1. Run `sbt clean compile` in `/Users/asami/src/dev2026/cncf-samples/samples/04-crud`.
2. Confirm that generation still succeeds and compile still succeeds.
3. Inspect the generated `CrudComponent.scala` and list the available CRUD surfaces:
   - `ItemService`
   - `AggregateService`
   - `ViewService`
   - `entity` surface
4. Determine the simplest real runtime verification path for this sample.
   - Prefer CNCF `command`-style verification if there is a runnable surface.
   - If runtime invocation is still blocked, document exactly what is missing.
5. Update the redesign record with facts only.
6. Update the phase checklist only for items that were actually confirmed.

## Report Updates

Update these files:

- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/26-02-crud-redesign-record.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-crud.md`

## Done Condition

You may treat this task as done only if all of the following are true:

- `sbt clean compile` succeeds
- the available generated CRUD surfaces are recorded
- a runtime CRUD confirmation path is actually verified, or its exact blocker is documented
- the phase checklist reflects the real state
