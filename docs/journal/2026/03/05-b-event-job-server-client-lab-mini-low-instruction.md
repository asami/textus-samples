# 05.b-event-job-server-client-lab Mini Low Instruction

## Goal

Implement a practical client/server follow-up lab after:

- `05-event-driven`
- `05.a-event-job-trace-lab`

This lab should help the user understand the runtime image in a more practical way:

- start a server
- invoke the event-producing operation from a client
- inspect the resulting progression from client-visible routes

## Read First

- [/Users/asami/src/dev2026/cncf-samples/samples/05-event-driven/README.md](/Users/asami/src/dev2026/cncf-samples/samples/05-event-driven/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/05.a-event-job-trace-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/05.a-event-job-trace-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/06-job/README.md](/Users/asami/src/dev2026/cncf-samples/samples/06-job/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/06.a-job-control-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/06.a-job-control-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/04-a-event-job-trace-lab-development-instruction.md](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/04-a-event-job-trace-lab-development-instruction.md)

## Intent

`04.a` is allowed to use direct same-JVM observation to explain the internal trace.

`04.b` should instead show a more practical image:

- one server process hosts the sample
- a client invokes the event-producing operation
- the client can inspect the result through existing routes

This is still a local sample.
It is not a distributed event platform lab.

## Required Outcome

Create `05.b-event-job-server-client-lab` that demonstrates:

1. one server startup path
2. one client path that emits the event
3. one client path that confirms the triggered reaction
4. one client-visible job/history/event observation path, if already possible with existing CNCF capabilities

## Preferred Direction

- Reuse the event story from `05-event-driven`
- Reuse existing CNCF server/client execution paths
- Prefer builtin routes for observation if they already work
- Keep custom code thin

## Rules

- Keep the lab small
- Do not redesign the server/client model
- Do not redesign the event subsystem
- Do not redesign the job subsystem
- Do not add CML or generator features
- Do not add hand-written repository logic
- Do not add a distributed broker or queue

## Stop Conditions

Stop immediately if any of these becomes necessary:

- a major CNCF server/client redesign
- new event/job model features
- Cozy/CML or generator enhancement
- custom code beyond thin server/client runners

If blocked, report only:

- the exact missing capability
- the exact file or command where it blocked
- which files were changed before stopping

## Minimum Verification

At minimum, confirm:

- the server starts
- the client can invoke the event-producing operation
- the triggered reaction is visible from a client-accessible route

If job/history/event observation is available through existing client routes, confirm one of them too.

## Report Back Only

- what files you changed
- what server command succeeded
- what client command succeeded
- what event was emitted
- what proved the triggered reaction
- whether the lab is complete or blocked
