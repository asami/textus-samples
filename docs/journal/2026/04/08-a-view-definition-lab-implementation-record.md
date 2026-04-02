# 08.a-view-definition-lab implementation record

## Scope

This line extends `08-view` with explicit named view aliases.

The first completion target was:

1. one named view definition is modeled
2. one projection-fixed summary/detail load route works
3. one projection-fixed summary/detail generated type exists
4. one typed summary search route works
5. one custom `VIEW > QUERY` route works
6. one metadata projection exposes explicit `VIEW` metadata
7. the README explains the difference from `08-view`

## Implemented

- sample source under `/Users/asami/src/dev2026/cncf-samples/samples/08.a-view-definition-lab`
- explicit `VIEW` metadata:
  - `VIEWS :: summary, detail`
  - `EVENTS :: person.created, person.updated`
  - `REBUILDABLE :: true`
  - `QUERY > searchByCity`
- projection-fixed help path
  - `command help named-view-sample.view.load-person-summary`
- projection-fixed load routes
  - `command named-view-sample.view.load-person-summary --id tokyo-sales-entity-person-1742198400000-abcd1234`
  - `command named-view-sample.view.load-person-detail --id tokyo-sales-entity-person-1742198400000-abcd1234`
- projection-fixed search route
  - `command named-view-sample.view.search-person-summary --city Tokyo`
- custom query route
  - `command named-view-sample.view.search-person --view search_by_city --city Tokyo`
- metadata projection
  - `command named-view-sample.meta.describe --format yaml`

## Observed Results

- help resolves for `load-person-summary`
- `entity.view.summary.Person` and `entity.view.detail.Person` are generated
- `load-person-summary` returns the seeded `Alice` row through `entity.view.summary.Person`
- `load-person-detail` returns the seeded `Alice` row through `entity.view.detail.Person`
- `search-person-summary --city Tokyo` returns the Tokyo row through `entity.view.summary.Person`
- `search-person --view search_by_city --city Tokyo` returns the Tokyo row through the custom query alias
- `meta.describe` exposes:
  - `viewNames = detail, summary`
  - `queries = search_by_city`
  - `sourceEvents = person.created, person.updated`
  - `rebuildable = true`

## Notes

- At this line, predefined named projections also have generated Scala types:
  - `entity.view.summary.<Type>`
  - `entity.view.detail.<Type>`
- runtime behavior now supports projection-fixed load/search by binding named browsers to projection-specific generated modules
- `VIEW > QUERY`, `EVENTS`, and `REBUILDABLE` are now carried through generated metadata and projection surfaces
- `VIEW > QUERY` runtime semantics currently work by registering query names as browser aliases and filtering the search record to the `query.*` fields referenced by the query expression
- the next natural line is event-driven projection / rebuild flow
