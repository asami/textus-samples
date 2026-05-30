#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

server_log="$(mktemp)"
cleanup() {
  if [ -n "${server_pid:-}" ]; then
    kill "$server_pid" >/dev/null 2>&1 || true
    wait "$server_pid" 2>/dev/null || true
  fi
  rm -f "$server_log"
}
trap cleanup EXIT INT TERM

cncf dev command --project . --component-dev-dir . help event-driven
cncf dev command --project . --component-dev-dir . help event-driven.event.emit-event
cncf dev command --project . --component-dev-dir . help job-control.job.await-job-result
cncf dev command --project . --component-dev-dir . help job-control.job.load-job-history
cncf dev command --project . --component-dev-dir . event-driven.meta.describe --format yaml

cncf dev server --project . --component-dev-dir . >"$server_log" 2>&1 &
server_pid=$!
sleep 3

job_id="$(cncf dev client --project . --component-dev-dir . event-driven.event.emit-event --name alpha --title Alpha)"
printf '%s\n' "$job_id"

cncf dev client --project . --component-dev-dir . job-control.job.await-job-result --id "$job_id"
cncf dev client --project . --component-dev-dir . job-control.job.get-job-status --id "$job_id"
cncf dev client --project . --component-dev-dir . job-control.job.load-job-history --id "$job_id"
cncf dev client --project . --component-dev-dir . event-driven.event.load-effect
