# 04-09-subsystem Rework Checklist

## Goal

Rework `09-subsystem` as the shell-first baseline formal subsystem sample driven by a generic component artifact and a subsystem-name CLI parameter.

## Checklist

- [x] a minimum formal subsystem structure with one generic component artifact is implemented
- [x] the minimum formal subsystem composition is explicitly implemented and verified
- [x] the generic-component formal subsystem approach is explained in the README
- [x] `samples/09-subsystem/README.md` matches the implementation
- [x] build succeeds
- [x] CLI execution works
- [x] minimum subsystem behavior is confirmed

## Verification

- [x] `../../bin/setup cozy`
- [x] `sbt --batch clean compile`
- [x] `bash ../../bin/cncf command meta.help --format yaml --no-default-components --textus.runtime.subsystem=testsubsystem`
- [x] `bash ../../bin/cncf command meta.help testcomp --format yaml --no-default-components --textus.runtime.subsystem=testsubsystem`
- [x] `bash ../../bin/cncf command help testcomp.main.hello --no-default-components --textus.runtime.subsystem=testsubsystem`
- [x] `bash ../../bin/cncf command testcomp.main.hello --no-default-components --textus.runtime.subsystem=testsubsystem`
- [x] `bash run.sh`
