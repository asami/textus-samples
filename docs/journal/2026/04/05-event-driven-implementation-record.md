# 05-event-driven Implementation Record

## Summary

`05-event-driven` was normalized as the base event-oriented shell sample.

The sample now focuses on:

- the generated event-facing shell surface
- modeled event emission and reception selectors
- metadata that makes the event structure visible
- the boundary between user-facing event commands and internal runtime proof

## What Changed

- removed the sample-local same-JVM demo main
- removed `run-demo.sh`
- rewrote the README as a shell-first event sample
- moved the same-JVM event-effect proof into `cozy` scripted
- updated `run.sh` to cover:
  - component help
  - emit-event help
  - load-effect help
  - metadata describe

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help event-driven`
- `bash ../../bin/cncf --discover=classes command help event-driven.event.emit-event`
- `bash ../../bin/cncf --discover=classes command help event-driven.event.load-effect`
- `bash ../../bin/cncf --discover=classes command event-driven.meta.describe --format yaml`
- `bash run.sh`
- `sh check-event-driven-surface.sh`

## Observed Output

Component help confirms:

- `EventDriven`
- `Event`
- `emitEvent`
- `recordEffect`
- `loadEffect`

Operation help confirms:

- `event-driven.event.emit-event`
- `event-driven.event.load-effect`

Metadata confirms that the sample exposes an event-facing runtime surface through the generated component.

The `cozy` scripted fixture confirms the internal proof that used to live in the sample:

- same-JVM `emitEvent -> awaitJobResult -> loadEffect`
- emitted event name `item.changed`
- observed effect fields `name = alpha` and `title = Alpha`
- final scripted result `EVENT_DRIVEN_SURFACE_OK`

## Main Point

`05-event-driven` is the first shell-facing event sample.

It shows the modeled event surface to the user, while the internal same-JVM effect proof is moved to `cozy` scripted.
