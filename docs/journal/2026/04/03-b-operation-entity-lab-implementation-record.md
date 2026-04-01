# 03.b-operation-entity-lab Implementation Record

- date: 2026-04-01
- status: done

## Purpose

Fix the first entity-integrated operation line after:

- `03-operation`
- `03.a-operation-command-lab`

## Target

- entity metadata and operation metadata in the same component
- query-shaped operation contract
- user-facing help path

## Verification

- `bash run.sh`
- `command help operation-entity-sample.person-app.get-person-card`

Confirmed:

- `ENTITY`
- `SERVICE > OPERATION`
- `TYPE = QUERY`
- `INPUT > VALUE`
- `OUTPUT > VALUE`
- user-facing operation help works in a component that also owns an entity
- `returns: PersonCard`
