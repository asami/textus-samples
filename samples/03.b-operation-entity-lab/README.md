# 03.b-operation-entity-lab

## Overview

This lab shows the first entity-integrated operation contract line.

It keeps the focus on operation modeling, but does so in a component that also owns an entity.

## Scope

- `ENTITY`
- `SERVICE > OPERATION`
- `TYPE = QUERY`
- `INPUT/OUTPUT > VALUE`
- operation help surface in a component with entity metadata

## Verification

`run.sh` confirms:

- `command help operation-entity-sample.person-app.get-person-card`

This keeps the sample on the user-facing operation/help path while showing that entity metadata and operation metadata can coexist in one component.

Verified help return:

- `PersonCard`
