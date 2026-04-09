# 12-subsystem-wiring Checklist

## Purpose

Deliver a subsystem sample that demonstrates how one subsystem wires two components into a composed executable unit.

Stage Status:
- Current status: `RUNNABLE`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Two-component wiring
- Subsystem assembly
- Component composition
- Descriptor-guided delegated call
- Port and wiring result retrieval through `admin.assembly.report`

## Step

- Show how a subsystem assembles two components and routes one component call to another using descriptor wiring metadata.

## Checklist

- [x] A subsystem structure with two components is implemented
- [x] The wiring between the two components is explicitly implemented and verified
- [x] Declared `api` / `spi` ports are visible in the sample result and admin assembly report
- [x] The subsystem assembly approach is explained in the README
- [x] The wiring result can be retrieved from the admin assembly surface
- [ ] If needed, `docker/` is updated to executable contents
- [x] `samples/12-subsystem-wiring/README.md` is updated to match the implementation

## Exit Criteria

- [x] Build succeeds
- [x] CLI execution works
- [x] Two-component subsystem wiring is confirmed
- [x] README is complete
