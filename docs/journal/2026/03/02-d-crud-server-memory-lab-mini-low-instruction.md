# 02.d-crud-server-memory-lab Mini Low Instruction

## Goal

Implement a CRUD follow-up lab that demonstrates the same model-driven CRUD direction as `02-crud`, but through a local server/client shape with memory-backed runtime state.

## Read First

- [/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md](/Users/asami/src/dev2026/cncf-samples/samples/04-crud/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/07.b-event-job-server-client-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/07.b-event-job-server-client-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/08.a-job-control-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/08.a-job-control-lab/README.md)

## Intent

`02.d` is for runtime-shape variation.

It should show:

- the same model-driven CRUD shape
- a server startup path
- client-side CRUD access against memory-backed state

This lab is not for:

- SQLite or persistent backend behavior
- handwritten CRUD logic
- distributed deployment

## Required Outcome

Create `02.d-crud-server-memory-lab` that demonstrates:

1. one server startup path
2. one client-side CRUD create path
3. one client-side load or search confirmation
4. memory-backed runtime state

## Rules

- Keep the same Cozy/CML-driven direction as `02-crud`
- Do not add handwritten repository logic
- Do not introduce SQLite here
- Keep custom code thin
- Reuse existing CNCF server/client execution paths

## Stop Conditions

Stop immediately if any of these becomes necessary:

- major CNCF server/client redesign
- new Cozy/CML language features
- custom handwritten persistence layer
- broad framework work beyond sample wiring

If blocked, report only:

- the exact missing capability
- the exact file or command where it blocked
- which files were changed before stopping

## Minimum Verification

At minimum, confirm:

- the server starts
- the client can invoke one CRUD create-style operation
- the client can confirm the resulting state with load or search
- README explains that server + memory is the point of this lab

## Report Back Only

- what files you changed
- what server command succeeded
- what client commands succeeded
- what proves the memory-backed CRUD behavior
- what remains unfinished, if anything
