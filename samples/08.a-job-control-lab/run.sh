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

ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
SERVER_PORT="$(tr -d '[:space:]' < "$ROOT_DIR/versions/cncf-server-port.conf")"
for pid in $(lsof -ti "tcp:${SERVER_PORT}" 2>/dev/null || true); do
  kill "$pid" >/dev/null 2>&1 || true
done

server_log="${TMPDIR:-/tmp}/08-a-job-control-server.log"
: >"${server_log}"
cncf dev server --project-dev . >"${server_log}" 2>&1 &
server_pid=$!

server_ready=0
for _ in $(seq 1 30); do
  if grep -q 'Ember-Server service bound to address' "${server_log}"; then
    server_ready=1
    break
  fi
  if ! kill -0 "$server_pid" >/dev/null 2>&1; then
    cat "${server_log}"
    exit 1
  fi
  sleep 1
done
if [[ "$server_ready" -ne 1 ]]; then
  cat "${server_log}"
  exit 1
fi

extract_job_id() {
  python3 -c 'import json,re,sys
text=sys.stdin.read()
lines=[line.strip() for line in text.splitlines() if line.strip()]
jobs=re.findall(r"cncf-job-[A-Za-z0-9_-]+", text)
if jobs:
    print(jobs[-1])
    raise SystemExit(0)
payloads=[line for line in lines if line.startswith("{") and line.endswith("}")]
if payloads:
    obj=json.loads(payloads[-1])
    for path in (("job", "id"), ("data", "job_id"), ("data", "jobId"), ("data", "id"), ("job_id",), ("jobId",), ("id",)):
        cur=obj
        for key in path:
            if isinstance(cur, dict) and key in cur:
                cur=cur[key]
            else:
                cur=None
                break
        if isinstance(cur, str) and cur:
            print(cur)
            raise SystemExit(0)
for line in lines:
    m=re.match(r"id:\s*(\S+)", line)
    if m:
        print(m.group(1))
        raise SystemExit(0)
raise SystemExit(f"job id not found in {text!r}")'
}

job_json="$(cncf dev client --project-dev . job-control-lab.item.create-item --name quick --title Quick)"
echo "${job_json}"
outer_job_id="$(printf '%s\n' "${job_json}" | extract_job_id)"
job_id="$(cncf dev client --project-dev . job-control.job.await-job-result --id "${outer_job_id}" | extract_job_id)"

cncf dev client --project-dev . job-control.job-admin.suspend-job --id "${job_id}" --privilege content_admin
cncf dev client --project-dev . job-control.job.get-job-status --id "${job_id}"
cncf dev client --project-dev . job-control.job-admin.resume-job --id "${job_id}" --privilege content_admin

await_job_result() {
  local id="$1"
  local result=""
  local i
  for i in $(seq 1 10); do
    result="$(cncf dev client --project-dev . job-control.job.await-job-result --id "${id}")"
    if printf '%s\n' "${result}" | grep -Eq '("id":|"id"[[:space:]]*:|^id:[[:space:]])'; then
      printf '%s\n' "${result}"
      return 0
    fi
    sleep 1
  done
  printf '%s\n' "${result}"
  return 1
}

await_job_result "${job_id}"
cncf dev client --project-dev . job-control.job.load-job-history --id "${job_id}"
cncf dev command --project-dev . event.event-admin.load-job-events --id "${job_id}" --privilege content_admin

cancel_json="$(cncf dev client --project-dev . job-control-lab.item.create-item --name cancel --title Cancel)"
echo "${cancel_json}"
cancel_outer_job_id="$(printf '%s\n' "${cancel_json}" | extract_job_id)"
cancel_job_id="$(cncf dev client --project-dev . job-control.job.await-job-result --id "${cancel_outer_job_id}" | extract_job_id)"

cncf dev client --project-dev . job-control.job-admin.cancel-job --id "${cancel_job_id}" --privilege content_admin
cncf dev client --project-dev . job-control.job.load-job-history --id "${cancel_job_id}"
cncf dev command --project-dev . event.event-admin.load-job-events --id "${cancel_job_id}" --privilege content_admin
