#!/bin/sh

set -eu

ITEM_ID=org-sample-entity-item-$(date +%Y%m%d%H%M%S)-gamma111
SERVER_PORT="$(tr -d '[:space:]' < ../../versions/cncf-server-port.conf)"
SERVER_BASEURL="http://127.0.0.1:${SERVER_PORT}"
server_log="$(mktemp)"
server_pid=""

cleanup() {
  if [ -n "${server_pid}" ]; then
    kill "$server_pid" >/dev/null 2>&1 || true
    wait "$server_pid" >/dev/null 2>&1 || true
  fi
  rm -f "$server_log"
}
trap cleanup EXIT INT TERM

bash ../../bin/cncf --discover=classes command help cqrs.item.create-item
bash ../../bin/cncf --discover=classes command help cqrs.entity.create-item-record
bash ../../bin/cncf --discover=classes command cqrs.meta.describe --format yaml

ps -ax \
  | grep -F "org.goldenport.cncf.CncfMain --discover=classes server" \
  | grep -v grep \
  | awk '{print $1}' \
  | xargs kill >/dev/null 2>&1 || true

bash ../../bin/cncf --discover=classes server >"$server_log" 2>&1 &
server_pid=$!

for _ in $(seq 1 30); do
  if curl -sS "$SERVER_BASEURL/" >/dev/null 2>&1; then
    break
  fi
  if ! kill -0 "$server_pid" >/dev/null 2>&1; then
    cat "$server_log"
    exit 1
  fi
  sleep 1
done


job_id=$(bash ../../bin/cncf --discover=classes client cqrs.entity.create-item-record --id "$ITEM_ID" --name gamma --title Gamma)
printf '%s\n' "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "$job_id"
bash ../../bin/cncf --discover=classes client cqrs.entity.load-item --id "$ITEM_ID"
