# 03.b-operation-entity-lab

## Overview

`03.b-operation-entity-lab` is the first sample that connects an operation contract to an entity model.

It follows:

- `03-operation`
  - minimal query contract
- `03.a-operation-command-lab`
  - command contract plus job-based execution
- `03.b-operation-entity-lab`
  - query operation plus entity model in the same component

This is still a small sample.
It does not try to become a CRUD sample.
Its point is to show how a user-facing operation can sit on top of an entity-oriented domain model.

## Position

- `03-operation`
  - operation contract only
- `03.a-operation-command-lab`
  - CQRS `C` side contract and async command flow
- `03.b-operation-entity-lab`
  - CQRS `Q` side contract connected to an entity model

## Intended Use Case

Use this sample when you want to confirm:

- a component can own both an entity model and an operation service
- a query-shaped operation can expose its own typed input/output contract
- the operation layer can translate from entity-oriented data into a user-facing result contract
- operation metadata and entity metadata can coexist without turning the sample into a CRUD demo

Typical use cases are:

- teaching the boundary between application-facing operations and entity models
- showing that a query operation can return a smaller contract than the underlying entity shape
- preparing for later samples where operations and entities interact more deeply

## Files

- `src/main/cozy/operation-entity.cml`
  - the source model
- `src/main/scala/org/sample/operationentity/OperationEntitySampleFactory.scala`
  - sample-specific factory that provides the minimal executable query behavior
- `build.sbt`
  - enables `sbt-cozy` generation for the sample
- `run.sh`
  - batch wrapper for the documented shell commands

## How To Run

```bash
$ cd samples/03.b-operation-entity-lab
$ ../../bin/setup cozy
$ sbt --batch clean compile
$ bash run.sh
```

## Command Walkthrough

### Operation Help

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationentity.OperationEntitySampleFactory command help operation-entity-sample.person-app.get-person-card
```

Parameters:

- `--component-factory-class org.sample.operationentity.OperationEntitySampleFactory`
  - uses the sample-specific factory that provides the executable query behavior
- `command`
  - uses the ordinary CNCF command path
- `help`
  - asks CNCF to describe the selected runtime target
- `operation-entity-sample.person-app.get-person-card`
  - selects the generated query operation

Output example:

```yaml
type: operation
name: getPersonCard
summary: Operation: PersonApp.getPersonCard
component: OperationEntitySample
service: PersonApp
selector:
  cli: operation-entity-sample.person-app.get-person-card
returns:
  - PersonCard
```

This confirms the contract surface:

- service name: `PersonApp`
- operation name: `getPersonCard`
- output type: `PersonCard`

### Metadata Describe

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationentity.OperationEntitySampleFactory command operation-entity-sample.meta.describe --format yaml
```

Parameters:

- `operation-entity-sample.meta.describe`
  - invokes the generated metadata service
- `--format yaml`
  - asks for structured YAML output

Output example:

```yaml
services:
- name: PersonApp
  runtime_name: person-app
operation_definitions:
- name: getPersonCard
  kind: QUERY
  input_type: PersonLookup
  output_type: PersonCard
  input_value_kind: QUERY_VALUE
```

This confirms that the sample is still operation-oriented even though the component owns an entity model too.

### Execute The Query Operation

```bash
$ bash ../../bin/cncf --component-factory-class org.sample.operationentity.OperationEntitySampleFactory command operation-entity-sample.person-app.get-person-card --person-id major-minor-entity-person-1742198400000-abcd1234
```

Parameters:

- `operation-entity-sample.person-app.get-person-card`
  - invokes the application-facing query operation
- `--person-id`
  - supplies the entity identifier accepted by `PersonLookup`

Output example:

```yaml
name: Alice
```

This is the main point of the sample:

- the input contract is entity-oriented because it takes `personId`
- the operation uses the entity model internally
- the returned contract is smaller and user-facing because it returns `PersonCard`

## What This Sample Does Not Try To Show

The sample intentionally avoids:

- full CRUD behavior
- search and paging
- job handling
- persistence-specific concerns

Those concerns belong to later samples.
