# CNCF Discover Classes Factory Mini-Low Instruction

Status: `Active Instruction`

Reviewed and updated on 2026-03-26.

## Purpose

Improve CNCF class discovery so that model-driven generated components can run with `--discover=classes` alone.

Today, generated samples such as `02-crud` and `02.a-simpleentity-crud-lab` work when `CncfMain` is given:

- `--component-factory-class=org.sample.crud.CrudComponent$Factory`
- `--component-factory-class=org.sample.simpleentitycrudlab.SimpleEntityCrudLabComponent$Factory`

The desired behavior is that `--discover=classes` can discover the component and use the companion `Factory` automatically.

## Repositories

- CNCF runtime repo:
  - `/Users/asami/src/dev2025/cloud-native-component-framework`
- Reference samples:
  - `/Users/asami/src/dev2026/cncf-samples/samples/02-crud`
  - `/Users/asami/src/dev2026/cncf-samples/samples/02.a-simpleentity-crud-lab`

## Read First

- `/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/CncfMain.scala`
- `/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/component/repository/ComponentProvider.scala`
- `/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md`
- `/Users/asami/src/dev2026/cncf-samples/samples/02.a-simpleentity-crud-lab/README.md`

## Problem Statement

There is an existing rule in CNCF:

- a component can provide `Factory` from its companion object

However, in the current runtime behavior, model-driven generated components are not fully usable through `--discover=classes` alone.

Observed current state:

- `--component-factory-class=...` works
- `--discover=classes` does not reliably expose the generated CRUD runtime surface in the same way

This means companion-factory discovery is not being applied effectively for generated components.

## Task

Make the runtime automatically use the companion `Factory` when class discovery finds a generated component class.

Target behavior:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.Item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.Item.createItem"

sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help SimpleEntityCrudLab"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help SimpleEntityCrudLab.Item"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help SimpleEntityCrudLab.Item.createItem"
```

The goal is not to remove `--component-factory-class`.

The goal is:

- `--component-factory-class` remains as an explicit escape hatch
- `--discover=classes` becomes sufficient for generated component samples in normal development usage

## Do

1. Inspect how class discovery currently resolves component classes.
2. Inspect how generated component classes expose their companion `Factory`.
3. Update CNCF runtime so that discovered generated components can contribute their factory-produced components automatically.
4. Prefer a runtime-side fix in CNCF.
5. Keep existing explicit `--component-factory-class` support working.

## Do Not

- Do not change `02-crud` back to hand-written CRUD logic.
- Do not rewrite the sample READMEs first and call that done.
- Do not remove `--component-factory-class` support.
- Do not hardcode sample-specific class names into CNCF.

## Acceptance Criteria

- `02-crud` works with `--discover=classes` only for:
  - `Crud`
  - `Crud.Item`
  - `Crud.Item.createItem`
- `02.a-simpleentity-crud-lab` works with `--discover=classes` only for:
  - `SimpleEntityCrudLab`
  - `SimpleEntityCrudLab.Item`
  - `SimpleEntityCrudLab.Item.createItem`
- Existing `--component-factory-class=...` still works
- The fix is recorded in a CNCF-side implementation note or commit message

## Report Back

Report only these facts:

- what prevented companion-factory discovery from working
- what files were changed in CNCF
- whether `02-crud` now works with `--discover=classes`
- whether `02.a-simpleentity-crud-lab` now works with `--discover=classes`
- what still remains, if anything
