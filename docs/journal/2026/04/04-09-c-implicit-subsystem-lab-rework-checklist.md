# 04-09.c-implicit-subsystem-lab Rework Checklist

## Goal

Establish `11.c-implicit-subsystem-lab` as the shell-first sample for the implicit subsystem form.

## Checklist

- [x] the implicit subsystem shape is explained
- [x] the sample remains component-only
- [x] subsystem help, component help, and operation execution are verified
- [x] the README matches the implementation

## Verification

- [x] `../../bin/setup cozy`
- [x] `sbt --batch clean compile`
- [x] `bash ../../bin/cncf --discover=classes command meta.help --format yaml`
- [x] `bash ../../bin/cncf --discover=classes command meta.help subsystem --format yaml`
- [x] `bash ../../bin/cncf --discover=classes command help subsystem.main.hello`
- [x] `bash ../../bin/cncf --discover=classes command subsystem.main.hello`
- [x] `bash run.sh`
