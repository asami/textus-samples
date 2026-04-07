# 07.a-aggregate-single-record-lab Test Mini Low Instruction

## Goal

Verify the already-implemented `07.a-aggregate-single-record-lab`.

This is a test-and-documentation task.

The purpose is to confirm the current first line with actual commands and to update docs only for facts that are directly verified.

## What 06.a Means

`07.a-aggregate-single-record-lab` shows the single-record aggregate pattern:

- `Order` is one persisted `Entity`
- `OrderLine` is a `Value Object`
- `OrderLine` is embedded inside `Order`
- the aggregate is restored from one record

This is different from `07-aggregate`, which shows the application-join aggregate pattern.

## Scope

Allowed:

- run verification commands
- update README if needed to match actual output
- update phase/checklist only for verified facts
- update implementation record with actual verification results

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

## Required Verification

1. Run:

```bash
sbt --batch clean compile
```

in:

`/Users/asami/src/dev2026/cncf-samples/samples/09.a-aggregate-single-record-lab`

2. Run:

```bash
bash run.sh
```

3. If present and already part of the sample, also run:

```bash
bash run-datastore.sh
```

4. Confirm only facts that are actually seen in output.

## Facts To Confirm If Observed

- one `Order` record is used
- embedded `lines` are present in the record
- restored `lines` are present after reconstruction
- `line-count = 2`
- if datastore demo exists:
  - saved record contains embedded `lines`
  - loaded record contains embedded `lines`

## Required Updates

1. Keep README short and aligned to actual command results.
2. Update phase/checklist only for facts directly confirmed.
3. Add concrete verification notes to the implementation record.
4. Do not add claims about CRUD/help/server/client paths unless actually verified.

## Success Criteria

- `06.a` build succeeds
- `run.sh` succeeds
- docs match actual output
- no code or framework behavior changes are introduced

## Report Format

If successful, report only:

- files changed
- commands that succeeded
- what facts were confirmed
- whether anything remains unclear

If blocked, report only:

- exact command that failed
- exact error or mismatch
- which files were changed before stopping
