# 02-crud Runtime Gap Mini-Low Instruction

Status: `Active Instruction`

Reviewed and updated on 2026-03-26.

## Purpose

Resolve the current blocker for `02-crud`.

The blocker is no longer CML recognition.
`sbt cozyGenerate` already succeeds.
The remaining problem is that generated Scala code does not compile against the current runtime dependencies.

## Read First

Read these files before making changes:

1. `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md`
2. `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/src/main/cozy/crud.cml`
3. `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/build.sbt`
4. `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/02-crud.md`
5. `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/26-02-crud-redesign-record.md`
6. `/Users/asami/src/dev2026/textus-user-account/src/main/cozy/user-account.cml`
7. `/Users/asami/src/dev2026/textus-user-account/build.sbt`

Then inspect the generated code under:

- `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/target/scala-3.3.7/src_managed/main`

Then inspect the runtime source in:

- `/Users/asami/src/dev2025/cloud-native-component-framework`

## Important Rules

- `02-crud` must stay aligned with the `textus-user-account` method.
- Do not restore hand-written CRUD repository logic.
- Do not replace the Dox-style CML with a compact DSL.
- Do not mark the phase checklist `DONE` unless compile and runtime confirmation are actually verified.
- Record facts only. Do not claim success for compile/runtime unless the commands really succeed.

## Task

1. Run `sbt compile` in `samples/02-crud` and capture the missing types exactly.
2. Find where the generated code refers to `EntityPersistable*` and `EntityPersistent*`.
3. Search `/Users/asami/src/dev2025/cloud-native-component-framework` to determine whether those types:
   - already exist under another package or name
   - were renamed
   - are missing from the runtime
4. Compare the result with the generated code pattern used by `textus-user-account`, if any generated output is available there.
5. Decide which side is wrong:
   - generated code expectation
   - runtime API
   - dependency/version mismatch
6. Apply the minimum fix needed to make `sbt compile` succeed.
7. If compile succeeds, identify the runnable CRUD surface and update the docs honestly.

## Report Updates

Update these files:

- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/26-02-crud-redesign-record.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/02-crud.md`

Only update the checklist for items that were actually confirmed.

## Done Condition

You may treat this task as done only if all of the following are true:

- `sbt cozyGenerate` succeeds
- `sbt compile` succeeds
- the generated CRUD surface is described in the redesign record
- the phase checklist is updated honestly

If `sbt compile` still fails, stop after documenting:

- the exact missing types
- where they are referenced
- whether the mismatch belongs to generator or runtime
