#!/bin/sh

set -eu

dbpath="target/cncf.d/02c-crud-sqlite-lab.sqlite"
mkdir -p target/cncf.d
rm -f "$dbpath"

bash ../../bin/cncf --discover=classes command help crud.entity.load-item
bash ../../bin/cncf --discover=classes command help crud.entity.search-item-record
bash ../../bin/cncf --discover=classes command --cncf.datastore.sqlite.path="$dbpath" crud.entity.load-item --id major-minor-entity-item-20260328000000-aaa111
bash ../../bin/cncf --discover=classes command --cncf.datastore.sqlite.path="$dbpath" crud.entity.search-item-record --name alpha

created_id=$(
  bash ../../bin/cncf --discover=classes command \
    --textus.command.execution-mode sync-direct-no-job \
    --cncf.datastore.sqlite.path="$dbpath" \
    crud.entity.create-item \
    --name delta \
    --title Delta \
    | awk '/^id: / {print $2}'
)

bash ../../bin/cncf --discover=classes command --cncf.datastore.sqlite.path="$dbpath" crud.entity.load-item --id "$created_id"
bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml
