#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dbpath="target/cncf.d/02f-crud-nested-value-lab.sqlite"
mkdir -p target/cncf.d

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  -- \
  command \
  "--cncf.datastore.sqlite.path=$dbpath" \
  --textus.runtime.command.execution-mode sync-direct-no-job \
  crud-nested-value-sample.entity.create-person \
  --name alice \
  --address.street Marunouchi-1-2-3 \
  --address.city Tokyo \
  --address.country.value JP
