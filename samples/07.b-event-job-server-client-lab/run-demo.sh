#!/bin/sh
set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dir="$(CDPATH= cd -- "$(dirname "$0")" && pwd)"
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

grep "Ember-Server service bound to address" "$logfile"

job_id="$(bash run-client-emit.sh)"
printf '%s\n' "$job_id"
bash run-client-await.sh "$job_id"
bash run-client-load.sh
