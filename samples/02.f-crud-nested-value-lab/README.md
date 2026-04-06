# 02.f-crud-nested-value-lab

## Overview

`02.f-crud-nested-value-lab` is the nested value persistence variant of the `02` CRUD line.

It stays in plain entity CRUD:

- one entity attribute is a value object
- that value object contains another value object
- the persistence shape remains one entity record
- a later load restores the nested value structure

It is not an aggregate sample.

## Position

Compared with the earlier CRUD samples:

- `02-crud`
  - shows the base generated CRUD surface
- `02.c-crud-sqlite-lab`
  - shows SQLite-backed persistence across separate commands
- `02.f-crud-nested-value-lab`
  - shows SQLite-backed persistence when one attribute is a nested value object

## Intended Use Case

Use this sample when you want to confirm:

- that generated CRUD can accept nested value fields on the create route
- that nested value data can be embedded into one persisted entity record
- that a later load restores the nested shape without introducing an aggregate boundary

Typical use cases are:

- starting with a plain CRUD entity that still has structured value data
- persisting address-like or profile-like fields without redesigning the model as an aggregate
- checking how nested input fields are mapped back into a loaded record

## Files

- `src/main/cozy/crud-nested-value.cml`
  - the source model
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run-create.sh`
  - creates one record with nested value input fields
- `run-load.sh`
  - loads that record back from SQLite
- `run-datastore.sh`
  - runs the create/load roundtrip
- `run.sh`
  - batch wrapper for the documented shell commands

## Nested Value Shape

The sample model uses this nesting:

- `Person.address: Address`
- `Address.country: CountryCode`

The shell input therefore uses nested field names:

- `--address.street`
- `--address.city`
- `--address.country.value`

The important point is that this still remains one CRUD entity record.

## How To Run

```bash
$ cd samples/02.f-crud-nested-value-lab
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Create Help

```bash
$ bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.create-person
```

Parameters:

- `--discover=classes`
  - tells `bin/cncf` to load the generated classes from the sample build output
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `crud-nested-value-sample.entity.create-person`
  - selects the generated create operation

Output example:

```yaml
type: operation
name: createPerson
selector:
  cli: crud-nested-value-sample.entity.create-person
returns:
  - unit
```

### Load Help

```bash
$ bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.load-person
```

Parameters:

- `crud-nested-value-sample.entity.load-person`
  - selects the generated load operation

### Metadata Describe

```bash
$ bash ../../bin/cncf --discover=classes command crud-nested-value-sample.meta.describe --format yaml
```

Parameters:

- `crud-nested-value-sample.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - asks for structured YAML output

Observed output points:

```yaml
services:
- name: entity
  runtime_name: entity
aggregates:
- name: person
  entity_name: person
views:
- name: person
  entity_name: person
```

### Create A Person With Nested Value Fields

```bash
$ bash run-create.sh
```

Parameters:

- `--cncf.datastore.sqlite.path=target/cncf.d/02f-crud-nested-value-lab.sqlite`
  - uses the SQLite datastore file for the sample
- `--textus.runtime.command.execution-mode sync-direct-no-job`
  - returns immediately instead of using a job id
- `--name alice`
  - sets the top-level entity field
- `--address.street Marunouchi-1-2-3`
  - sets the nested street field
- `--address.city Tokyo`
  - sets the nested city field
- `--address.country.value JP`
  - sets the nested nested country value field

Output example:

```yaml
id: major-minor-entity-person-1775440349468-3QMuFFVVBWIwWNlwtRCgWr
```

### Load The Persisted Person

```bash
$ bash run-load.sh major-minor-entity-person-1775440349468-3QMuFFVVBWIwWNlwtRCgWr
```

Parameters:

- `--cncf.datastore.sqlite.path=target/cncf.d/02f-crud-nested-value-lab.sqlite`
  - reuses the same SQLite-backed datastore file
- `--id ...`
  - loads the created person record

Output example:

```yaml
id: major-minor-entity-person-1775440349468-3QMuFFVVBWIwWNlwtRCgWr
name: alice
address:
  street: Marunouchi-1-2-3
  city: Tokyo
  country:
    value: JP
```

This is the main confirmation line of the sample.
The nested value structure is restored through a later CRUD load.

### Run The Whole Roundtrip

```bash
$ bash run-datastore.sh
```

This script:

- clears the SQLite file
- creates one person with nested value input
- captures the returned `id`
- loads the person again through the same datastore

## Relation To Other Samples

- `02.c-crud-sqlite-lab`
  - focuses on CRUD persistence across commands
- `02.f-crud-nested-value-lab`
  - adds nested value restoration to the same SQLite-backed CRUD line
- `07.a-aggregate-single-record-lab`
  - is a different direction because it demonstrates an aggregate boundary
