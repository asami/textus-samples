# 02.c-crud-sqlite-lab Mini Low Instruction

## Goal

Implement a CRUD follow-up lab that demonstrates the same model-driven CRUD direction as `02-crud`, but with SQLite-backed persistence.

## Read First

- [/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/README.md)

## Intent

`02.c` is for persistence variation.

It should show:

- the same model-driven CRUD shape
- the same generated/entity/aggregate/view surfaces where relevant
- SQLite as the backing store

This lab is not for:

- server/client runtime shape
- direct repository coding
- handwritten CRUD logic

## Required Outcome

Create `02.c-crud-sqlite-lab` that demonstrates:

1. model-driven CRUD setup
2. SQLite-backed persistence
3. at least one create/load/search-style confirmation against SQLite-backed data

## Rules

- Keep the same Cozy/CML-driven direction as `02-crud`
- Do not add handwritten repository logic
- Do not turn this into a database tuning lab
- Do not introduce a server/client split here
- Prefer existing CNCF datastore/persistence capabilities

## Stop Conditions

Stop immediately if any of these becomes necessary:

- major CNCF datastore redesign
- new Cozy/CML language features
- custom handwritten persistence layer
- broad SQLite-specific framework work beyond sample wiring

If blocked, report only:

- the exact missing capability
- the exact file or command where it blocked
- which files were changed before stopping

## Minimum Verification

At minimum, confirm:

- build succeeds
- CRUD help paths resolve
- one create-style operation succeeds
- one load or search-style operation succeeds
- README explains that SQLite is the point of this lab

## Report Back Only

- what files you changed
- what SQLite-backed path is used
- what runtime commands succeeded
- what remains unfinished, if anything
