#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORKLOAD_DIR="$SCRIPT_DIR/../01.c-builtin-and-help-lab"
export CNCF_SERVER_PORT="${CNCF_SERVER_PORT:-19614}"

cd "$WORKLOAD_DIR"
exec cncf client --textus.config.file "$SCRIPT_DIR/.textus.conf" minimal.main.hello \
  --baseurl "http://127.0.0.1:${CNCF_SERVER_PORT}"
