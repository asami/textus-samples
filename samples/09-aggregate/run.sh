#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19549}"
SERVER_BASEURL="http://127.0.0.1:${SERVER_PORT}"
SERVER_LOG="${TMPDIR:-/tmp}/09-aggregate-server.log"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

cncf server --textus.server.port "$SERVER_PORT" >"$SERVER_LOG" 2>&1 &
SERVER_PID=$!
trap 'kill "$SERVER_PID" >/dev/null 2>&1 || true' EXIT

server_ready=0
for _ in $(seq 1 30); do
  ready_output="$(cncf client aggregate-sample.meta.describe --baseurl "$SERVER_BASEURL" --format yaml 2>&1 || true)"
  if printf '%s\n' "$ready_output" | grep -q "type: component"; then
    server_ready=1
    break
  fi
  if ! kill -0 "$SERVER_PID" >/dev/null 2>&1; then
    cat "$SERVER_LOG"
    exit 1
  fi
  sleep 1
done
if [[ "$server_ready" -ne 1 ]]; then
  cat "$SERVER_LOG"
  exit 1
fi

extract_result_id() {
  python3 -c 'import json,re,sys
text=sys.stdin.read()
lines=[line.strip() for line in text.splitlines() if line.strip()]
for line in lines:
    if line.startswith("cncf-job-"):
        print(line)
        raise SystemExit(0)
payloads=[line for line in lines if line.startswith("{") and line.endswith("}")]
if payloads:
    obj=json.loads(payloads[-1])
    def find(o):
        if isinstance(o, dict):
            for key in ("id", "job_id", "jobId"):
                v=o.get(key)
                if isinstance(v, str) and v:
                    return v
            for key in ("data", "record", "result", "job"):
                if key in o:
                    v=find(o[key])
                    if v:
                        return v
        return None
    value=find(obj)
    if value:
        print(value)
        raise SystemExit(0)
for line in lines:
    m=re.match(r"id:\s*(\S+)", line)
    if m:
        print(m.group(1))
        raise SystemExit(0)
raise SystemExit(f"id not found in {text!r}")'
}

await_if_job() {
  local value="$1"
  if [[ "$value" == cncf-job-* ]]; then
    cncf client job-control.job.await-job-result --baseurl "$SERVER_BASEURL" --id "$value" | extract_result_id
  else
    printf '%s\n' "$value"
  fi
}

ORDER_NAME="alpha-$(date +%s)"
CREATE_RESULT_ID="$(cncf client aggregate-sample.entity.create-order-record --baseurl "$SERVER_BASEURL" --name "$ORDER_NAME" --status Draft | extract_result_id)"
ORDER_ID="$(await_if_job "$CREATE_RESULT_ID")"
ADD_LINE_RESULT_ID="$(cncf client aggregate-sample.order.add-line --baseurl "$SERVER_BASEURL" --orderId "$ORDER_ID" --lineName pen --quantity 2 | extract_result_id)"
await_if_job "$ADD_LINE_RESULT_ID" >/dev/null
cncf client aggregate-sample.order.load-order-aggregate --baseurl "$SERVER_BASEURL" --id "$ORDER_ID"
