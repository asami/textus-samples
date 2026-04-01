#!/usr/bin/env bash
set -eu
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command help operation-entity-sample.person-app.get-person-card
