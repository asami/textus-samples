#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
SERVER_PORT="$(tr -d '[:space:]' < "$ROOT_DIR/versions/cncf-server-port.conf")"
SERVER_BASEURL="http://127.0.0.1:${SERVER_PORT}"
SERVER_LOG="${TMPDIR:-/tmp}/07-aggregate-server.log"

sbt --batch compile >/dev/null

ps -ax \
  | grep -F "org.goldenport.cncf.CncfMain --discover=classes server" \
  | grep -v grep \
  | awk '{print $1}' \
  | xargs kill >/dev/null 2>&1 || true

bash "$ROOT_DIR/bin/cncf" --discover=classes server >"$SERVER_LOG" 2>&1 &
SERVER_PID=$!
trap 'kill "$SERVER_PID" >/dev/null 2>&1 || true' EXIT

for _ in $(seq 1 30); do
  if bash "$ROOT_DIR/bin/cncf" --discover=classes client aggregate-sample.meta.describe --format yaml >/dev/null 2>&1; then
    break
  fi
  if ! kill -0 "$SERVER_PID" >/dev/null 2>&1; then
    cat "$SERVER_LOG"
    exit 1
  fi
  sleep 1
done

ORDER_NAME="alpha-$(date +%s)"
CREATE_JOB_ID="$(bash "$ROOT_DIR/bin/cncf" --discover=classes client aggregate-sample.entity.create-order-record --name "$ORDER_NAME" --status Draft)"
ORDER_ID="$(bash "$ROOT_DIR/bin/cncf" --discover=classes client job-control.job.await-job-result --id "$CREATE_JOB_ID" | python3 -c 'import json,sys; print(json.load(sys.stdin)["id"])')"
ADD_LINE_JOB_ID="$(bash "$ROOT_DIR/bin/cncf" --discover=classes client aggregate-sample.order.add-line --order-id "$ORDER_ID" --line-name pen --quantity 2)"
bash "$ROOT_DIR/bin/cncf" --discover=classes client job-control.job.await-job-result --id "$ADD_LINE_JOB_ID" >/dev/null
bash "$ROOT_DIR/bin/cncf" --discover=classes client aggregate-sample.order.load-order-aggregate --id "$ORDER_ID"
