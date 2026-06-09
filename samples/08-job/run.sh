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

for pid in $(lsof -ti "tcp:${SERVER_PORT}" 2>/dev/null || true); do
  kill "$pid" >/dev/null 2>&1 || true
done

server_pid=""
cncf dev server --project-dev . >"$logfile" 2>&1 &
server_pid=$!
cleanup() {
  if [ -n "$server_pid" ]; then
    kill "$server_pid" >/dev/null 2>&1 || true
    wait "$server_pid" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

server_ready=0
for _ in $(seq 1 30); do
  if curl -sS "$SERVER_BASEURL/" >/dev/null 2>&1; then
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

job_id="$(cncf dev client --project-dev . job-sample.item.create-item --name alpha --title Alpha)"
printf '%s\n' "$job_id"
cncf dev client --project-dev . job-control.job.await-job-result --id "$job_id"
cncf dev client --project-dev . job-control.job.get-job-result --id "$job_id"
cncf dev client --project-dev . job-control.job.get-job-status --id "$job_id"
cncf dev client --project-dev . job-control.job.load-job-history --id "$job_id"
