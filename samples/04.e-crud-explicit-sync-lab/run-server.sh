#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19543}"

exec cncf dev server --project . --textus.server.port "$SERVER_PORT"
