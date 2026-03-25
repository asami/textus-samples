# CNCF Samples Foundation Plan

## Phase 1. Repository Foundation

Purpose:

- Establish a repository foundation that can absorb samples continuously
- Fix the AI directive, documentation structure, and standard sample layout
- Define progress judgment criteria before implementation starts

Stage Status:
- Current status: `IN_PROGRESS`
- Owner: `Codex + human`
- Update rule: `Checklist state changes or phase closure decisions must update this block immediately.`

### Subphase 1.1 Layout And Governance

Focus:

- Root structure
- AI entry points
- Sample placement conventions

Step:

- Create the fixed repository structure and establish clear entry points for future sample work

Checklist:

- [x] `ai/directive` is configured as a submodule
- [x] Root `AGENT.md` and `RULE.md` are exposed as symlinks to `ai/directive/core`
- [x] Root `README.md` explains the project purpose, AI directive, and sample layout
- [x] `docs/architecture`, `docs/patterns`, `docs/rules`, `docs/spec`, `docs/design`, `docs/notes`, and `docs/phase` are present
- [x] Standard layout scaffolds exist from `samples/01-minimal` through `samples/101-distributed`
- [x] The roles of `shared/common-lib` and `shared/test-utils` are defined

### Subphase 1.2 Execution Baseline

Focus:

- Minimum build prerequisites
- Per-sample independence
- Validation criteria for later work

Step:

- Document the assumptions for independent build/run per sample and fix the validation viewpoint

Checklist:

- [x] Root `build.sbt` and `project/build.properties` exist
- [x] Each sample has its own `build.sbt`
- [x] Each sample has its own `project/build.properties`
- [x] `04-event-driven` and later samples have a `docker/` location
- [ ] The CLI execution path and expected output for `01-minimal` have been verified against the implementation
- [x] Exit criteria for each sample are explicitly defined in per-sample checklist documents under `docs/phase/samples`

### Phase 1 Exit Criteria

Checklist:

- [x] The foundation directory structure is fixed
- [x] The AI directive reference model is fixed
- [x] The standard sample layout is fixed
- [ ] At least `01-minimal` satisfies build success, CLI success, and README completion

## Phase 2. Sample Delivery

Purpose:

- Implement the samples in definition order and make each pattern executable

Stage Status:
- Current status: `OPEN`
- Owner: `Codex + human`
- Update rule: `When a sample checklist item changes, update the corresponding subphase and phase status.`

### Subphase 2.1 01-minimal

Reference:

- `docs/phase/samples/01-minimal.md`

### Subphase 2.2 02-crud

Reference:

- `docs/phase/samples/02-crud.md`

### Subphase 2.3 03-cqrs

Reference:

- `docs/phase/samples/03-cqrs.md`

### Subphase 2.4 04-event-driven

Reference:

- `docs/phase/samples/04-event-driven.md`

### Subphase 2.5 05-job

Reference:

- `docs/phase/samples/05-job.md`

### Subphase 2.6 06-subsystem

Reference:

- `docs/phase/samples/06-subsystem.md`

### Subphase 2.7 07-subsystem-wiring

Reference:

- `docs/phase/samples/07-subsystem-wiring.md`

### Subphase 2.8 101-distributed

Reference:

- `docs/phase/samples/101-distributed.md`

### Phase 2 Exit Criteria

Checklist:

- [ ] `01-minimal` through `101-distributed` are completed in order
- [ ] Each sample satisfies independently buildable and executable
- [ ] No sample violates the reverse-dependency prohibition
- [ ] Each sample README satisfies Overview, Structure, How to Run, Example Commands, and Key Learnings

## Phase 3. Cross-Sample Quality

Purpose:

- Establish repository-wide quality so the sample set is comparable and reusable

Stage Status:
- Current status: `OPEN`
- Owner: `Codex + human`
- Update rule: `Update when shared validation or repository-wide consistency work changes state.`

### Subphase 3.1 Consistency Audit

Focus:

- Naming
- Execution style
- Documentation consistency

Step:

- Align explanations, commands, and structural granularity across samples

Checklist:

- [ ] Command naming is consistent across samples
- [ ] README section structure is consistent across samples
- [ ] Cross-sample comparison viewpoints are organized in `docs/patterns`
- [ ] The overall repository structure is organized in `docs/architecture`

### Subphase 3.2 Verification Support

Focus:

- Reproducible execution checks
- Helper scripts

Step:

- Organize sample verification so it does not rely excessively on manual checking

Checklist:

- [ ] Verification commands are documented per sample
- [ ] Required helper scripts or test utilities are organized under `shared/test-utils`
- [ ] Evidence remains for build success, CLI success, and expected output confirmation

### Phase 3 Exit Criteria

Checklist:

- [ ] Comparison viewpoints for the sample set are documented
- [ ] Reproducibility of execution checks is secured
- [ ] Repository-wide documentation and implementation are aligned
