#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

export CNCF_SERVER_PORT="${CNCF_SERVER_PORT:-19614}"
curl -fsS -X POST "http://127.0.0.1:${CNCF_SERVER_PORT}/form-api/metrics/metrics/load-runtime-metrics"
echo
