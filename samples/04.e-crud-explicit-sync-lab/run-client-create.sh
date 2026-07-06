#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19543}"

exec cncf client \
  --textus.command.execution-mode sync-direct-no-job \
  crud.entity.create-item \
  --baseurl "http://localhost:${SERVER_PORT}" \
  --name alpha \
  --title Alpha
