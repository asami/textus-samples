#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

logfile="$(mktemp)"
trap 'rm -f "$logfile"' EXIT
export CNCF_SAMPLE_SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19542}"

extract_id() {
  python3 -c 'import json,sys
text=sys.stdin.read()
lines=[line.strip() for line in text.splitlines() if line.strip()]
payloads=[line for line in lines if line.startswith("{") and line.endswith("}")]
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
if payloads:
    value=find(json.loads(payloads[-1]))
    if value:
        print(value)
        raise SystemExit(0)
for line in reversed(lines):
    if line.startswith("id:"):
        value=line.split(":", 1)[1].strip().strip("\"'\''")
        if value:
            print(value)
            raise SystemExit(0)
    if line.startswith("cncf-job-"):
        print(line)
        raise SystemExit(0)
raise SystemExit(f"id not found in {text!r}")'
}

bash run-server.sh >"$logfile" 2>&1 &
server_pid=$!
cleanup() {
  kill "$server_pid" >/dev/null 2>&1 || true
  wait "$server_pid" >/dev/null 2>&1 || true
}
trap cleanup EXIT

server_ready=0
for _ in $(seq 1 30); do
  if grep -q "Ember-Server service bound to address" "$logfile"; then
    server_ready=1
    break
  fi
  if ! kill -0 "$server_pid" >/dev/null 2>&1; then
    cat "$logfile"
    exit 1
  fi
  sleep 1
done
if [[ "$server_ready" -ne 1 ]]; then
  cat "$logfile"
  exit 1
fi

create_result="$(bash run-client-create.sh)"
created_id="$(printf '%s\n' "$create_result" | extract_id)"
if [[ "$created_id" == cncf-job-* ]]; then
  item_id="$(bash run-client-await.sh "$created_id" | extract_id)"
else
  item_id="$created_id"
fi
load_result="$(bash run-client-load.sh "$item_id")"
printf '%s\n' "$load_result"
case "$load_result" in
  *'"error"'*)
    exit 1
    ;;
esac
