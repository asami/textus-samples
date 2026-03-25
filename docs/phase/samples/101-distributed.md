# 101-distributed Checklist

## Purpose

Deliver a distributed sample that demonstrates multiple subsystems deployed across network boundaries.

Stage Status:
- Current status: `OPEN`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Distributed deployment
- Network boundary
- Component communication

## Step

- Run multiple subsystems separately across a network boundary.

## Checklist

- [ ] Separate deployment of multiple subsystems is implemented
- [ ] Component communication is verified
- [ ] A `docker compose up` based startup flow is in place
- [ ] The network boundary is explained in the README
- [ ] `samples/101-distributed/README.md` is updated to match the implementation

## Exit Criteria

- [ ] Build succeeds
- [ ] CLI execution works
- [ ] Distributed behavior is confirmed
- [ ] README is complete
