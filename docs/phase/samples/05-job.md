# 05-job Checklist

## Purpose

Deliver a job lifecycle sample that demonstrates submission, status query, and failure or retry handling.

Stage Status:
- Current status: `OPEN`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Job submission
- Job status
- Failure or retry behavior

## Step

- Create a job management sample with submit and status query.

## Checklist

- [ ] `submitJob` works
- [ ] `getJobStatus` works
- [ ] Retry or failure handling is verified
- [ ] If needed, `docker/` is updated to executable contents
- [ ] `samples/05-job/README.md` is updated to match the implementation

## Exit Criteria

- [ ] Build succeeds
- [ ] CLI execution works
- [ ] Job lifecycle behavior is confirmed
- [ ] README is complete
