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
- shell-first command flow under `bin/cncf`
- `run.sh` as a batch wrapper around explicit shell commands
- scripted fixture under `/Users/asami/src/dev2025/cozy/src/sbt-test/cozy/named-view-definition`
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
  - `command named-view-sample.view.search-person-summary-record --city Tokyo`
- custom query route
  - `command named-view-sample.view.search-person --view search_by_city --city Tokyo`
- metadata projection
  - `command named-view-sample.meta.describe --format yaml`
- direct scripted generation path
  - `runMain cozy.Cozy modeler-scala src/main/cozy/test.dox --save=out.d`
- scripted verification
  - `sh check-named-view.sh`

## Observed Results

- help resolves for `load-person-summary`
- `entity.view.summary.Person` and `entity.view.detail.Person` are generated
- `load-person-summary` returns the seeded `Alice` row through `entity.view.summary.Person`
- `load-person-detail` returns the seeded `Alice` row through `entity.view.detail.Person`
- `search-person-summary-record --city Tokyo` returns the Tokyo row through `entity.view.summary.Person`
- `search-person --view search_by_city --city Tokyo` returns the Tokyo row through the custom query alias
- the custom query alias now returns structured query output:
  - `query.condition.city = Tokyo`
- `meta.describe` exposes:
  - `view_names = detail, summary`
  - `queries = search_by_city`
  - `source_events = person.created, person.updated`
  - `rebuildable = true`
- scripted fixture completes with:
  - `NAMED_VIEW_OK`

## Notes

- At this line, predefined named projections also have generated Scala types:
  - `entity.view.summary.<Type>`
  - `entity.view.detail.<Type>`
- runtime behavior now supports projection-fixed load/search by binding named browsers to projection-specific generated modules
- `VIEW > QUERY`, `EVENTS`, and `REBUILDABLE` are now carried through generated metadata and projection surfaces
- `VIEW > QUERY` runtime semantics now normalize typed query conditions before source-entity search so the alias route stays shell-visible without leaking runtime internals into the sample
- generated query models now use `RecordPresentable`, and custom query output is normalized to structured record form instead of internal typed-condition strings
- runtime view cache now applies to named view load/search paths as well, and entity writes invalidate cached view results
- direct `modeler-scala --save` previously emitted `PackageName(...)` into implementation package paths; that generator bug is now fixed at the source
- the scripted fixture now runs without `delegate-launcher`; the fixture root itself acts as the published `cozy` launcher
- the next natural line is event-driven projection / rebuild flow
