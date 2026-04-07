# 05-event-driven Checklist

## Purpose

Deliver the first minimal event-oriented sample after `06-cqrs`.

The first completion line is:

- one event-producing action
- one event reception path
- one visible post-event effect

Stage Status:
- Current status: `DONE`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Event definition
- Event dispatch/reception
- Observable post-event effect

## Step

- Build the smallest event-driven sample that proves another runtime part reacted to an emitted event.

## Checklist

- [x] The event-producing action is defined
- [x] The emitted event is defined and documented
- [x] One event reception path is implemented
- [x] One visible post-event effect is implemented
- [x] Event-related help paths are identified
- [x] `samples/07-event-driven/README.md` explains the event, the receiver, and the observable effect

## Exit Criteria

- [x] Build succeeds
- [x] Event-related help paths resolve
- [x] The event-producing path succeeds
- [x] The post-event effect is observable
- [x] README matches the actual runtime behavior
