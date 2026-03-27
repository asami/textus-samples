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
- transport/runtime parameters use the `textus.*` namespace so domain attributes
  such as `name` do not collide with output controls such as `textus.format`
  (`cncf.*` remains accepted as a compatibility alias)

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
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity.load-item"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity.search-item-record"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command crud.entity.load-item --id org-sample-entity-item-20260327000000-aaa111"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command crud.entity.search-item-record"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command crud.entity.search-item-record --name alpha"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --textus.format yaml crud.entity.search-item-record --name alpha"`

The help commands should be run from this sample directory so CNCF can resolve
the local `car.d` and `entity.d` layout.

The target runtime checks are:

- `crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111`
- `crud.entity.search-item-record`
- `crud.entity.search-item-record --name alpha`
- `command --textus.format yaml crud.entity.search-item-record --name alpha`

Help output now distinguishes the formal model name from runtime selectors:

- `name`: formal model name such as `searchItemRecord`
- `selector.canonical`: formal selector such as `Crud.Entity.searchItemRecord`
- `selector.cli`: CLI selector such as `crud.entity.search-item-record`
- `selector.rest`: REST selector such as `/crud/entity/search-item-record`
- `usage`: prefers the kebab-case selector form

Observed results:

- `crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111`
  returns the seeded `alpha` item
- `crud.entity.search-item-record`
  returns the two imported items
- `crud.entity.search-item-record --name alpha`
  returns the seeded `alpha` item only
- `command --textus.format yaml crud.entity.search-item-record --name alpha`
  still applies the domain filter and does not leak `textus.format` into the
  query condition

The phase checklist can be treated as complete for this lab.
