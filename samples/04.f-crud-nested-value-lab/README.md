# 02.f-crud-nested-value-lab

## Overview

`04.f-crud-nested-value-lab` is the nested value persistence variant of the `02` CRUD line.

It stays in plain entity CRUD:

- one entity attribute is a value object
- that value object contains another value object
- the persistence shape remains one entity record
- a later load restores the nested value structure

It is not an aggregate sample.

## Position

Compared with the earlier CRUD samples:

- `04-crud`
  - shows the base generated CRUD surface
- `04.c-crud-sqlite-lab`
  - shows SQLite-backed persistence across separate commands
- `04.f-crud-nested-value-lab`
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

## Setup

### 1. Prepare the `cozy` command

Prepare the local `cozy` launcher that `sbt-cozy` delegates to during generation.

```bash
$ cd samples/04.f-crud-nested-value-lab
$ ../../bin/setup cozy
```

### 2. Build the generated sample

Compile the sample and generate the runtime classes that `cncf --discover=classes` will use later.

```bash
$ cd samples/04.f-crud-nested-value-lab
$ sbt --batch clean compile
```

## Run The Whole Scenario

```bash
$ cd samples/04.f-crud-nested-value-lab
$ bash run.sh
```

`run.sh` is only a convenience batch runner.

It is the batch form of the walkthrough below.

The main learning path is still the explicit shell sequence in `Command Walkthrough`.

## Command Walkthrough

This sample uses:

```bash
bash ../../bin/cncf --discover=classes ...
```

Common points:

- `cncf`:
  - the standard CNCF command-line entry point
  - in this sample repository it is invoked through `../../bin/cncf`
  - after a normal CNCF installation, the same command is expected to be available as `cncf`
- `--discover=classes`:
  - use the locally compiled generated classes under `target/`
  - this is the local sample-friendly way to run the generated component without first packaging and installing a separate artifact
- `command`:
  - run one-shot CNCF command execution without starting a persistent server
- `help`:
  - ask CNCF to describe the selected component, service, or operation instead of executing it

### Create Help

```bash
$ bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.create-person
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

- `crud-nested-value-sample.entity.load-person`
  - selects the generated load operation

### Metadata Describe

```bash
$ bash ../../bin/cncf --discover=classes command crud-nested-value-sample.meta.describe --format yaml
```

Parameters:
- `command`
  - uses ordinary one-shot CNCF command execution for this step

- `help`
  - asks CNCF to describe the selected component, service, or operation instead of executing it

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
- `--textus.command.execution-mode sync-direct-no-job`
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

- `04.c-crud-sqlite-lab`
  - focuses on CRUD persistence across commands
- `04.f-crud-nested-value-lab`
  - adds nested value restoration to the same SQLite-backed CRUD line
- `09.a-aggregate-single-record-lab`
  - is a different direction because it demonstrates an aggregate boundary
