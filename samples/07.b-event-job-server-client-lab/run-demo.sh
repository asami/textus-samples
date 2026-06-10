#!/bin/sh
set -eu

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

server_ready=0
for _ in $(seq 1 30); do
  if grep -q "Ember-Server service bound to address" "$logfile"; then
    server_ready=1
    break
  fi
  if ! kill -0 "$server_pid" >/dev/null 2>&1; then
    cat "$logfile"
    exit 1
  fi
  sleep 1
done
if [ "$server_ready" -ne 1 ]; then
  cat "$logfile"
  exit 1
fi

grep "Ember-Server service bound to address" "$logfile"

job_id="$(bash run-client-emit.sh)"
printf '%s\n' "$job_id"
bash run-client-await.sh "$job_id"
bash run-client-load.sh
