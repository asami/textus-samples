#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
SERVER_PORT="$(tr -d '[:space:]' < "$ROOT_DIR/versions/cncf-server-port.conf")"
for pid in $(lsof -ti "tcp:${SERVER_PORT}" 2>/dev/null || true); do
  kill "$pid" >/dev/null 2>&1 || true
done

exec cncf server
