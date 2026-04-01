# 08.a-view-definition-lab

## Overview

This lab is the first extension line after `08-view`.

Its purpose is to show explicit view-definition metadata, especially:

- named view aliases
- explicit `VIEW` metadata beyond the default generated view
- view metadata such as `QUERY`, `EVENTS`, and `REBUILDABLE`
- named view selection through the runtime view DSL

## Position

- `08-view`
  - default generated view load/search
- `08.a-view-definition-lab`
  - named view definition

## First Completion Line

The first completion line is:

1. one named view model exists
2. one projection-fixed summary/detail generated type exists
3. one projection-fixed summary/detail load route works
4. one projection-fixed summary search route works
5. one custom `VIEW > QUERY` route works
6. one metadata projection shows explicit `VIEW` metadata
7. the README explains how this differs from the default `08-view` line

## Status

Implemented to the first named-view-definition line.

## How To Run

Use:

```bash
bash run.sh
```

This checks:

- `command help named-view-sample.view.load-person-summary`
- `command named-view-sample.view.load-person-summary --id tokyo-sales-entity-person-1742198400000-abcd1234`
- `command named-view-sample.view.search-person-summary --city Tokyo`
- `command named-view-sample.view.search-person --view search_by_city --city Tokyo`
- `command named-view-sample.view.load-person-detail --id tokyo-sales-entity-person-1742198400000-abcd1234`
- `command named-view-sample.meta.describe --format yaml`

## Difference From 08-view

`08-view` demonstrates the default generated read-model projection.

This lab adds explicit `VIEW` metadata:

- `VIEWS :: summary, detail`
- `EVENTS :: person.created, person.updated`
- `REBUILDABLE :: true`
- `QUERY > searchByCity`

At this line, named views are exposed in two ways:

- dynamic alias selection through `--view summary`
- projection-fixed operations such as `load-person-summary` and `load-person-detail`

The structural point is that named view aliases and explicit query/source-event metadata are now part of the component metadata, while predefined projections also have dedicated generated Scala types under `view.summary.*` and `view.detail.*`.

Observed results:

- `load-person-summary` returns the seeded `Alice` row through `view.summary.Person`
- `search-person-summary --city Tokyo` returns projected rows through `view.summary.Person`
- `search-person --view search_by_city --city Tokyo` returns only rows selected by the custom view-query alias
- `load-person-detail` returns the seeded `Alice` row through `view.detail.Person`
- `meta.describe` exposes:
  - `viewNames`
  - `queries`
  - `sourceEvents`
  - `rebuildable`

The next line after this sample is:

- event-driven projection / rebuild flow
