#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -ne 1 ]; then
  echo "usage: bash run-load.sh <person-id>" >&2
  exit 1
fi

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
  crud-nested-value-sample.entity.load-person \
  --id "$1"
