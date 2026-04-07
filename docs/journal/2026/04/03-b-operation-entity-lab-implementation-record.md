# 03.b Operation Entity Lab Implementation Record

## Summary

`05.b-operation-entity-lab` was normalized as the first operation-plus-entity sample.

The sample now verifies both:

- the generated help and metadata surface for one minimal query operation
- the smallest executable line where an operation accepts an entity id and returns a user-facing result contract

## What Changed

- rewrote the README as a shell-first operation-to-entity sample
- replaced the old sample-runner wrapper with direct `bin/cncf` commands
- added a sample-specific factory that provides the minimal query behavior
- added a checklist for the rework status

## Verified Commands

- `bash ../../bin/cncf --component-factory-class org.sample.operationentity.OperationEntitySampleFactory command help operation-entity-sample.person-app.get-person-card`
- `bash ../../bin/cncf --component-factory-class org.sample.operationentity.OperationEntitySampleFactory command operation-entity-sample.meta.describe --format yaml`
- `bash ../../bin/cncf --component-factory-class org.sample.operationentity.OperationEntitySampleFactory command operation-entity-sample.person-app.get-person-card --person-id major-minor-entity-person-1742198400000-abcd1234`
- `bash run.sh`

## Observed Output

Help confirms:

- `service: PersonApp`
- `name: getPersonCard`
- `returns: PersonCard`

Metadata confirms:

- `runtime_name: person-app`
- `kind: QUERY`
- `input_type: PersonLookup`
- `output_type: PersonCard`
- `input_value_kind: QUERY_VALUE`

Query execution confirms:

- the operation accepts `personId`
- the returned contract is reduced to `name: Alice`

## Main Point

`03.b` is the first sample where an operation contract and an entity model live in the same component.
It shows that an application-facing query can take an entity-oriented identifier and still return a smaller user-facing contract.
