# 07-aggregate Development Instruction

## Goal

Create the first dedicated aggregate-oriented sample after the job section.

## Position

- `02-*`
  - CRUD and persistence variations
- `03-*`
  - CQRS and sync variations
- `04-*`
  - event-oriented patterns
- `05-*`
  - job and job control
- `07-aggregate`
  - aggregate-oriented structure as an independent topic

## First Completion Line

The sample is complete at the first line when:

1. one aggregate-oriented model exists
2. one aggregate load route is runnable
3. one aggregate search route is runnable
4. the sample documentation makes the aggregate boundary explicit

## Scope

- keep it small
- prefer model-driven definition
- no handwritten repository layer
- no distributed infrastructure
- no large domain story
- use application-level join aggregate construction as the main explanatory pattern

## Notes

- use this slot to explain aggregate-shaped access itself
- prefer a root + member aggregate such as `Order` + `OrderLine`
- do not make the main line a single-record encoded aggregate
- do not collapse it into plain CRUD wording
- keep the completion line concrete and runnable
