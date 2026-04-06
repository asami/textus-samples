#!/bin/sh

set -eu

server_log="$(mktemp)"
cleanup() {
  if [ -n "${server_pid:-}" ]; then
    kill "$server_pid" >/dev/null 2>&1 || true
    wait "$server_pid" 2>/dev/null || true
  fi
  rm -f "$server_log"
}
trap cleanup EXIT INT TERM

bash ../../bin/cncf --discover=classes command help event-driven
bash ../../bin/cncf --discover=classes command help event-driven.event.emit-event
bash ../../bin/cncf --discover=classes command help job-control.job.await-job-result
bash ../../bin/cncf --discover=classes command help job-control.job.load-job-history
bash ../../bin/cncf --discover=classes command event-driven.meta.describe --format yaml

bash ../../bin/cncf --discover=classes server >"$server_log" 2>&1 &
server_pid=$!
sleep 3

job_id="$(bash ../../bin/cncf --discover=classes client event-driven.event.emit-event --name alpha --title Alpha)"
printf '%s\n' "$job_id"

bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.get-job-status --id "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.load-job-history --id "$job_id"
bash ../../bin/cncf --discover=classes client event-driven.event.load-effect
