#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ $# -eq 0 ]]; then
  exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
    --script-path "$0" \
    -- \
    client \
    --help
fi

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  -- \
  client \
  "$@"
