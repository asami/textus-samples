# 02.f CRUD Nested Value Lab Mini Low Instruction

## Purpose

`02.f-crud-nested-value-lab` is the CRUD lab for nested value object persistence.

This lab is not about aggregate behavior.
This lab is not about single-record aggregate patterns.
This lab is about plain entity CRUD where an entity attribute is typed by a value object, and that value object itself contains another value object.

## Position In Sample Sequence

- `02-crud`: baseline CRUD
- `02.a`: seed import
- `02.b`: simpleentity variation
- `02.c`: sqlite persistence
- `02.d`: server + memory runtime
- `02.e`: explicit sync execution
- `02.f`: nested value object persistence

`02.f` should be described as a CRUD capability lab, not as an aggregate lab.

## Main Story

Use a plain entity model such as:

- `Person`
- `Address`
- `CountryCode`

Typical shape:

- `Person.address: Address`
- `Address.country: CountryCode`

The key point is:

- entity attribute -> value object
- value object attribute -> value object

## What The Lab Should Prove

First completion line:

1. Create or save one entity with nested value object data.
2. Load the entity back.
3. Confirm the nested value object structure is restored.
4. Explain that the persistence shape remains one entity record with embedded nested value data.

Optional:

- search confirmation

Do not require aggregate semantics.

## Important Rules

- Keep this as a documentation and sample-shaping task unless explicitly asked to implement framework changes.
- Do not redesign Cozy/CML.
- Do not redesign simple-modeler.
- Do not redesign CNCF runtime.
- Do not describe this as an aggregate sample.
- Keep the explanation short and concrete.

## Recommended Documentation Focus

Clarify the distinction:

- `06.a-aggregate-single-record-lab`
  - embedded value objects inside a single-record aggregate
- `02.f-crud-nested-value-lab`
  - plain CRUD entity with nested value object attributes

## Success Condition

The resulting direction should make clear:

- why `02.f` belongs in CRUD
- why `02.f` is different from `06.a`
- what the first implementation line should demonstrate
