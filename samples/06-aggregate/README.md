# 06-aggregate

## Overview

This sample family is the first dedicated aggregate-oriented slot after `05-job`.

Its purpose is to show aggregate-shaped access as an independent structural topic,
not only as a side note of CRUD or CQRS.

## What It Is For

- aggregate-oriented model shape
- aggregate load access
- aggregate search access
- comparison with plain entity-oriented access when useful

This slot is intended to use the application-join aggregate pattern:

- aggregate is built from multiple entities at application/runtime level

This sample is not intended to use the single-record encoded-object pattern as its main line.
That pattern is also valid in real applications, but it is not the primary explanatory shape here.

## First Completion Line

The first completion line is:

1. one aggregate-oriented sample model exists
2. one aggregate load route works
3. one aggregate search route works
4. the README explains why this is aggregate-oriented rather than plain CRUD
5. the README explains that this sample uses application-join aggregate construction

## Status

This slot is prepared but not implemented yet.

The active work order is:

- `/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/06-aggregate-development-instruction.md`
