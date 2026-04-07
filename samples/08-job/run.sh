#!/bin/sh
set -eu

dir="$(CDPATH= cd -- "$(dirname "$0")" && pwd)"
cd "$dir"

logfile="$(mktemp)"
trap 'rm -f "$logfile"' EXIT

server_pid=""
if curl -sS http://127.0.0.1:8080/ >/dev/null 2>&1; then
  echo "Reusing existing server on :8080"
else
  bash ../../bin/cncf --discover=classes server >"$logfile" 2>&1 &
  server_pid=$!
  cleanup() {
    if [ -n "$server_pid" ]; then
      kill "$server_pid" >/dev/null 2>&1 || true
      wait "$server_pid" >/dev/null 2>&1 || true
    fi
  }
  trap cleanup EXIT

  for _ in $(seq 1 30); do
    if grep -q "Ember-Server service bound to address" "$logfile"; then
      break
    fi
    if ! kill -0 "$server_pid" >/dev/null 2>&1; then
      cat "$logfile"
      exit 1
    fi
    sleep 1
  done

  grep "Ember-Server service bound to address" "$logfile"
fi

job_id="$(bash ../../bin/cncf --discover=classes client job-sample.item.create-item --name alpha --title Alpha)"
printf '%s\n' "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.get-job-result --id "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.get-job-status --id "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.load-job-history --id "$job_id"
