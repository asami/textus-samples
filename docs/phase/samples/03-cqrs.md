# 03-cqrs Checklist

## Purpose

Deliver a CQRS sample that separates asynchronous commands from synchronous queries.

Stage Status:
- Current status: `DONE`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- async command
- sync query
- job integration

## Step

- Separate job-backed commands from immediate queries.

## Checklist

- [x] `createItem` works as an async command
- [x] `loadItem` works as a sync query
- [x] `searchItemRecord` works as a sync query
- [x] The job integration flow is verified
- [x] The sample does not depend on other samples
- [x] `samples/03-cqrs/README.md` is updated to match the implementation

## Exit Criteria

- [x] Build succeeds
- [x] CLI execution works
- [x] CQRS behavior is confirmed
- [x] README is complete
