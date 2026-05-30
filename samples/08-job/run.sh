#!/bin/sh
set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dir="$(CDPATH= cd -- "$(dirname "$0")" && pwd)"
cd "$dir"

SERVER_PORT="$(tr -d '[:space:]' < ../../versions/cncf-server-port.conf)"
SERVER_BASEURL="http://127.0.0.1:${SERVER_PORT}"
logfile="$(mktemp)"
trap 'rm -f "$logfile"' EXIT

server_pid=""
if curl -sS "$SERVER_BASEURL/" >/dev/null 2>&1; then
  echo "Reusing existing server on :$SERVER_PORT"
else
  cncf dev server --project . --component-dev-dir . >"$logfile" 2>&1 &
  server_pid=$!
  cleanup() {
    if [ -n "$server_pid" ]; then
      kill "$server_pid" >/dev/null 2>&1 || true
      wait "$server_pid" >/dev/null 2>&1 || true
    fi
  }
  trap cleanup EXIT

  for _ in $(seq 1 30); do
    if curl -sS "$SERVER_BASEURL/" >/dev/null 2>&1; then
      break
    fi
    if ! kill -0 "$server_pid" >/dev/null 2>&1; then
      cat "$logfile"
      exit 1
    fi
    sleep 1
  done
fi

job_id="$(cncf dev client --project . --component-dev-dir . job-sample.item.create-item --name alpha --title Alpha)"
printf '%s\n' "$job_id"
cncf dev client --project . --component-dev-dir . job-control.job.await-job-result --id "$job_id"
cncf dev client --project . --component-dev-dir . job-control.job.get-job-result --id "$job_id"
cncf dev client --project . --component-dev-dir . job-control.job.get-job-status --id "$job_id"
cncf dev client --project . --component-dev-dir . job-control.job.load-job-history --id "$job_id"
