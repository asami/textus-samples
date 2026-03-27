# 02.a-crud-seed-import-lab

## Overview

This lab is reserved for the CNCF initial data import flow.

It is intended to sit between `02-crud` and `02.b-simpleentity-crud-lab`.

## Status

This lab is wired to import seed data from `entity.d`.

The static runtime metadata is supplied through:

- `car.d/meta/component-descriptor.yaml`

This is the local development/test form of the same `ComponentDescriptor`
concept that is intended to live in CAR by default.

The generated runtime surface is present, and the descriptor-first wiring now
comes from `car.d/meta/component-descriptor.yaml` instead of a sample-local
Scala wrapper factory.

Current verification state:

- `load` is confirmed against imported seed data
- `search` is confirmed against imported seed data
- transport/runtime parameters use the `cncf.*` namespace so domain attributes
  such as `name` do not collide with output controls such as `cncf.format`

## Intended Shape

- model-driven sample
- Cozy/CML input under `src/main/cozy`
- `ComponentDescriptor` under `car.d/meta`
- preloaded seed data before runtime verification
- runtime focus on `load` and `search`

## Difference from the Other CRUD Labs

- `02-crud` focuses on the base CRUD model surface
- `02.a` focuses on initial data import plus `load` / `search`
- `02.b` focuses on the `SimpleEntity` variation

## Expected Commands

The following commands are the current verification set:

- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.entity"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.entity.loadItem"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.entity.searchItemRecord"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command Crud.entity.loadItem --id org-sample-entity-item-20260327000000-aaa111"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command Crud.entity.searchItemRecord"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command Crud.entity.searchItemRecord --name alpha"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.format yaml Crud.entity.searchItemRecord --name alpha"`

The help commands should be run from this sample directory so CNCF can resolve
the local `car.d` and `entity.d` layout.

The target runtime checks are:

- `Crud.entity.loadItem --id major-minor-entity-item-20260327000000-aaa111`
- `Crud.entity.searchItemRecord`
- `Crud.entity.searchItemRecord --name alpha`
- `command --cncf.format yaml Crud.entity.searchItemRecord --name alpha`

Observed results:

- `Crud.entity.loadItem --id major-minor-entity-item-20260327000000-aaa111`
  returns the seeded `alpha` item
- `Crud.entity.searchItemRecord`
  returns the two imported items
- `Crud.entity.searchItemRecord --name alpha`
  returns the seeded `alpha` item only
- `command --cncf.format yaml Crud.entity.searchItemRecord --name alpha`
  still applies the domain filter and does not leak `cncf.format` into the
  query condition

The phase checklist can be treated as complete for this lab.
