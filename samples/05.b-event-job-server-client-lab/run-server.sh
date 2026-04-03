#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run-server.sh" \
  --discover-classes \
  -- \
  server
