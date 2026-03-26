# 02-crud Redesign Record

Status: `Completed`

Reviewed and updated on 2026-03-26.

## Summary

`02-crud` was redefined to follow the same `textus-user-account` method:

- model in CML
- `cozy` as the required tool
- CNCF-generated entity / aggregate surface
- no hand-written CRUD repository logic as the main idea

## Changes Made

- removed the hand-written CRUD Scala implementation
- added `project/plugins.sbt` with the `sbt-cozy` plugin
- added `build.sbt` using the cozy plugin pattern
- added `src/main/cozy/crud.cml`
- rewrote the README for the model-driven flow

## Verification Attempt

`sbt cozyGenerate`, `sbt clean compile`, and runtime help probes were run after publishing the local generator/runtime fixes.

Observed result:

- the cozy backend found `src/main/cozy/crud.cml`
- `cozyGenerate` succeeded
- the cozy backend generated 9 Scala source files
- `sbt clean compile` succeeded
- compile produced warnings only; no errors remained
- `CncfMain` was extended to accept `--component-factory-class`
- the generated `CrudComponent$Factory` was injected at runtime through `CncfMain`

Generated source files:

- `target/scala-3.3.7/src_managed/main/org/sample/crud/CrudComponent.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/aggregate/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/create/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/operation/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/query/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/read/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/update/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/crud/entity/view/Item.scala`

Generated CRUD surface observed in `CrudComponent.scala`:

- `ItemService` with `createItem`, `getItem`, and `listItems`
- `AggregateService` with `createItem`, `loadItem`, `updateItem`, `deleteItem`, and `searchItem`
- `ViewService` with `loadItem`, `loadItemByView`, `searchItem`, and `searchItemRecord`
- `entity` surface with `createItem`, `createItemRecord`, `loadItem`, `loadItemRecord`, `updateItem`, `updateItemRecord`, `deleteItem`, `deleteItemHard`, `searchItem`, and `searchItemRecord`

Runtime probe results after the `CncfMain` extension:

- `command help Crud` succeeded and showed the `Crud` component help
- `command help Crud.Item` succeeded and showed the generated `Item` service
- `command help Crud.Item.createItem` succeeded and showed the generated operation help
- the component help exposed `Item`, `aggregate`, `entity`, `meta`, `system`, and `view`
- the generated/runtime CRUD surface is therefore confirmed

## Notes

- the redesign direction is correct
- the first blocker was the compact DSL model
- the second blocker was generated source compile issues
- the third blocker was that `02-crud` had no direct runtime entry point for the generated component factory
- that gap was resolved by extending `CncfMain` with `--component-factory-class`
- runtime command-style CRUD help confirmation is now verified
