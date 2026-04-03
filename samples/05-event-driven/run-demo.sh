#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --sample-main-class org.sample.eventdriven.EventFlowDemo
