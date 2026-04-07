# 06.a-job-control-lab Mini-Low Instruction

Read first:

- /Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/05-a-job-control-lab-development-instruction.md
- /Users/asami/src/dev2026/cncf-samples/docs/phase/samples/08.a-job-control-lab.md
- /Users/asami/src/dev2026/cncf-samples/samples/08-job/README.md
- /Users/asami/src/dev2026/cncf-samples/docs/phase/samples/08-job.md

## Goal

Implement the first minimal `06.a-job-control-lab`.

This lab must demonstrate:

- one submitted job
- one cancel route
- one suspend route
- one resume route
- one visible observation route that shows the control effect

## Scope

Keep the lab small and local.

Use the existing CNCF job-control surface if it already exists.

Prefer the same direction as `06-job`:

- command-first
- local runtime
- minimal demo runner only if necessary

## Required Deliverables

Create or update:

- `/Users/asami/src/dev2026/cncf-samples/samples/08.a-job-control-lab/README.md`
- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/08.a-job-control-lab.md`

Add an implementation record only if real implementation progress happens.

## Minimum Verification

Confirm all of these with facts:

1. one job can be submitted
2. cancel changes the observable status or timeline
3. suspend changes the observable status or timeline
4. resume changes the observable status or timeline

## Do Not

- Do not add large custom sample code.
- Do not create a handwritten framework-like control layer inside the sample.
- Do not redesign CNCF job control.
- Do not improve Cozy/CML for this task.
- Do not add new model directives for this task.

## Stop Rule

Stop immediately if any of these becomes necessary:

- custom code creation beyond a thin sample runner
- major CNCF modification
- Cozy/CML improvement or extension

If blocked, report only:

- the exact missing capability
- the exact command or path where it blocked
- which files were changed before stopping

## Completion Rule

Do not mark `06.a-job-control-lab` as `DONE` unless cancel, suspend, resume, and observation are all actually confirmed.
