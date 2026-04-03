#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  -- \
  --textus.runtime.command.execution-mode sync-direct-no-job \
  command \
  crud.entity.create-item \
  --name alpha \
  --title Alpha
