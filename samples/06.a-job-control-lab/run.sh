#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

server_pid=""
cleanup() {
  if [[ -n "${server_pid}" ]]; then
    kill "${server_pid}" >/dev/null 2>&1 || true
    wait "${server_pid}" 2>/dev/null || true
  fi
}
trap cleanup EXIT

bash ../../bin/cncf --discover=classes server >/tmp/06-a-job-control-server.log 2>&1 &
server_pid=$!

for _ in $(seq 1 30); do
  if grep -q 'Ember-Server service bound to address' /tmp/06-a-job-control-server.log; then
    break
  fi
  sleep 1
done

job_json="$(bash ../../bin/cncf --discover=classes client job-control-lab.item.create-item --name quick --title Quick)"
echo "${job_json}"
job_id="$(printf '%s\n' "${job_json}" | sed -n 's/.*"job_id":"\([^"]*\)".*/\1/p')"

bash ../../bin/cncf --discover=classes client job-control.job-admin.suspend-job --id "${job_id}" --privilege content_admin
bash ../../bin/cncf --discover=classes client job-control.job.get-job-status --id "${job_id}"
bash ../../bin/cncf --discover=classes client job-control.job-admin.resume-job --id "${job_id}" --privilege content_admin

await_job_result() {
  local id="$1"
  local result=""
  local i
  for i in $(seq 1 10); do
    result="$(bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "${id}")"
    if printf '%s\n' "${result}" | grep -q '"id":"major-minor-entity-item-'; then
      printf '%s\n' "${result}"
      return 0
    fi
    sleep 1
  done
  printf '%s\n' "${result}"
  return 1
}

await_job_result "${job_id}"
bash ../../bin/cncf --discover=classes client job-control.job.load-job-history --id "${job_id}"
bash ../../bin/cncf --discover=classes command event.event-admin.load-job-events --id "${job_id}" --privilege content_admin

cancel_json="$(bash ../../bin/cncf --discover=classes client job-control-lab.item.create-item --name cancel --title Cancel)"
echo "${cancel_json}"
cancel_job_id="$(printf '%s\n' "${cancel_json}" | sed -n 's/.*"job_id":"\([^"]*\)".*/\1/p')"

bash ../../bin/cncf --discover=classes client job-control.job-admin.cancel-job --id "${cancel_job_id}" --privilege content_admin
bash ../../bin/cncf --discover=classes client job-control.job.load-job-history --id "${cancel_job_id}"
bash ../../bin/cncf --discover=classes command event.event-admin.load-job-events --id "${cancel_job_id}" --privilege content_admin
