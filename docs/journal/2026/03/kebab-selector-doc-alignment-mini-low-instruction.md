# Kebab Selector Doc Alignment Mini-Low Instruction

Status: Active Instruction

## Goal

Align sample documents with the current selector/help direction:

- formal model names stay formal
- CLI usage prefers kebab-case selectors
- mixed-case selectors may remain accepted, but they should not be the primary
  CLI examples

This is a documentation-alignment task. Do not change runtime behavior unless a
concrete bug is discovered.

## Read First

- [/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/help-canonical-name-and-selector-direction.md](/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/help-canonical-name-and-selector-direction.md)
- [/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/help-selector-format-mini-low-instruction.md](/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/help-selector-format-mini-low-instruction.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02-crud/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02.a-crud-seed-import-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/02.b-simpleentity-crud-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/04-cqrs/README.md](/Users/asami/src/dev2026/cncf-samples/samples/04-cqrs/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/04.a-designed-sync-command-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/04.a-designed-sync-command-lab/README.md)
- [/Users/asami/src/dev2026/cncf-samples/samples/04.b-test-sync-command-lab/README.md](/Users/asami/src/dev2026/cncf-samples/samples/04.b-test-sync-command-lab/README.md)

## Required Outcome

The affected sample documents should:

- keep formal names such as `Crud`, `Entity`, `searchItemRecord`
- use kebab-case for primary CLI usage examples, such as:
  - `crud.entity.search-item-record`
  - `designed-sync.item.create-item`
- avoid presenting mixed-case selector strings as the main CLI form

## Work Steps

1. Update the listed sample READMEs so CLI examples prefer kebab-case selectors.
2. Where useful, mention the formal names separately from CLI selector examples.
3. If any phase or implementation record still treats mixed-case selector strings as the primary CLI form, update those docs too.
4. Keep the explanation consistent with current help output direction.

## Do Not

- Do not change resolver logic.
- Do not change generator logic.
- Do not rename model-level component/service/operation names.
- Do not rewrite every historical document in the repository.
- Do not change sample semantics.

## Minimum Verification

For at least one updated sample, verify that the kebab-case example shown in the
README actually resolves at runtime.

Example:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help crud.entity.search-item-record"
```

## Report Back Only

- what files you changed
- which mixed-case examples were replaced with kebab-case usage
- what runtime command you used to verify the updated docs
- what still remains, if anything
