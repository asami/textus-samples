# 07-aggregate Work Checklist

This checklist is the working tracker for `07-aggregate`.

Authority over completion remains:

- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/07-aggregate.md`

This file is for implementation progress management across:

- Cozy
- CNCF
- `cncf-samples`

## Cozy

- [x] confirm aggregate source-of-truth shape (`EntityDef.aggregate` / `AggregateDef`)
- [x] confirm `Order` / `OrderLine` can be expressed in the current model
- [x] confirm first-line behavior/invariant shape can be expressed
- [x] confirm aggregate-related generated output needed by `07-aggregate`
- [x] confirm metadata needed for delegated `AggregateBehavior` binding

## CNCF

- [x] freeze first-line runtime contract for `Behavior` / `AggregateBehavior` / `ActionCall`
- [x] add `Behavior` abstraction with protected DSL
- [x] align protected-method naming to `snake_case`
- [x] add `AggregateBehavior`
- [x] make `ActionCall` able to resolve delegated `AggregateBehavior`
- [x] make `ActionCall` able to invoke delegated `AggregateBehavior` and continue its own logic
- [x] add Factory-based `AggregateBehavior` binding/lookup
- [x] add Factory-based aggregate collection binding hook for future aggregate read extension
- [x] confirm aggregate read path works for application-join aggregate construction
- [x] confirm first-line invariant failure can be surfaced correctly
- [x] add aggregate-internal visibility as a dedicated framework execution-context rule
- [x] confirm generated aggregate load/search mainline can use aggregate collection binding
- [x] generalize aggregate collection binding enough to remove sample-local aggregate read builder/query
- [x] remove runtime reflection from aggregate member attach via generated `AggregateAssembler`

## Sample

- [x] create `samples/07-aggregate/` runnable skeleton
- [x] define `Order` aggregate root
- [x] define `OrderLine` member entity
- [x] verify aggregate-load
- [x] verify aggregate-search
- [x] verify `addLine` behavior
- [x] verify invalid quantity failure
- [x] make sample join logic consume generated aggregate member metadata
- [x] make generated aggregate read type include aggregate members
- [x] update README with application-join aggregate explanation
- [x] update implementation record
- [x] update phase checklist with confirmed facts only
- [x] remove request-side content-manager workaround from the demo and rely on aggregate-internal visibility
- [x] confirm `07-aggregate` still runs after the structural `AggregateAssembler` change

## Out Of Scope For First Line

- [ ] do not pull in view synchronization
- [ ] do not introduce separate `Processor` abstraction
- [ ] do not make single-record encoded aggregate the main sample pattern
- [ ] do not add handwritten repository logic
- [ ] do not broaden into snapshot/distributed concerns
