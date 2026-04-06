#!/bin/sh
set -eu

if [ "$#" -ne 1 ]; then
  echo "usage: bash run-load.sh <person-id>" >&2
  exit 1
fi

dbpath="target/cncf.d/02f-crud-nested-value-lab.sqlite"
mkdir -p target/cncf.d

exec bash ../../bin/cncf --discover=classes command \
  "--cncf.datastore.sqlite.path=$dbpath" \
  crud-nested-value-sample.entity.load-person \
  --id "$1"
