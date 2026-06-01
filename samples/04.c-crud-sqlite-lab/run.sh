#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dbpath="target/cncf.d/02c-crud-sqlite-lab.sqlite"
mkdir -p target/cncf.d
rm -f "$dbpath"

cncf dev command --project . help crud.entity.load-item
cncf dev command --project . help crud.entity.search-item-record
cncf dev command --project . --cncf.datastore.sqlite.path="$dbpath" crud.entity.load-item --id major-minor-entity-item-20260328000000-aaa111
cncf dev command --project . --cncf.datastore.sqlite.path="$dbpath" crud.entity.search-item-record --name alpha

created_id=$(
  cncf dev command --project . \
    --textus.command.execution-mode sync-direct-no-job \
    --cncf.datastore.sqlite.path="$dbpath" \
    crud.entity.create-item \
    --name delta \
    --title Delta \
    | awk '/^id: / {print $2}'
)

cncf dev command --project . --cncf.datastore.sqlite.path="$dbpath" crud.entity.load-item --id "$created_id"
cncf dev command --project . crud.meta.describe --format yaml
