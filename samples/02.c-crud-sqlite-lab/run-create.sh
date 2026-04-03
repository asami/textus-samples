#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dbpath="target/cncf.d/02c-crud-sqlite-lab.sqlite"
mkdir -p target/cncf.d

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  -- \
  command \
  "--cncf.datastore.sqlite.path=$dbpath" \
  crud.entity.create-item \
  --name alpha \
  --title Alpha
