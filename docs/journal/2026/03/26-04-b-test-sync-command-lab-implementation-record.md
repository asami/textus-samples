# 04.b-test-sync-command-lab Implementation Record

Status: `Completed`

## Summary

`04.b-test-sync-command-lab` is the runtime-override counterpart to `03.a`.

It keeps the command async/job-backed by default and adds a synchronous
execution override for test/local/debug use.

## Facts

- The sample lives under `samples/04.b-test-sync-command-lab`.
- The command target is `TestSync.Item.createItem`.
- The default runtime path returns a job id:
  - `cncf-job-job-...`
- The override path also returns a job id and succeeds with:
  - `--textus.runtime.command.execution-mode sync-job-async-interface`
- The sample now uses the existing runtime override mode rather than `sync` / `sync-direct`.
- CNCF needed a small command-path wiring fix so framework parameters survive subsystem initialization in command mode.
- Envelope output succeeds with:
  - `--textus.output.shape envelope`
  - `--textus.output.format yaml`
- The default envelope shows:
  - `textus-execution.interface-shape: job`
- The override envelope shows:
  - `textus-execution.interface-shape: job`
  - `textus-execution.requested-mode: sync-job-async-interface`

## Notes

- This record should be updated only with runtime facts that were actually confirmed.
- The phase checklist remains the status authority.
- `03.b` keeps the external interface job-based on both paths.
- The override is about synchronous internal completion for test/local/debug, not about changing the visible result shape.
- The envelope path makes the requested override visible without changing the external job-shaped contract.
