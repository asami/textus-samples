#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
LOAD_ID="tokyo-sales-entity-person-1742198400000-abcd1234"
mkdir -p "$SCRIPT_DIR/target/cncf.d"

echo "--- help"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command help simple-entity-view-sample.view.load-person

echo
echo "--- load"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command simple-entity-view-sample.view.load-person --id "$LOAD_ID"

echo
echo "--- search"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command simple-entity-view-sample.view.search-person-record --name Alice
