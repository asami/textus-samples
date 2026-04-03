#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run.sh" \
  --discover-classes \
  -- \
  command designed-sync.item.create-item --name beta --title Beta
