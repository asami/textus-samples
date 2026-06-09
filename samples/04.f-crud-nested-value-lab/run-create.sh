#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dbpath="target/cncf.d/02f-crud-nested-value-lab.sqlite"
mkdir -p target/cncf.d

exec cncf dev command --project-dev . \
  "--cncf.datastore.sqlite.path=$dbpath" \
  --textus.command.execution-mode sync-direct-no-job \
  crud-nested-value-sample.entity.create-person \
  --name alice \
  --address.street Marunouchi-1-2-3 \
  --address.city Tokyo \
  --address.country.value JP
