# 06-aggregate Work Checklist

This checklist is the working tracker for `06-aggregate`.

Authority over completion remains:

- `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/06-aggregate.md`

This file is for implementation progress management across:

- Cozy
- CNCF
- `cncf-samples`

## Cozy

- [x] confirm aggregate source-of-truth shape (`EntityDef.aggregate` / `AggregateDef`)
- [ ] confirm `Order` / `OrderLine` can be expressed in the current model
- [ ] confirm first-line behavior/invariant shape can be expressed
- [ ] confirm aggregate-related generated output needed by `06-aggregate`
- [ ] confirm metadata needed for delegated `AggregateBehavior` binding

## CNCF

- [x] freeze first-line runtime contract for `Behavior` / `AggregateBehavior` / `ActionCall`
- [ ] add `Behavior` abstraction with protected DSL
- [ ] align protected-method naming to `snake_case`
- [ ] add `AggregateBehavior`
- [ ] make `ActionCall` able to resolve delegated `AggregateBehavior`
- [ ] make `ActionCall` able to invoke delegated `AggregateBehavior` and continue its own logic
- [ ] add Factory-based `AggregateBehavior` binding/lookup
- [ ] confirm aggregate read path works for application-join aggregate construction
- [ ] confirm first-line invariant failure can be surfaced correctly

## Sample

- [x] create `samples/06-aggregate/` runnable skeleton
- [ ] define `Order` aggregate root
- [ ] define `OrderLine` member entity
- [ ] verify aggregate-load
- [ ] verify aggregate-search
- [ ] verify `addLine` behavior
- [ ] verify invalid quantity failure
- [ ] update README with application-join aggregate explanation
- [ ] update implementation record
- [ ] update phase checklist with confirmed facts only

## Out Of Scope For First Line

- [ ] do not pull in view synchronization
- [ ] do not introduce separate `Processor` abstraction
- [ ] do not make single-record encoded aggregate the main sample pattern
- [ ] do not add handwritten repository logic
- [ ] do not broaden into snapshot/distributed concerns
