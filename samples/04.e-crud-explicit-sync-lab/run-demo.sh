#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

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

item_id="$(
  bash run-client-create.sh |
    python3 -c 'import json,sys; lines=[line.strip() for line in sys.stdin if line.strip()]; payloads=[line for line in lines if line.startswith("{") and line.endswith("}")]; print(json.loads(payloads[-1])["id"])'
)"
bash run-client-search.sh "$item_id"
