# `blocking-task` Implementation Mini-Low Instruction

Read first:

- /Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/cml-operation-implementation-directive-direction.md
- /Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/cml-operation-implementation-blocking-task-direction.md
- /Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/05-a-job-control-lab-development-instruction.md
- /Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/05-a-job-control-lab-mini-low-instruction.md
- /Users/asami/src/dev2026/cncf-samples/samples/05-job/README.md

## Goal

Implement the first standard `IMPLEMENTATION = blocking-task` path.

This work is for generator/runtime support, not for finishing `05.a` itself.

## Required Outcome

Support this new implementation kind:

- `blocking-task`

The result must make it possible for a generated command to stay alive long enough
for job control to observe and act on it.

## Scope

Keep the first version minimal.

Prefer:

- metadata propagation through the existing `IMPLEMENTATION` path
- a standard runtime pattern
- no business-domain logic

## Allowed Work

- parser/model metadata propagation if needed
- generator support for `blocking-task`
- runtime support needed to execute the generated pattern
- small focused tests

## Do Not

- Do not redesign all job control
- Do not add distributed infrastructure
- Do not build a general scheduler
- Do not add a large handwritten sample workaround
- Do not finish `05.a` in the same task unless the support is already naturally complete

## Stop Rule

Stop if this would require:

- a major CNCF redesign
- a large new custom execution framework
- a broad Cozy/CML grammar redesign beyond one implementation kind

If blocked, report only:

- the exact missing capability
- the exact file or command where it blocked
- which files were changed before stopping

## Minimum Verification

Confirm with facts:

1. `blocking-task` is accepted as an implementation directive
2. generated output no longer falls back to `uowmNotImplemented` for that operation
3. a generated command using `blocking-task` can create a running job
4. the running job remains alive long enough to be observable by job control

## Report Back Only

- what files you changed
- where `blocking-task` is interpreted
- what verification succeeded
- what remains unfinished, if anything
