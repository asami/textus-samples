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

cncf command help event-driven
cncf command help event-driven.event.emit-event
cncf command help job-control.job.await-job-result
cncf command help job-control.job.load-job-history
cncf command event-driven.meta.describe --format yaml

ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
SERVER_PORT="$(tr -d '[:space:]' < "$ROOT_DIR/versions/cncf-server-port.conf")"
for pid in $(lsof -ti "tcp:${SERVER_PORT}" 2>/dev/null || true); do
  kill "$pid" >/dev/null 2>&1 || true
done

cncf server >"$server_log" 2>&1 &
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

job_id="$(cncf client event-driven.event.emit-event --name alpha --title Alpha --privilege content_admin)"
printf '%s\n' "$job_id"

cncf client job-control.job.await-job-result --id "$job_id" --privilege content_admin
cncf client job-control.job.get-job-status --id "$job_id" --privilege content_admin
cncf client job-control.job.load-job-history --id "$job_id" --privilege content_admin
cncf client event-driven.event.load-effect --privilege content_admin
