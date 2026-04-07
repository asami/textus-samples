# Sample-First Rework Plan

## Goal

Rework the sample line so that each sample is directly useful to a user who wants to learn how to build with CNCF.

The core rule is:

- samples are for users
- internal assertions and runtime-behavior checks belong in framework specs or `cozy` scripted tests

## Background

Some current samples and demo programs are technically useful as tests, but they are not appropriate as user-facing samples.

Typical problems are:

- the sample relies on internal CNCF or Cozy APIs
- a Scala demo program constructs `Subsystem` or other runtime internals directly
- `run.sh` hides too much of the actual user operation flow
- the sample demonstrates framework internals rather than a practical usage pattern

## Fixed Design Policy

### Sample purpose

Each sample must provide information that a user can directly reuse for development.

Each sample should show:

- what is modeled in CML
- what minimal Scala customization is needed
- how CNCF is started
- how the resulting component or CAR is invoked from the shell

### Allowed implementation shape

Preferred order:

1. CML only
2. CML + minimal `ComponentFactory` override
3. CML + minimal supporting Scala code only when unavoidable

Disallowed as sample core shape:

- Scala demo code that programmatically constructs `Subsystem`
- Scala demo code that directly bootstraps runtime internals
- sample logic that depends on internal framework-only APIs

### Execution shape

The user-facing execution model should be shell-first.

The ideal shape is:

1. prepare generated component or CAR
2. start CNCF with it
3. invoke commands from shell

Execution guidance:

- when no state change is observed:
  - use `command`
- when state change must be observed across requests:
  - use `server` and `client`

### Documentation shape

Each sample must explain:

- the purpose of the sample
- the files that matter
- the exact shell commands to run
- the expected result

`run.sh` is not the primary explanation.
It is only the batch form of the documented shell procedure.

### Test relocation rule

If a current sample contains logic that is valuable mainly as a runtime assertion, move that logic to:

- `cozy` scripted
- framework spec
- plugin/spec verification

The sample should keep only the user-facing path.

## Rework Order

### Phase 1. Rules and pilot

1. define the sample-first rule set
2. create sample rework checklist template
3. apply the new rule set to `08.c`

### Phase 2. View line normalization

4. rework `08-view`
5. rework `08.a-view-definition-lab`
6. rework `08.b-simpleentity-view-lab`
7. rework `08.c-view-cache-lab`

Status:

- [x] `08-view` completed as the base shell-first view sample
- [x] `08.a-view-definition-lab` completed with matching `cozy` scripted verification
- [x] `08.b-simpleentity-view-lab` completed with matching `cozy` scripted verification
- [x] `08.c-view-cache-lab` completed as the UI-list/cache sample, with cache-proof logic moved to `cozy` scripted

### Phase 3. CRUD / operation line normalization

8. rework `02-crud`
9. rework `02.a-crud-seed-import-lab`
10. rework `02.b-simpleentity-crud-lab`
11. rework `02.c-crud-sqlite-lab`
12. rework `02.d-crud-server-memory-lab`
13. rework `02.e-crud-explicit-sync-lab`
14. rework `02.f-crud-nested-value-lab`
15. rework `03-operation`
16. rework `03.a-operation-command-lab`
17. rework `03.b-operation-entity-lab`

Status:

- [x] `02-crud` completed as the base shell-first CRUD surface sample
- [x] `02.a-crud-seed-import-lab` completed with matching `cozy` scripted verification
- [x] `02.b-simpleentity-crud-lab` completed with matching `cozy` scripted verification
- [x] `02.c-crud-sqlite-lab` completed as the SQLite-backed CRUD sample with matching `cozy` scripted verification
- [x] `02.d-crud-server-memory-lab` completed as the server/client memory-backed CRUD sample with matching `cozy` scripted verification
- [x] `02.e-crud-explicit-sync-lab` completed as the explicit sync CRUD sample with matching `cozy` scripted verification
- [x] `02.f-crud-nested-value-lab` completed as the nested-value CRUD sample with matching `cozy` scripted verification
- [x] `03-operation` completed as the base shell-first operation contract sample
- [x] `03.a-operation-command-lab` completed with matching `cozy` scripted verification, including job submit and await-result
- [x] `03.b-operation-entity-lab` completed with matching `cozy` scripted verification

### Phase 4. CQRS / event / job / aggregate normalization

18. rework `04-cqrs`
19. rework `04.a-designed-sync-command-lab`
20. rework `04.b-test-sync-command-lab`
21. rework `05-event-driven`
22. rework `05.a-event-job-trace-lab`
23. rework `05.b-event-job-server-client-lab`
24. rework `06-job`
25. rework `06.a-job-control-lab`
26. rework `06.b-job-control-demo-lab`
27. rework `07-aggregate`
28. rework `07.a-aggregate-single-record-lab`
29. rework `07.b-aggregate-relation-boundary-model`
30. rework `07.c-aggregate-external-update-semantics`

Status:

- [x] `04-cqrs` completed as the first explicit CQRS sample with matching `cozy` scripted verification
- [x] `04.a-designed-sync-command-lab` completed as the design-time sync counterpart with matching `cozy` scripted verification
- [x] `04.b-test-sync-command-lab` completed as the runtime test-sync counterpart with matching `cozy` scripted verification
- [x] `05-event-driven` completed as the base shell-first event surface sample, with the same-JVM effect proof moved to `cozy` scripted
- [x] `05.a-event-job-trace-lab` completed as the shell-first event/job trace bridge sample with matching `cozy` scripted verification
- [x] `05.b-event-job-server-client-lab` completed as the practical server/client event flow sample with matching `cozy` scripted verification
- [x] `06-job` completed as the base shell-first job-management sample with matching `cozy` scripted verification
- [x] `06.a-job-control-lab` completed as the shell-first job-control sample with matching `cozy` scripted verification
- [x] `06.b-job-control-demo-lab` completed by relocating the same-JVM direct-framework proof to `cozy` scripted
- [x] `07-aggregate` completed as the first shell-first aggregate sample, with the same-JVM proof moved to `cozy` scripted
- [x] `07.a-aggregate-single-record-lab` completed as the shell-first explanatory sample for the single-record aggregate pattern, with roundtrip proofs moved to `cozy` scripted
- [x] `07.b-aggregate-relation-boundary-model` completed as the shell-first explanatory sample for relation kind, boundary, and join semantics, with the same-JVM proof moved to `cozy` scripted

### Phase 5. Future lines

31. `09-subsystem`
32. `10-subsystem-wiring`
33. `101-distributed`

## Sample Rework Checklist

Use this checklist for each sample.

### A. Purpose

- [ ] the user-facing purpose is explicit
- [ ] the sample demonstrates one practical CNCF usage pattern
- [ ] the sample does not primarily demonstrate framework internals

### B. Modeling

- [ ] CML is the primary source
- [ ] Scala customization is minimal
- [ ] if Scala exists, it is limited to the smallest practical override
- [ ] `ComponentFactory` override is preferred over custom runtime bootstrapping

### C. Execution shape

- [ ] the sample can be explained as shell commands
- [ ] `command` is used for non-stateful observation
- [ ] `server/client` is used when state change must be observed
- [ ] the sample does not rely on a Scala demo main as the primary user path

### D. Documentation

- [ ] README explains purpose, files, commands, and expected result
- [ ] README shows shell commands explicitly
- [ ] `run.sh` is described as a convenience batch runner
- [ ] the shell procedure remains understandable without reading implementation code

### E. Internal leakage

- [ ] no direct `Subsystem` construction in the sample path
- [ ] no direct internal runtime wiring in the sample path
- [ ] no internal-only API usage in the user-facing path

### F. Test relocation

- [ ] internal assertions have been identified
- [ ] internal assertions are moved to `cozy` scripted or framework specs where appropriate
- [ ] the sample retains only the user-facing flow

### G. Verification

- [ ] documented commands were actually run
- [ ] `run.sh` matches the documented commands
- [ ] result output is appropriate for user comprehension

### H. Completion

- [ ] sample implementation updated
- [ ] README updated
- [ ] journal updated
- [ ] related test relocation completed
- [ ] commit completed with history-comment rule applied

## Immediate Target

The `08` view line pilot is complete.

The next active targets are the later sample families in:

- `07-*`
