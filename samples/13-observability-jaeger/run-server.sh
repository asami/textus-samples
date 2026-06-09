#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORKLOAD_DIR="$SCRIPT_DIR/../01.c-builtin-and-help-lab"
export CNCF_SERVER_PORT="${CNCF_SERVER_PORT:-19613}"

cd "$WORKLOAD_DIR"
exec cncf dev server --project-dev . --textus.config.file "$SCRIPT_DIR/.textus.conf"
