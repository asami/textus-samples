#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run-operation-help.sh" \
  --discover-classes \
  --command-path help \
  -- \
  minimal.main.hello \
  "$@"
