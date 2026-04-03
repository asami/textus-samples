#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run-client-emit.sh" \
  --discover-classes \
  -- \
  client event-driven.event.emit-event --name alpha --title Alpha
