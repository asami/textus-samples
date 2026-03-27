# 03.b-test-sync-command-lab Mini-Low Instruction

Status: `Active Instruction`

This instruction is written for a smaller coding model.

Follow it exactly.
Do not expand the scope.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/03.b-test-sync-command-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/03.b-test-sync-command-lab.md)

## Read First

Read these files before editing anything:

- [`docs/journal/2026/03/03-b-test-sync-command-lab-development-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/03-b-test-sync-command-lab-development-instruction.md)
- [`docs/phase/samples/03.b-test-sync-command-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/03.b-test-sync-command-lab.md)
- [`docs/phase/samples/03-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/03-cqrs.md)
- [`docs/phase/samples/03.a-designed-sync-command-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/03.a-designed-sync-command-lab.md)
- [`samples/03-cqrs/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/03-cqrs/README.md)
- [`samples/03.a-designed-sync-command-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/03.a-designed-sync-command-lab/README.md)

## Goal

Implement `03.b-test-sync-command-lab` as a follow-up to `03-cqrs`.

The lab must show:

- one command that is async/job-backed by default
- the same command can run synchronously for test/local/debug use
- the synchronous behavior comes from runtime override, not from CML design-time sync

## Required Work

Do only these steps:

1. create the sample directory and README
2. start from the same model-driven direction as `03-cqrs`
3. keep the command default behavior async/job-backed
4. add one explicit test/local sync execution path
5. document both command results in the README
6. update the phase checklist only for checks you actually verified

## Do Not

Do not do any of these:

- do not use `EXECUTION=sync` in CML
- do not copy the designed-sync solution from `03.a`
- do not redesign the command as synchronous by contract
- do not expand into event sourcing or distributed topics
- do not mark the phase `DONE` unless both runtime shapes are actually confirmed

## Minimum Runtime Checks

Minimum acceptable runtime confirmation:

- build succeeds
- help for the component succeeds
- help for the command target succeeds
- default execution returns a job-shaped result
- overridden execution returns an immediate result

## Report Format

When the work is done, report only:

- what files changed
- what command target is used
- what proves the default path is async/job-backed
- what proves the override path is synchronous
- what remains unfinished, if anything
