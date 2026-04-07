# 02.b-simpleentity-crud-lab Implementation Record

Status: `Completed`

Reviewed and updated on 2026-03-26.
Extended after `02.a-crud-seed-import-lab` on 2026-03-27.

## Summary

`02.b-simpleentity-crud-lab` is a `SimpleEntity` follow-up to `02-crud`.

It keeps the same Cozy/CML method as `textus-user-account`:

- Dox-style CML under `src/main/cozy`
- `cozy`-driven generation
- generated service and entity surfaces instead of hand-written CRUD repository logic

## Changes Made

- created `samples/04.b-simpleentity-crud-lab`
- added `src/main/cozy/crud.cml` in Dox-style model form
- added `build.sbt` using the Cozy plugin pattern and shared CNCF dependency version
- rewrote the README to describe the `SimpleEntity` variation of `02-crud`
- kept service and operation descriptions in the CML after fixing the upstream generator that had emitted unsupported `docSummary` / `docDescription` builder calls

## Verification

`sbt cozyGenerate` and `sbt clean compile` were run in `samples/04.b-simpleentity-crud-lab`.

Observed result:

- the cozy backend found `src/main/cozy/crud.cml`
- `cozyGenerate` succeeded
- the cozy backend generated 9 Scala source files
- `sbt clean compile` succeeded
- compile produced warnings only; no errors remained
- generated Scala under `target/scala-3.3.7/src_managed/main` contained no `docSummary` or `docDescription` calls

Generated source files:

- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/SimpleEntityCrudLabComponent.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/aggregate/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/create/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/operation/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/query/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/read/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/update/Item.scala`
- `target/scala-3.3.7/src_managed/main/org/sample/simpleentitycrudlab/entity/view/Item.scala`

Generated CRUD surface observed in `SimpleEntityCrudLabComponent.scala`:

- `ItemService` with `createItem`, `getItem`, and `listItems`
- `AggregateService` with `createItem`, `loadItem`, `updateItem`, `deleteItem`, and `searchItem`
- `ViewService` with `loadItem`, `loadItemByView`, `searchItem`, and `searchItemRecord`
- `entity` surface with `createItem`, `createItemRecord`, `loadItem`, `loadItemRecord`, `updateItem`, `updateItemRecord`, `deleteItem`, `deleteItemHard`, `searchItem`, and `searchItemRecord`

Runtime probe results:

- `command help SimpleEntityCrudLab` succeeded through `CncfMain --discover=classes`
- `command help SimpleEntityCrudLab.Item` succeeded and exposed `createItem`, `getItem`, and `listItems`
- `command help SimpleEntityCrudLab.Item.createItem` succeeded and exposed the generated operation target
- `command help SimpleEntityCrudLab.system` succeeded and showed `health`, `ping`, and `status`
- `command help SimpleEntityCrudLab.meta` succeeded and showed the built-in meta service
- `command --cncf.format yaml help SimpleEntityCrudLab.Item.createItem` follows the
  framework-parameter namespace rule without changing the domain selector

## Follow-up From 02.a

The later `02.a-crud-seed-import-lab` established two shared rules that also
apply to `02.b`:

- runtime/framework parameters should use the `cncf.*` namespace
- unprefixed parameters should remain available for domain attributes

`02.b` intentionally does not add descriptor-first seed import. That role now
belongs to `02.a`. `02.b` remains focused on the `SimpleEntity`-specific CRUD
shape and generated attribute groups.

## Notes

- the sample is a `SimpleEntity` variation of `02-crud`
- runtime confirmation uses `CncfMain --discover=classes` in the same way as `02-crud`
- runtime/framework parameters now follow the same `cncf.*` namespace rule as `02-crud` and `02.a`
- component, service, and operation selectors are all confirmed
- there is no hand-written CRUD repository logic in this sample
- the original compile failure came from `simplemodeler 1.1.10-SNAPSHOT` generating `docSummary` / `docDescription` calls inside component/service/operation builder code
- the generator was corrected in `/Users/asami/src/dev2025/simple-modeler/src/main/scala/org/simplemodeling/SimpleModeler/generator/scala/ComponentPart.scala`
- after that fix was published locally, the sample compiled with service and operation descriptions restored in `src/main/cozy/crud.cml`
