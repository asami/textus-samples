#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

docker compose up -d

echo "Collector OTLP HTTP: http://127.0.0.1:4318"
echo "Jaeger UI: http://127.0.0.1:16686"
echo "Prometheus: http://127.0.0.1:9090"
echo "Grafana: http://127.0.0.1:3000"
