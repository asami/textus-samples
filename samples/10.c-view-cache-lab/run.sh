#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
mkdir -p "$SCRIPT_DIR/target/cncf.d"

run_command() {
  local workspace="$1"
  shift
  bash "$SCRIPT_DIR/../../bin/cncf" \
    --discover=classes \
    --workspace "$SCRIPT_DIR/target/cncf.d/$workspace" \
    "$@"
}

echo "--- help"
run_command help \
  command help view-cache-sample.view.search-person-summary-record

echo
echo "--- page 1"
run_command page-1 \
  command view-cache-sample.view.search-person-summary-record \
  --city Tokyo \
  --query.limit 2 \
  --query.offset 0

echo
echo "--- page 2"
run_command page-2 \
  command view-cache-sample.view.search-person-summary-record \
  --city Tokyo \
  --query.limit 2 \
  --query.offset 1

echo
echo "--- page 3"
run_command page-3 \
  command view-cache-sample.view.search-person-summary-record \
  --city Tokyo \
  --query.limit 2 \
  --query.offset 2

echo
echo "--- meta describe"
run_command meta \
  command view-cache-sample.meta.describe --format yaml
