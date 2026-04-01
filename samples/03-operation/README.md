# 03-operation

## Overview

This lab is the user-facing slot for CML operation contract modeling.

The first line focuses on:

- `OPERATION`
- `INPUT`
- `OUTPUT`
- `TYPE`
- `SUMMARY`
- `DESCRIPTION`

This lab should show the intended CML usage for operation contracts,
not internal parser/model workarounds.

## Position

- `02.f-crud-nested-value-lab`
  - typed CRUD/value modeling just before operation contract
- `03-operation`
  - operation contract modeling in CML
- `04-cqrs`
  - command/query separation built on operation contracts

## Current State

The sample now shows the intended user-facing structure with a minimal query example:

- `SERVICE > OPERATION`
- `INPUT > VALUE`
- `OUTPUT > VALUE`
- `SUMMARY`
- `DESCRIPTION`
- `greeting(name) -> message`

The example intentionally avoids:

- entity integration
- CRUD generation
- command behavior

so the grammar and Cozy implementation can be developed first.

`run.sh` is now user-facing and shows:

- `command help operation-contract-sample.greeting.greeting`

The implemented grammar line is verified by:

- `kaleidox` test
- `cozy` generation test
- the runnable sample path above

## Closure

The `03` line is now covered by:

- [03-operation](/Users/asami/src/dev2026/cncf-samples/samples/03-operation)
- [03.a-operation-command-lab](/Users/asami/src/dev2026/cncf-samples/samples/03.a-operation-command-lab)
- [03.b-operation-entity-lab](/Users/asami/src/dev2026/cncf-samples/samples/03.b-operation-entity-lab)

Verified help returns:

- `GreetingResult`
- `GreetingAccepted`
- `PersonCard`
