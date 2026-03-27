# Request Core And Kebab Selector Mini-Low Instruction

Status: Active Instruction

## Goal

Apply these two design corrections together:

1. `org.goldenport.protocol.Request` is core and must not know product-specific prefixes such as `textus.` or `cncf.`.
2. Runtime selector names should use `kebab-case` as the canonical form for `component.service.operation`.

This instruction is for `mini low`. Keep the work narrow and factual.

## Read First

- [/Users/asami/src/dev2025/simplemodeling-lib/src/main/scala/org/goldenport/protocol/Request.scala](/Users/asami/src/dev2025/simplemodeling-lib/src/main/scala/org/goldenport/protocol/Request.scala)
- [/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/cli/CncfRuntime.scala](/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/cli/CncfRuntime.scala)
- [/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/directive/Query.scala](/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/directive/Query.scala)
- [/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/subsystem/resolver/OperationResolver.scala](/Users/asami/src/dev2025/cloud-native-component-framework/src/main/scala/org/goldenport/cncf/subsystem/resolver/OperationResolver.scala)
- [/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/03-cqrs/README.md](/Users/asami/src/dev2026/cncf-samples/samples/03-cqrs/README.md)

## Required Outcome

### 1. Request stays product-neutral

`Request` must not hard-code:

- `textus.`
- `cncf.`
- any other product/runtime prefix

Instead:

- `Request.toRecord(...)` must accept an exclusion rule from the caller
- the caller decides which prefixes are framework parameters

Acceptable forms:

- `toRecord(excludeProperty = name => ...)`
- `toRecord(propertyFilter = ...)`
- another small API with the same effect

The important rule is:

- core `Request` does not know product names
- CNCF/Textus passes the exclusion logic from outside

### 2. Kebab-case is canonical for selectors

Canonical runtime selector form should be:

- `component-name.service-name.operation-name`

Examples:

- `crud.entity.search-item-record`
- `designed-sync.item.create-item`

Keep compatibility with current names for now if needed, but:

- help output
- README examples
- canonical selector rendering

should prefer `kebab-case`.

## Work Steps

1. Refactor `Request` so product prefixes are not hard-coded in core.
2. Update CNCF call sites so they pass the framework/query exclusion rule explicitly.
3. Confirm that `textus.*`, `cncf.*`, and `query.*` are excluded from domain query conditions after the refactor.
4. Identify where selector names are rendered/resolved.
5. Change canonical selector rendering to `kebab-case`.
6. Keep existing selectors working if that is easy, but do not add large compatibility wrappers.
7. Update at least one sample README to show the new canonical selector style.

## Do Not

- Do not keep product-specific prefix knowledge inside `Request`.
- Do not introduce a large compatibility layer.
- Do not rewrite all samples at once.
- Do not break already working `textus.*` and `cncf.*` parameter handling.
- Do not change Scala class names to kebab-case.

## Minimum Verification

### Request/core side

Verify a sample command like:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --textus.format yaml Crud.entity.searchItemRecord --name alpha"
```

passes with:

- `textus.format` not appearing in the domain `Query(...)`

Also verify the same with:

```bash
--cncf.format yaml
```

### Selector side

Verify that canonical help/rendering shows kebab-case for at least one component/service/operation path.

If compatibility is preserved, verify that old mixed-case input still resolves.

## Report Back Only

- what files you changed
- how `Request.toRecord(...)` was changed
- where the framework/query exclusion logic now lives
- what selector rendering/resolution was changed for kebab-case
- what commands you used to verify behavior
- what remains unfinished, if anything
