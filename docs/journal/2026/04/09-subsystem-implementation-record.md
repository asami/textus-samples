# 09-subsystem Implementation Record

## Summary

`09-subsystem` was implemented as the baseline shell-first formal subsystem sample.

## Outcome

- one generic component artifact
- one explicitly selected subsystem name
- one service
- one operation
- subsystem help, component help, operation help, and operation execution all verified through `cncf`

## Notes

- the sample intentionally stays close to `01-minimal`
- the difference is the explanatory focus: `09-subsystem` centers the subsystem as an explicit composition artifact
- the stable execution line is `component.d/testcomp.car` plus `--textus.runtime.subsystem=testsubsystem`
- component name and subsystem name are intentionally different: `testcomp` vs `testsubsystem`
- config-file equivalents exist, but they are only the general CNCF parameter/config transparency rule
- no sample-local main is used
