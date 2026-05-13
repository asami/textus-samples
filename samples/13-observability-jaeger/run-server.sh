#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORKLOAD_DIR="$SCRIPT_DIR/../01.c-builtin-and-help-lab"
export CNCF_SERVER_PORT="${CNCF_SERVER_PORT:-19613}"

cd "$WORKLOAD_DIR"
exec bash "$SCRIPT_DIR/../../bin/cncf" \
  --discover=classes \
  --textus.config.file "$SCRIPT_DIR/.textus.conf" \
  server
