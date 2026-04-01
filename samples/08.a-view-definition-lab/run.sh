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
  command help named-view-sample.view.load-person-summary

echo
echo "--- summary load"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command named-view-sample.view.load-person-summary --id "$LOAD_ID"

echo
echo "--- summary search"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command named-view-sample.view.search-person-summary-record --city Tokyo

echo
echo "--- custom query search"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command named-view-sample.view.search-person --view search_by_city --city Tokyo

echo
echo "--- detail load"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command named-view-sample.view.load-person-detail --id "$LOAD_ID"

echo
echo "--- meta describe"
bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command named-view-sample.meta.describe --format yaml
