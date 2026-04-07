# 09-subsystem Checklist

## Purpose

Deliver the baseline formal subsystem sample with one generic component artifact, started by naming the runtime subsystem explicitly.

Stage Status:
- Current status: `DONE`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Formal subsystem
- One generic component artifact
- Distinct subsystem and component names
- Subsystem-name startup parameter

## Step

- Show the minimum executable form of an explicitly selected subsystem wired to a generic component artifact, started by `--textus.runtime.subsystem=...`.

## Checklist

- [x] A minimum subsystem structure with one generic component artifact is implemented
- [x] The minimum formal subsystem composition is explicitly implemented and verified
- [x] The minimum subsystem approach is explained in the README
- [x] If needed, `docker/` is updated to executable contents
- [x] `samples/09-subsystem/README.md` is updated to match the implementation

## Exit Criteria

- [x] Build succeeds
- [x] CLI execution works
- [x] Minimum subsystem behavior is confirmed
- [x] README is complete
