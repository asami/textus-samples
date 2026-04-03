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

job_id="$(bash run-client-create.sh | rg '^cncf-job-' | tail -n 1)"
item_json="$(bash "$dir/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  -- \
  client \
  job-control.job.await-job-result \
  --id "${job_id}" | rg '^\{' | tail -n 1)"
item_id="$(printf '%s\n' "$item_json" | python3 -c 'import json,sys; print(json.loads(sys.stdin.read())["id"])')"
bash run-client-load.sh "$item_id"
