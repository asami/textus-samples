#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

docker compose up -d

echo "Jaeger UI: http://127.0.0.1:16686"
echo "OTLP HTTP: http://127.0.0.1:4318"
