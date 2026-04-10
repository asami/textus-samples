#!/bin/sh

set -eu

dbpath="target/cncf.d/02f-crud-nested-value-lab.sqlite"
mkdir -p target/cncf.d

exec bash ../../bin/cncf --discover=classes command \
  "--cncf.datastore.sqlite.path=$dbpath" \
  --textus.command.execution-mode sync-direct-no-job \
  crud-nested-value-sample.entity.create-person \
  --name alice \
  --address.street Marunouchi-1-2-3 \
  --address.city Tokyo \
  --address.country.value JP
