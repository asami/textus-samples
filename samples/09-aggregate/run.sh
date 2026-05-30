#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
SERVER_PORT="$(tr -d '[:space:]' < "$ROOT_DIR/versions/cncf-server-port.conf")"
SERVER_BASEURL="http://127.0.0.1:${SERVER_PORT}"
SERVER_LOG="${TMPDIR:-/tmp}/07-aggregate-server.log"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

ps -ax \
  | grep -F "cncf.launcher.CncfLauncherMain dev server" \
  | grep -v grep \
  | awk '{print $1}' \
  | xargs kill >/dev/null 2>&1 || true

cncf dev server --project . --component-dev-dir . >"$SERVER_LOG" 2>&1 &
SERVER_PID=$!
trap 'kill "$SERVER_PID" >/dev/null 2>&1 || true' EXIT

for _ in $(seq 1 30); do
  if cncf dev client --project . --component-dev-dir . aggregate-sample.meta.describe --format yaml >/dev/null 2>&1; then
    break
  fi
  if ! kill -0 "$SERVER_PID" >/dev/null 2>&1; then
    cat "$SERVER_LOG"
    exit 1
  fi
  sleep 1
done

ORDER_NAME="alpha-$(date +%s)"
CREATE_JOB_ID="$(cncf dev client --project . --component-dev-dir . aggregate-sample.entity.create-order-record --name "$ORDER_NAME" --status Draft)"
ORDER_ID="$(cncf dev client --project . --component-dev-dir . job-control.job.await-job-result --id "$CREATE_JOB_ID" | python3 -c 'import json,sys; print(json.load(sys.stdin)["id"])')"
ADD_LINE_JOB_ID="$(cncf dev client --project . --component-dev-dir . aggregate-sample.order.add-line --order-id "$ORDER_ID" --line-name pen --quantity 2)"
cncf dev client --project . --component-dev-dir . job-control.job.await-job-result --id "$ADD_LINE_JOB_ID" >/dev/null
cncf dev client --project . --component-dev-dir . aggregate-sample.order.load-order-aggregate --id "$ORDER_ID"
