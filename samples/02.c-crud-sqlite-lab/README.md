# 02.c-crud-sqlite-lab

## Overview

`02.c-crud-sqlite-lab` is the SQLite-backed persistence variant of the `02` CRUD line.

It keeps the same generated CRUD surface as `02-crud` and `02.a`, but adds a file-backed datastore path so state can be observed across separate shell commands.

In practice, SQLite is a good default for:

- local development
- test environments
- CI/CD verification

For a small application, SQLite may also remain a reasonable production choice.

## Position

Compared with the earlier CRUD samples:

- `02-crud`
  - shows the generated CRUD surface
- `02.a-crud-seed-import-lab`
  - shows descriptor-first metadata and seed import verification
- `02.b-simpleentity-crud-lab`
  - shows the `SimpleEntity` inspection variant
- `02.c-crud-sqlite-lab`
  - shows persistence across separate commands through a SQLite datastore path

## Intended Use Case

Use this sample when you want to confirm:

- that a generated CRUD component can use SQLite as its backing datastore
- that seeded records are visible through the SQLite-backed entity load/search path
- that a created record can be loaded in a later command by reusing the same SQLite file

Typical use cases are:

- using SQLite as the first datastore in development
- running generated CRUD verification in CI/CD with a disposable file-backed database
- keeping SQLite as-is for a simpler application
- preparing for a later switch to MySQL or another SQL datastore without changing the model, as long as the application does not depend on SQLite-specific SQL behavior

## Files

- `src/main/cozy/crud.cml`
  - the source model
- `entity.d/crud.yaml`
  - imported seed data
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## SQLite Path

The sample uses this datastore file:

```bash
target/cncf.d/02c-crud-sqlite-lab.sqlite
```

Reusing the same file across commands is the point of the lab.

That also makes the sample convenient for automation and CI, because the datastore can be created, observed, and discarded inside the sample workspace.

## How To Run

```bash
$ cd samples/02.c-crud-sqlite-lab
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Load Help

```bash
$ bash ../../bin/cncf --discover=classes command help crud.entity.load-item
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud.entity.load-item`
  - selects the generated entity load operation

Output example:

```yaml
type: operation
name: loadItem
component: Crud
service: entity
selector:
  cli: crud.entity.load-item
returns:
  - Option[Item]
```

### Search Help

```bash
$ bash ../../bin/cncf --discover=classes command help crud.entity.search-item-record
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud.entity.search-item-record`
  - selects the generated entity search operation

### Load Seeded Record Through SQLite

```bash
$ bash ../../bin/cncf --discover=classes command \
    --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite \
    crud.entity.load-item \
    --id major-minor-entity-item-20260328000000-aaa111
```

Parameters:

- `--cncf.datastore.sqlite.path=...`
  - selects the SQLite-backed datastore file
- `crud.entity.load-item`
  - invokes the generated entity load operation
- `--id ...`
  - selects the seeded record from `entity.d/crud.yaml`

Output example:

```yaml
id: major-minor-entity-item-20260328000000-aaa111
name_attributes:
  name: alpha
  title: Alpha
```

This confirms that the seeded record is available through the SQLite-backed read path.

### Search Seeded Record Through SQLite

```bash
$ bash ../../bin/cncf --discover=classes command \
    --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite \
    crud.entity.search-item-record \
    --name alpha
```

Parameters:

- `--cncf.datastore.sqlite.path=...`
  - selects the SQLite-backed datastore file
- `crud.entity.search-item-record`
  - invokes the generated entity search operation
- `--name alpha`
  - filters by the domain attribute `name`

Output example:

```yaml
query:
  condition:
    name: alpha
data:
- id: major-minor-entity-item-20260328000000-aaa111
  name_attributes:
    name: alpha
    title: Alpha
total_count: 1
fetched_count: 1
```

### Create A Record And Re-Load It Through SQLite

```bash
$ created_id=$(
    bash ../../bin/cncf --discover=classes command \
      --textus.runtime.command.execution-mode sync-direct-no-job \
      --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite \
      crud.entity.create-item \
      --name delta \
      --title Delta \
      | awk '/^id: / {print $2}'
  )

$ bash ../../bin/cncf --discover=classes command \
    --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite \
    crud.entity.load-item \
    --id "$created_id"
```

Parameters:

- `--textus.runtime.command.execution-mode sync-direct-no-job`
  - forces immediate command completion instead of returning a job id
- `--cncf.datastore.sqlite.path=...`
  - reuses the same SQLite-backed datastore file
- `crud.entity.create-item`
  - creates the new record
- `--name delta`
  - domain attribute for the created record
- `--title Delta`
  - another domain attribute for the created record
- `created_id`
  - captures the created entity id from the command output

Output example:

```yaml
id: major-minor-entity-item-...
name_attributes:
  name: delta
  title: Delta
```

This is the central persistence check of the sample:

- one command creates a record
- a later command loads the same record
- both commands use the same SQLite file path

## SQLite In Practice

This sample treats SQLite as a practical datastore choice, not only as a demo backend.

That matters in three common cases:

- development
  - no external database process is needed
- CI/CD
  - the database can be created inside the workspace and discarded after verification
- small production systems
  - SQLite may be sufficient without introducing a separate database service

It is also acceptable for many small environments that have some concurrent updates.
In that kind of usage, the question is usually not maximum write throughput but whether the system remains safe and operational without data corruption.
SQLite is often good enough for that level of concurrency.

It also gives a reasonable migration path.
If the application stays within ordinary generated CRUD behavior and does not depend on SQLite-specific SQL, the same model can later be retargeted to MySQL or another SQL datastore mainly through configuration and datastore selection rather than model redesign.

The natural point to move away from SQLite is when concurrent writes become a major operational concern, not merely when the application has any concurrency at all.

### Metadata Describe

```bash
$ bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `crud.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - requests YAML output

## Summary

Use `02.c-crud-sqlite-lab` as the first persistence-variation sample in the `02` line.

It shows:

- the same generated CRUD surface
- a SQLite-backed datastore path
- load/search against seeded data
- create in one command and load in another command through the same SQLite file
