# 02.f CRUD Nested Value Lab Implementation Record

## Summary

`02.f-crud-nested-value-lab` was reworked as the nested value persistence variant of the `02` CRUD line.

The sample no longer relies on hand-written demo mains.
It now uses the generated CRUD path and verifies nested value persistence through SQLite-backed create and load commands.

## What Changed

- kept the sample on the `sbt-cozy` generated line
- updated the build to use the current CNCF version line
- removed the hand-written nested value demo mains
- rewrote the shell scripts around `bin/cncf`
- made the SQLite-backed CRUD roundtrip the mainline verification

## Verified Commands

- `bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.create-person`
- `bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.load-person`
- `bash ../../bin/cncf --discover=classes command crud-nested-value-sample.meta.describe --format yaml`
- `bash run-create.sh`
- `bash run-load.sh <person-id>`
- `bash run-datastore.sh`
- `bash run.sh`

## Observed Output Shape

The generated create route returns the created entity id immediately:

```yaml
id: major-minor-entity-person-...
```

The later load restores the nested value structure:

```yaml
id: major-minor-entity-person-...
name: alice
address:
  street: Marunouchi-1-2-3
  city: Tokyo
  country:
    value: JP
```

## Main Point

The important point of `02.f` is that nested value data remains inside the plain CRUD entity line.
The persistence path still uses one entity record, but the later load recreates the nested value structure correctly.
