# 02.f-crud-nested-value-lab Implementation Record

- status: done
- date: 2026-03-31

## Scope

First line for plain CRUD nested value object persistence.

## Model

- `Person` is the persisted entity.
- `Address` is a value object embedded in `Person`.
- `CountryCode` is a value object embedded in `Address`.

## Verified

- `sbt --batch clean compile` in `samples/02.f-crud-nested-value-lab`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud-nested-value-sample.entity.create-person"`
- `bash run.sh`
- `bash run-datastore.sh`
- `bash run-create.sh`
- `bash run-load.sh <person-id>`

## Confirmed

- one `Person` record contains nested embedded value data
- `Person.createC(record)` restores nested value object structure
- datastore `create -> load` also restores nested value object structure
- SQLite-backed `create-person -> load-person` also restores nested value object structure across processes
- output includes:
  - `record.address.country.value = "JP"`
  - `loaded.address.country.value = "JP"`

## Notes

- this lab stays in the plain CRUD line
- this is not an aggregate sample
- unlike `06.a-aggregate-single-record-lab`, the point here is nested value object persistence for an entity attribute
