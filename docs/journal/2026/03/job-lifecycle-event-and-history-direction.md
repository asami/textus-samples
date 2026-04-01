# Job Lifecycle Event And History Direction

## Intent

Job control should not be observed only through a final status snapshot.

For `submit / suspend / resume / cancel`, CNCF should expose:

- lifecycle events
- per-job history

Both are needed because they serve different roles.

## Two Observation Surfaces

### 1. Event Surface

Lifecycle changes should be emitted as events.

Minimum event kinds:

- `job.submitted`
- `job.suspended`
- `job.resumed`
- `job.cancelled`

Optional but natural additions:

- `job.running`
- `job.succeeded`
- `job.failed`

Purpose:

- external observation
- event-driven follow-up
- cross-component or cross-subsystem integration
- traceability

### 2. History Surface

The same lifecycle changes should also be visible as job-local history.

This is a per-job ordered record of what happened.

Minimum fields:

- `job-id`
- `kind`
- `timestamp`
- `status`
- `command` or `request-summary`
- `control-command` when applicable

Purpose:

- admin UI
- debugging
- audit
- lab verification for suspend/resume/cancel

## Relationship

The preferred direction is:

- job lifecycle change occurs
- a lifecycle event is emitted
- the job read model/history records the same change

So event and history are not separate truths.

They are two observation surfaces over the same lifecycle fact.

## Sample Impact

### `06-job`

Application-facing job sample:

- submit
- status/result
- timeline/debug

### `06.a-job-control-lab`

Admin-facing control lab:

- cancel
- suspend
- resume
- lifecycle event observation
- job history observation

This is stronger than checking only a final status.

## Why This Matters

Without lifecycle events or history:

- `cancel` is often visible through final status
- `suspend` and `resume` are much harder to prove

That is exactly what made the first `05.a` prototype insufficient.

## Direction

The next implementation direction should be:

1. emit lifecycle events for job control actions
2. expose those events through an event observation route
3. expose the same lifecycle facts through job history
4. update `05.a` to verify both surfaces
