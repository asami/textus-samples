# 02.c-crud-sqlite-lab rework checklist

## Goal

Rework `02.c-crud-sqlite-lab` so that:

- it stays user-facing and shell-first
- it explains SQLite as a persistence variation of the generated CRUD line
- it verifies persistence across separate commands through one SQLite file path

## Checklist

### A. Positioning

- [x] explain how `02.c` extends the earlier `02` CRUD line
- [x] explain that SQLite is the central point of the sample
- [x] explain that the sample keeps the generated CRUD surface

### B. Sample execution shape

- [x] run from the sample directory
- [x] use `bin/cncf`
- [x] keep the sample command-based
- [x] keep SQLite path reuse explicit across commands
- [x] keep `run.sh` as a batch wrapper around explicit shell commands

### C. README

- [x] add intended use case
- [x] add prepare step for `bin/setup cozy`
- [x] add “run the whole scenario”
- [x] add shell command walkthrough
- [x] explain parameters and command meaning
- [x] show expected output

### D. Verification

- [x] load help runs successfully
- [x] search help runs successfully
- [x] seeded load runs successfully
- [x] seeded search runs successfully
- [x] sync create plus later load runs successfully
- [x] metadata describe runs successfully
- [x] `run.sh` runs successfully

### E. Scripted relocation

- [x] add a `cozy` scripted fixture for the SQLite CRUD line
- [x] use direct `cozy.Cozy modeler-scala --save=out.d` in the fixture
- [x] compile the generated fixture successfully
- [x] assert seeded load / seeded search through the SQLite path
- [x] assert sync create plus later load through the same SQLite path
- [x] assert metadata describe

## Completion condition

- [x] `02.c` can be used directly as the SQLite-backed CRUD sample
- [x] the shell command flow is explicit and reproducible
- [x] the same SQLite verification line is covered by `cozy` scripted
