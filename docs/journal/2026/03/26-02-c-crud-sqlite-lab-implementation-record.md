# 02.c-crud-sqlite-lab Implementation Record

Status:
- `Completed`
- This is an implementation record, not the progress authority.
- The progress authority is `/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/02.c-crud-sqlite-lab.md`.

## Direction

This lab keeps the same model-driven CRUD direction as `02-crud` and uses the CNCF SQLite datastore path for persistence variation.

## Key Changes

- Added the sample under `/Users/asami/src/dev2026/cncf-samples/samples/02.c-crud-sqlite-lab`
- Kept the same model-driven CRUD CML source in `src/main/cozy/crud.cml`
- Wired runtime persistence through `cncf.datastore.sqlite.path`
- Added standard `entity.d` seed import so the SQLite-backed read path can be observed immediately
- Used the generated CRUD surface compiled into the sample so no handwritten repository logic was introduced

## Runtime Verification

Confirmed in:
- `/Users/asami/src/dev2026/cncf-samples/samples/02.c-crud-sqlite-lab`

Commands:

```bash
sbt clean compile
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite crud.entity.create-item --name alpha --title Alpha"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite crud.entity.load-item --id major-minor-entity-item-20260328000000-aaa111"
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=target/cncf.d/02c-crud-sqlite-lab.sqlite crud.entity.search-item-record --name alpha"
```

Observed facts:
- Build succeeded.
- CRUD help resolved.
- `create-item` returned a job-shaped result.
- `load-item` returned the seeded `alpha` item from the SQLite-backed datastore.
- `search-item-record --name alpha` returned the seeded `alpha` item from the SQLite-backed datastore.

## Conclusion

`02.c-crud-sqlite-lab` demonstrates:

- the same model-driven CRUD shape as `02-crud`
- SQLite-backed persistence
- create and read/search confirmation against SQLite-backed data
