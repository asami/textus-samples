# 01.d-component-script Implementation Record

Status: `Completed`

Implemented on 2026-03-26.

## Summary

`01.d-component-script` is now a single-file `scala-cli` script sample.

It demonstrates:

- one file script execution
- `scala-cli`-based startup
- CNCF script DSL
- management-program style usage

The concrete script example is:

- `samples/01.d-component-script/script/main.scala`

## Implementation

- replaced the earlier multi-file Component idea with a single-file script
- added a `scala-cli` shebang script using `run(args) { ... }`
- kept `run.sh` as a thin launcher to the script file
- kept `invoke.sh` as the same script entry, so the sample stays compact
- rewrote the README to explain why script form is useful for management commands

## Verification

- `./run.sh` succeeded and printed `Hello CNCF`
- `./invoke.sh` succeeded and printed `Hello CNCF`
- the script emitted CNCF runtime action events while executing

## Notes

- this sample is suitable for small management programs and operational tooling
- for larger or more structured behavior, formal Component definition should be preferred
- the shell remains thin
- the script file remains the real operational contract
