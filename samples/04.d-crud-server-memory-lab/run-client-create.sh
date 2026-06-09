#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19542}"

exec cncf dev client --project-dev . \
  crud.entity.create-item \
  --baseurl "http://localhost:${SERVER_PORT}" \
  --name alpha \
  --title Alpha
