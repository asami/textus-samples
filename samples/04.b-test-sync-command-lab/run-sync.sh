#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  -- \
  --textus.runtime.command.execution-mode sync-job-async-interface \
  command \
  TestSync.Item.createItem \
  --name beta \
  --title Beta
