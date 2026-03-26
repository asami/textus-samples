# 01.d-component-script Verification Record

Status: `Completed`

Verified on 2026-03-26 after script-only cleanup.

Status authority:

- [`docs/phase/samples/01.d-component-script.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.d-component-script.md)

## Summary

`01.d-component-script` was re-verified in its cleaned script-only form.

The sample now consists of:

- `script/main.scala`
- `run.sh`
- `invoke.sh`
- README documentation for the script form

Old sbt / Component sample scaffolding was removed from the sample structure.

## Verification

### `./run.sh`

- result: succeeded
- observed runtime cue: `event=enter scope=Action name=script_RUN`
- observed output: `Hello CNCF`

### `./invoke.sh`

- result: succeeded
- observed runtime cue: `event=enter scope=Action name=script_RUN`
- observed output: `Hello CNCF`

## Notes

- both launchers are thin wrappers around `script/main.scala`
- the script still runs through CNCF runtime behavior
- the cleaned sample structure is now aligned with the README description
