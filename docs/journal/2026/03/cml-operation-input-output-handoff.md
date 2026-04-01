# CML Operation Input / Output Handoff

- date: 2026-03-31
- status: draft

## Purpose

This note records the current problem around `OPERATION` sections in CML
and prepares the discussion for a refactoring of the syntax and semantics.

The immediate trigger is the current style in `cqrs.cml`:

```text
#### createItem

##### DESCRIPTION

Creates a new item through the command side.

##### IN

Item creation payload.

##### OUT

Command job result.
```

## Current Problem

`IN` and `OUT` are currently acting only as explanatory text.

That is too weak for `OPERATION`.

An operation needs at least:

- parameter definition
- return value definition

The current `IN` / `OUT` blocks look more like:

- input summary / description
- output summary / description

than actual structural definitions.

## Core Question

What should `INPUT` / `OUTPUT` mean in CML `OPERATION`?

Two distinct needs exist:

1. structural contract
   - parameters
   - return value
2. explanatory text
   - summary
   - description

The current notation mixes these.

## Working Interpretation

At the moment, the best interpretation is:

- current `IN`
  - input-side summary or description
- current `OUT`
  - output-side summary or description

This means the current syntax is not yet sufficient as an operation contract.

## Refactoring Direction To Examine

### Direction A

Keep `INPUT` / `OUTPUT`, but make them structural.

Example:

```text
##### INPUT

CreateItem

##### OUTPUT

CreateItemResult
```

In this direction:

- `INPUT` / `OUTPUT`
  - type reference or structural definition
- explanatory text
  - moves into `DESCRIPTION` or field descriptions

### Direction B

Separate parameter and result definitions explicitly.

Example:

```text
##### PARAMETER

| name | type | multiplicity | description |
|------+------|--------------+-------------|
| name | name | 1            | Item name.  |

##### RETURN

| name | type     | multiplicity | description      |
|------+----------+--------------+------------------|
| id   | entityid | 1            | Created item id. |
```

In this direction:

- operation contract becomes self-contained
- type references become optional rather than mandatory

### Direction C

Support both:

- type-based declaration
- inline parameter / return declaration

This is the most flexible direction, but also the heaviest.

## Evaluation Criteria

The discussion should evaluate at least:

- readability in SmartDox / CML
- consistency with existing `QUERY` / `ENTITY` / `VALUE` modeling
- generator friendliness
- help / CLI surface generation
- compatibility with command/query distinction
- compatibility with future client/server introspection

## Immediate Recommendation

For discussion purposes, start from this assumption:

- `INPUT` / `OUTPUT` should represent structural contract
- current text-only `IN` / `OUT` should be treated as description-like legacy usage

That gives a cleaner direction for operation modeling.

## Out Of Scope For This Handoff

- final grammar decision
- parser implementation
- generator implementation
- migration mechanics

This handoff is only for design discussion.
