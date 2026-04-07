# 02.a-crud-seed-import-lab Implementation Record

Status:
- `Completed`
- This is an implementation record, not the progress authority.
- The progress authority is `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/04.a-crud-seed-import-lab.md`.

## Direction

This lab was completed as a descriptor-first sample.

- Static runtime metadata is read from `car.d/meta/component-descriptor.yaml`.
- Seed data is imported from `entity.d/crud.yaml`.
- No sample-local wrapper factory is used.
- Runtime verification uses `CncfMain --discover=classes`.

## Key Changes

- Added local CAR-style descriptor placement:
  - `/Users/asami/src/dev2026/cncf-samples/samples/04.a-crud-seed-import-lab/car.d/meta/component-descriptor.yaml`
- Removed the sample-local Scala wrapper factory.
- Switched the runtime metadata direction to `ComponentDescriptor`.
- Separated runtime/framework parameters from domain query parameters by using
  the `cncf.*` namespace.
- Fixed entity search so domain filters are evaluated against decoded entities
  rather than flattened external records.

## Runtime Verification

Confirmed in:
- `/Users/asami/src/dev2026/cncf-samples/samples/04.a-crud-seed-import-lab`

Commands:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command Crud.entity.loadItem --id major-minor-entity-item-20260327000000-aaa111"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command Crud.entity.searchItemRecord"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command Crud.entity.searchItemRecord --name alpha"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.format yaml Crud.entity.searchItemRecord --name alpha"
```

Observed facts:
- `loadItem` returned the seeded `alpha` item.
- `searchItemRecord` returned the two imported items.
- `searchItemRecord --name alpha` returned only the `alpha` item.
- `--cncf.format yaml` did not interfere with the domain filter.

## Conclusion

`02.a-crud-seed-import-lab` now demonstrates:

- descriptor-first runtime metadata through `ComponentDescriptor`
- initial seed import through `entity.d`
- runtime `load` and `search` verification against preloaded data
- namespaced framework parameters (`cncf.*`) without leaking into domain search conditions
