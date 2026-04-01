# 04-cqrs Implementation Record

## Summary

`04-cqrs` now demonstrates a visible split between a job-backed command side and an immediate query side.

## Facts

- The sample is model-driven via `samples/04-cqrs/src/main/cozy/cqrs.cml`.
- The component is `Cqrs`.
- The command-side target is `Cqrs.Item.createItem`.
- The query-side targets are `Cqrs.entity.loadItem` and `Cqrs.entity.searchItemRecord`.
- A seeded `Item` record is provided under `samples/04-cqrs/entity.d/item.yaml`.
- The README examples were updated to use `CncfMain --discover=classes`.
- `Cqrs.Item.createItem` returns a job-oriented response.
- `Cqrs.entity.loadItem` returns the seeded item immediately.
- `Cqrs.entity.searchItemRecord` returns the matching records immediately.

## Notes

- This record only states facts that have been confirmed by implementation and runtime checks.
