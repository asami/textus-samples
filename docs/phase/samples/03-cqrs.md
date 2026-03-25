# 03-cqrs Checklist

## Purpose

Deliver a CQRS sample that separates asynchronous commands from synchronous queries.

Stage Status:
- Current status: `OPEN`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- async command
- sync query
- job integration

## Step

- Separate job-backed commands from immediate queries.

## Checklist

- [ ] `createItem` works as an async command
- [ ] `getItem` works as a sync query
- [ ] The job integration flow is verified
- [ ] The sample does not depend on other samples
- [ ] `samples/03-cqrs/README.md` is updated to match the implementation

## Exit Criteria

- [ ] Build succeeds
- [ ] CLI execution works
- [ ] CQRS behavior is confirmed
- [ ] README is complete
