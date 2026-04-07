# cncf-samples Full Verification Record

## Summary

Execution date:

- Apr. 3, 2026

Target:

- `/Users/asami/src/dev2026/cncf-samples/samples`

Policy:

- Prefer `run.sh`
- If `run.sh` is missing, use `run-*.sh`
- If those are also missing, use `invoke.sh`
- If no execution path exists, record `execution path missing`

Excluded from failure handling:

- `11-subsystem`
- `12-subsystem-wiring`
- `101-distributed`

These three are currently unimplemented samples, so they are treated as not-started rather than as failures.

## Result

Totals:

- total checked: 33
- passed: 28
- failed: 1
- blocked: 0
- execution path missing: 1

Handled as not-started:

- `09.c-aggregate-external-update-semantics`
- `11-subsystem`
- `12-subsystem-wiring`
- `101-distributed`

## Per Sample

`01-minimal | passed | run.sh | [success] Total time: 3 s, completed 2026/04/03 15:48:44 | 9s`

`01.a-invocation-source-lab | passed | run.sh | [success] Total time: 7 s, completed 2026/04/03 15:48:58 | 13s`

`01.b-startup-shapes-lab | passed | run.sh | [success] Total time: 5 s, completed 2026/04/03 15:49:10 | 12s`

`01.c-builtin-and-help-lab | passed | run.sh | [success] Total time: 4 s, completed 2026/04/03 15:49:44 | 11s`

`01.d-component-script | passed | run.sh | Hello CNCF | 5s`

`02-crud | passed | sbt runMain help crud | component help resolved after shared CNCF version fix | standard entrypoint is sample-local sbt runMain`

`02.a-crud-seed-import-lab | passed | sbt runMain help crud | component help resolved | standard entrypoint is sample-local sbt runMain`

`02.b-simpleentity-crud-lab | passed | sbt runMain help simple-entity-crud-lab | component help resolved | standard entrypoint is sample-local sbt runMain`

`02.c-crud-sqlite-lab | passed | run.sh | [success] Total time: 5 s, completed 2026/04/03 15:50:03 | 13s`

`02.d-crud-server-memory-lab | passed | run-client-create.sh | [success] Total time: 4 s, completed 2026/04/03 15:50:15 | 11s`

`02.e-crud-explicit-sync-lab | passed | run.sh | [success] Total time: 4 s, completed 2026/04/03 15:50:27 | 12s`

`02.f-crud-nested-value-lab | passed-after-refresh | run.sh | rerun succeeded after simplemodeling-model/simple-modeler publishLocal | local artifact refresh fixed stale dependency`

`03-operation | passed | run.sh | [success] Total time: 23 s, completed 2026/04/03 15:51:42 | 37s`

`03.a-operation-command-lab | passed | run.sh | [success] Total time: 24 s, completed 2026/04/03 15:52:14 | 32s`

`03.b-operation-entity-lab | passed | run.sh | [success] Total time: 50 s, completed 2026/04/03 15:53:16 | 61s`

`04-cqrs | passed | run.sh | sample runner path fixed and help path resolved | runner migrated to current sample launcher`

`04.a-designed-sync-command-lab | passed | run.sh | sample runner path fixed | current launcher line works`

`04.b-test-sync-command-lab | passed | run.sh | sample runner path fixed | current launcher line works`

`05-event-driven | passed | run.sh | sample runner path fixed | current launcher line works`

`05.a-event-job-trace-lab | passed | run.sh | sample runner path fixed | current launcher line works`

`05.b-event-job-server-client-lab | passed | run-demo.sh | current runner and client GET handling fixed | demo path works`

`06-job | passed | run.sh | sample runner path fixed | current launcher line works`

`06.a-job-control-lab | passed | run.sh | job status query updated and runner fixed | demo line works`

`06.b-job-control-demo-lab | passed | run.sh | sample runner path fixed | current launcher line works`

`07-aggregate | passed | run.sh | aggregate member package generation fixed | current sample runs`

`07.a-aggregate-single-record-lab | passed | run.sh | aggregate member package generation fixed | current sample runs`

`07.b-aggregate-relation-boundary-model | passed | run.sh | aggregate member package generation fixed | current sample runs`

`07.c-aggregate-external-update-semantics | not-started | README only | README states implementation is not started yet | reserved slot only`

`08-view | passed | run.sh | [success] Total time: 3 s, completed 2026/04/03 16:10:57 | 56s`

`08.a-view-definition-lab | passed | run.sh | [success] Total time: 4 s, completed 2026/04/03 16:12:40 | 103s`

`08.b-simpleentity-view-lab | passed | run.sh | load-person and search-person-record succeeded | simpleentity view line works`

`09-subsystem | not-started | run.sh | Wire this script to scripts/sample-runner.sh once the sample main class is defined. | unimplemented sample`

`10-subsystem-wiring | not-started | run.sh | Wire this script to scripts/sample-runner.sh once the sample main class is defined. | unimplemented sample`

`101-distributed | not-started | run.sh | Wire this script to scripts/sample-runner.sh once the sample main class is defined. | unimplemented sample`

## Failure Clusters

### Not-started slots

- `09.c-aggregate-external-update-semantics`
- `11-subsystem`
- `12-subsystem-wiring`
- `101-distributed`

These currently remain outside the fix scope.

resolved:

- `04.f-crud-nested-value-lab`
  - the cause was stale local `simplemodeling-model` / `simple-modeler` artifacts
  - a `publishLocal` refresh restored the sample
- `04-crud`
  - the cause was an old hard-coded `goldenport-cncf` version in `build.sbt`
  - the sample now reads the shared CNCF version file
- `04.a-crud-seed-import-lab`
  - the standard path is sample-local `sbt runMain ... help crud`
- `04.b-simpleentity-crud-lab`
  - the standard path is sample-local `sbt runMain ... help simple-entity-crud-lab`
- `04-07`
  - the shared runner / launcher line has been repaired
- `10.b-simpleentity-view-lab`
  - the simpleentity view line now resolves help/load/search successfully

## Fix Checklist

Status:

- closed
- note:
  - implemented samples are handled
  - reserved or not-started slots remain outside this checklist

### Phase 1: Triage

- [x] Reproduce the failure of `04.f-crud-nested-value-lab` and record the first essential error
- [x] Reproduce the failure of `10.b-simpleentity-view-lab` and record the first essential error
- [x] Reproduce the failure of `06-cqrs` and identify the likely common root cause
- [x] Reproduce the failure of `07-event-driven` and check whether it shares the same line as `04`
- [x] Reproduce the failure of `08-job` and check whether it shares the same line as `05`
- [x] Reproduce the failure of `09-aggregate` and determine whether it belongs to the same line as `04-06`

### Phase 2: Execution Path

- [x] Define the standard execution path for `04-crud`
- [x] Define the standard execution path for `04.a-crud-seed-import-lab`
- [x] Define the standard execution path for `04.b-simpleentity-crud-lab`
- [x] Define the standard execution path for `09.c-aggregate-external-update-semantics`

### Phase 3: Sample Repair

- [x] Fix `04.f-crud-nested-value-lab`
- [x] Fix `10.b-simpleentity-view-lab`
- [x] Fix the `04` sample line
- [x] Fix the `05` sample line
- [x] Fix the `06` sample line
- [x] Fix the `07` sample line

### Phase 4: Reverification

- [x] Reverify `02.f`
- [x] Reverify `08.b`
- [x] Reverify the `04-07` line
- [x] Reverify the full sample set

## Notes

- The goal of this pass was full-sample current-state verification, not deep debugging of every failure
- `07.c`, `09`, `10`, and `101` are currently treated as not-started samples and can remain that way for now
- `02.f` was investigated after the initial failure and was restored by refreshing `simplemodeling-model` / `simple-modeler` with `publishLocal`
- `04-07` runner failures were repaired by moving those samples onto the current shared launcher line
- this checklist is closed as a sample-maintenance pass
- future work starts from new sample implementation, not from this checklist
