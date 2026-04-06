# 02.c-crud-sqlite-lab implementation record

## Scope

This line is the SQLite-backed persistence variant of the `02` CRUD family.

The first completion target is:

1. one generated CRUD sample runs against a SQLite file
2. one seeded record is readable through the SQLite-backed load/search path
3. one created record can be loaded in a later command by reusing the same SQLite file
4. one metadata path explains the generated runtime surface

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/02.c-crud-sqlite-lab`
- generated `sbt-cozy` line restored
- old hand-written runtime Scala removed from the sample path
- shell-first runtime commands through `bin/cncf`
  - `command help crud.entity.load-item`
  - `command help crud.entity.search-item-record`
  - `command --cncf.datastore.sqlite.path=... crud.entity.load-item --id ...`
  - `command --cncf.datastore.sqlite.path=... crud.entity.search-item-record --name alpha`
  - `command --textus.runtime.command.execution-mode sync-direct-no-job --cncf.datastore.sqlite.path=... crud.entity.create-item --name delta --title Delta`
  - `command --cncf.datastore.sqlite.path=... crud.entity.load-item --id "$created_id"`
  - `command crud.meta.describe --format yaml`

## Verified Result

- `sbt --batch clean compile` succeeds
- load help succeeds
- search help succeeds
- seeded load succeeds
- seeded search succeeds
- sync create followed by later load succeeds
- metadata describe succeeds
- `cozy` scripted fixture succeeds with `CRUD_SQLITE_OK`

## Position

This sample keeps the generated CRUD line and adds one storage-specific concern: the same datastore file is reused across separate commands.

Its SQLite verification line is also covered by `cozy` scripted under `crud-sqlite`.
