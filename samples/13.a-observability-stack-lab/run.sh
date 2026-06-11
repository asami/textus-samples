#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
server_log="$(mktemp)"

cleanup() {
  if [ -n "${server_pid:-}" ]; then
    kill "$server_pid" >/dev/null 2>&1 || true
    wait "$server_pid" 2>/dev/null || true
  fi
  rm -f "$server_log"
}
trap cleanup EXIT INT TERM

bash "$SCRIPT_DIR/run-server.sh" >"$server_log" 2>&1 &
server_pid=$!

server_ready=0
for _ in $(seq 1 30); do
  if grep -q "Ember-Server service bound to address" "$server_log"; then
    server_ready=1
    break
  fi
  if ! kill -0 "$server_pid" >/dev/null 2>&1; then
    cat "$server_log"
    exit 1
  fi
  sleep 1
done

if [ "$server_ready" -ne 1 ]; then
  cat "$server_log"
  exit 1
fi

bash "$SCRIPT_DIR/run-operation.sh"
