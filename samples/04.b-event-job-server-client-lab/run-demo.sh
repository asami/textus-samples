#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

logfile="$(mktemp)"
trap 'rm -f "$logfile"' EXIT

bash run-server.sh >"$logfile" 2>&1 &
server_pid=$!
cleanup() {
  kill "$server_pid" >/dev/null 2>&1 || true
  wait "$server_pid" >/dev/null 2>&1 || true
}
trap cleanup EXIT

for _ in $(seq 1 30); do
  if grep -q "Ember-Server service bound to address" "$logfile"; then
    break
  fi
  sleep 1
done

bash run-client-emit.sh
bash run-client-load.sh
