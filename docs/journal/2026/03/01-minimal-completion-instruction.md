# 01-minimal Completion Instruction

Status: `Active Instruction`

This is the active work-order document for `01-minimal`.
Do not rewrite this file into a result note or completion report.
If completion is achieved, record the result by appending to an implementation record or by creating a separate completion record file.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md)

## Purpose

Execute the remaining work for `01-minimal` and bring the sample to an honestly verifiable completion state.

The sample keeps the intended runtime contract:

- Scala implementation class: `MinimalComponent`
- CNCF component name: `minimal`
- command path: `minimal.main.hello`

The remaining work is limited to:

- confirming the development-time execution path in the checklist
- confirming and documenting the visible output contract in the checklist and README

## Work Order

1. Verify development-time execution from the sample directory:

```bash
cd samples/01-minimal
./run.sh
```

2. Verify deployment-style execution from the sample directory:

```bash
cd samples/01-minimal
./invoke.sh
```

3. Record the actual visible output of both commands.
4. Compare that output against the sample requirement `Hello CNCF`.
5. If the runtime behavior and the sample contract do not match, do one of the following:
   - adjust runtime behavior
   - adjust the documented sample contract with explicit rationale
6. Update the following only after the behavior and decision are confirmed:
   - `docs/phase/samples/01-minimal.md`
   - `samples/01-minimal/README.md`
   - relevant journal records when the decision materially changes the documented sample contract

## Acceptance Criteria

`01-minimal` is complete only when all of the following are true:

- `./run.sh` succeeds in development-time execution
- `./invoke.sh` succeeds
- `minimal.main.hello` resolves consistently in both modes
- the visible output contract is verified and documented
- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md) is honestly marked `DONE`

## Related Documents

- [`docs/phase/samples/01-minimal.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01-minimal.md)
- [`docs/journal/2026/03/26-01-minimal-implementation-record.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/26-01-minimal-implementation-record.md)
