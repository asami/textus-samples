# 04.a-designed-sync-command-lab Mini-Low Instruction

Status: `Active Instruction`

This file is the handoff instruction for a smaller model.
Do not rewrite this file into a result note or completion report.

The status authority is:

- [`docs/phase/samples/04.a-designed-sync-command-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04.a-designed-sync-command-lab.md)

## Read First

Read these files first:

- [`docs/journal/2026/03/03-a-designed-sync-command-lab-development-instruction.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/03-a-designed-sync-command-lab-development-instruction.md)
- [`docs/phase/samples/04-cqrs.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04-cqrs.md)
- [`samples/04-cqrs/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/04-cqrs/README.md)
- [`samples/04-cqrs/src/main/cozy/cqrs.cml`](/Users/asami/src/dev2026/cncf-samples/samples/04-cqrs/src/main/cozy/cqrs.cml)

## Goal

Create `04.a-designed-sync-command-lab` as a follow-up to `04-cqrs`.

The lab must show one command that is synchronous by design.

This is not a test-only override lab.

## Required Outcome

You must produce all of the following:

1. a new sample directory for `04.a-designed-sync-command-lab`
2. a phase checklist for the new lab
3. one designed-sync command target
4. runtime confirmation that the command returns an immediate result
5. README text that explains this is design-time sync

## Fixed Rules

- Use the same model-driven Cozy/CML direction as `04-cqrs`.
- Put the sync behavior in CML/model metadata, not only in hand-written Scala.
- Keep the entity name as `Item` unless the codebase proves a different name is required.
- Keep the lab separate from test-only sync execution.
- Use `CncfMain --discover=classes` for runtime checks unless the codebase proves another path is required.
- If framework/runtime parameters are needed, use `cncf.*`.
- If query control parameters are needed, use `query.*`.
- Leave unprefixed parameters for domain attributes.

## Work Steps

Follow these steps in order:

1. read the files listed above
2. create the new sample directory and checklist
3. copy only the minimum structure needed from `04-cqrs`
4. change the command side so one command is synchronous by design in the model
5. verify help and runtime behavior
6. update the README to match actual commands
7. update the phase checklist only for items actually confirmed

## Minimum Runtime Checks

At minimum, verify:

- build succeeds
- help for the component succeeds
- help for the designed-sync command target succeeds
- the command executes
- the command returns an immediate result instead of a job id

## Do Not

- Do not implement test-only sync override here.
- Do not introduce config-based async-to-sync switching here.
- Do not turn this into a generic admin command sample.
- Do not finish with only a hand-written Scala sync hook.
- Do not mark the checklist `DONE` unless the immediate command result is actually confirmed.

## Report Back

Report only:

- what files you changed
- what command target was made synchronous by design
- what runtime result proves it is synchronous
- what checks succeeded
- what remains unfinished, if anything
