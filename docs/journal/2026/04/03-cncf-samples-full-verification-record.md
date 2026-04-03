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

- `09-subsystem`
- `10-subsystem-wiring`
- `101-distributed`

These three are currently unimplemented samples, so they are treated as not-started rather than as failures.

## Result

Totals:

- total checked: 33
- passed: 13
- failed: 16
- blocked: 0
- execution path missing: 4

Handled as not-started:

- `09-subsystem`
- `10-subsystem-wiring`
- `101-distributed`

## Per Sample

`01-minimal | passed | run.sh | [success] Total time: 3 s, completed 2026/04/03 15:48:44 | 9s`

`01.a-invocation-source-lab | passed | run.sh | [success] Total time: 7 s, completed 2026/04/03 15:48:58 | 13s`

`01.b-startup-shapes-lab | passed | run.sh | [success] Total time: 5 s, completed 2026/04/03 15:49:10 | 12s`

`01.c-builtin-and-help-lab | passed | run.sh | [success] Total time: 4 s, completed 2026/04/03 15:49:44 | 11s`

`01.d-component-script | passed | run.sh | Hello CNCF | 5s`

`02-crud | execution path missing | - | no runnable script found | check README/manual flow`

`02.a-crud-seed-import-lab | execution path missing | - | no runnable script found | check README/manual flow`

`02.b-simpleentity-crud-lab | execution path missing | - | no runnable script found | check README/manual flow`

`02.c-crud-sqlite-lab | passed | run.sh | [success] Total time: 5 s, completed 2026/04/03 15:50:03 | 13s`

`02.d-crud-server-memory-lab | passed | run-client-create.sh | [success] Total time: 4 s, completed 2026/04/03 15:50:15 | 11s`

`02.e-crud-explicit-sync-lab | passed | run.sh | [success] Total time: 4 s, completed 2026/04/03 15:50:27 | 12s`

`02.f-crud-nested-value-lab | passed-after-refresh | run.sh | rerun succeeded after simplemodeling-model/simple-modeler publishLocal | local artifact refresh fixed stale dependency`

`03-operation | passed | run.sh | [success] Total time: 23 s, completed 2026/04/03 15:51:42 | 37s`

`03.a-operation-command-lab | passed | run.sh | [success] Total time: 24 s, completed 2026/04/03 15:52:14 | 32s`

`03.b-operation-entity-lab | passed | run.sh | [success] Total time: 50 s, completed 2026/04/03 15:53:16 | 61s`

`04-cqrs | failed | run.sh | [error] Total time: 55 s, completed 2026/04/03 15:54:27 | exit 1`

`04.a-designed-sync-command-lab | failed | run.sh | [error] Total time: 72 s (0:01:12.0), completed 2026/04/03 15:55:53 | exit 1`

`04.b-test-sync-command-lab | failed | run.sh | [error] Total time: 67 s (0:01:07.0), completed 2026/04/03 15:57:17 | exit 1`

`05-event-driven | failed | run.sh | [error] Total time: 56 s, completed 2026/04/03 15:58:30 | exit 1`

`05.a-event-job-trace-lab | failed | run.sh | [error] Total time: 56 s, completed 2026/04/03 15:59:45 | exit 1`

`05.b-event-job-server-client-lab | failed | run-client-emit.sh | [error] Total time: 52 s, completed 2026/04/03 16:00:56 | exit 1`

`06-job | failed | run.sh | [error] Total time: 54 s, completed 2026/04/03 16:02:07 | exit 1`

`06.a-job-control-lab | failed | run.sh | [error] Total time: 50 s, completed 2026/04/03 16:03:10 | exit 1`

`06.b-job-control-demo-lab | failed | run.sh | [error] Total time: 54 s, completed 2026/04/03 16:04:18 | exit 1`

`07-aggregate | failed | run.sh | [error] Total time: 66 s (0:01:06.0), completed 2026/04/03 16:05:39 | exit 1`

`07.a-aggregate-single-record-lab | failed | run.sh | [error] Total time: 51 s, completed 2026/04/03 16:06:47 | exit 1`

`07.b-aggregate-relation-boundary-model | failed | run.sh | [error] Total time: 144 s (0:02:24.0), completed 2026/04/03 16:10:02 | exit 1`

`07.c-aggregate-external-update-semantics | execution path missing | - | no runnable script found | check README/manual flow`

`08-view | passed | run.sh | [success] Total time: 3 s, completed 2026/04/03 16:10:57 | 56s`

`08.a-view-definition-lab | passed | run.sh | [success] Total time: 4 s, completed 2026/04/03 16:12:40 | 103s`

`08.b-simpleentity-view-lab | failed | run.sh | [error] Total time: 54 s, completed 2026/04/03 16:13:44 | exit 1`

`09-subsystem | not-started | run.sh | Wire this script to scripts/sample-runner.sh once the sample main class is defined. | unimplemented sample`

`10-subsystem-wiring | not-started | run.sh | Wire this script to scripts/sample-runner.sh once the sample main class is defined. | unimplemented sample`

`101-distributed | not-started | run.sh | Wire this script to scripts/sample-runner.sh once the sample main class is defined. | unimplemented sample`

## Failure Clusters

### Missing execution path

- `02-crud`
- `02.a-crud-seed-import-lab`
- `02.b-simpleentity-crud-lab`
- `07.c-aggregate-external-update-semantics`

To check:

- whether `run.sh` should be added
- whether a README-based standard verification path should be fixed and documented

### CQRS / Event / Job / Aggregate line failures

- `04-cqrs`
- `04.a-designed-sync-command-lab`
- `04.b-test-sync-command-lab`
- `05-event-driven`
- `05.a-event-job-trace-lab`
- `05.b-event-job-server-client-lab`
- `06-job`
- `06.a-job-control-lab`
- `06.b-job-control-demo-lab`
- `07-aggregate`
- `07.a-aggregate-single-record-lab`
- `07.b-aggregate-relation-boundary-model`

suspect:

- runtime or model/generator changes affecting higher-level orchestration paths
- a shared breakage rather than isolated sample-specific issues

### Isolated failures

- `08.b-simpleentity-view-lab`

suspect:

- `SimpleEntity` view line regression

resolved:

- `02.f-crud-nested-value-lab`
  - the cause was stale local `simplemodeling-model` / `simple-modeler` artifacts
  - a `publishLocal` refresh restored the sample

## Fix Checklist

### Phase 1: Triage

- [x] Reproduce the failure of `02.f-crud-nested-value-lab` and record the first essential error
- [ ] Reproduce the failure of `08.b-simpleentity-view-lab` and record the first essential error
- [ ] Reproduce the failure of `04-cqrs` and identify the likely common root cause
- [ ] Reproduce the failure of `05-event-driven` and check whether it shares the same line as `04`
- [ ] Reproduce the failure of `06-job` and check whether it shares the same line as `05`
- [ ] Reproduce the failure of `07-aggregate` and determine whether it belongs to the same line as `04-06`

### Phase 2: Execution Path

- [ ] Define the standard execution path for `02-crud`
- [ ] Define the standard execution path for `02.a-crud-seed-import-lab`
- [ ] Define the standard execution path for `02.b-simpleentity-crud-lab`
- [ ] Define the standard execution path for `07.c-aggregate-external-update-semantics`

### Phase 3: Sample Repair

- [x] Fix `02.f-crud-nested-value-lab`
- [ ] Fix `08.b-simpleentity-view-lab`
- [ ] Fix the `04` sample line
- [ ] Fix the `05` sample line
- [ ] Fix the `06` sample line
- [ ] Fix the `07` sample line

### Phase 4: Reverification

- [x] Reverify `02.f`
- [ ] Reverify `08.b`
- [ ] Reverify the `04-07` line
- [ ] Reverify the full sample set

## Notes

- The goal of this pass was full-sample current-state verification, not deep debugging of every failure
- `09`, `10`, and `101` are currently treated as not-started samples and can remain that way for now
- `02.f` was investigated after the initial failure and was restored by refreshing `simplemodeling-model` / `simple-modeler` with `publishLocal`
