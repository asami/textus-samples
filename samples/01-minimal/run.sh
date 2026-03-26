#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  --command-path minimal.main.hello \
  -- \
  "$@"
