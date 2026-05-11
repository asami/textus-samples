#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
export CNCF_SERVER_PORT="${CNCF_SERVER_PORT:-19614}"
WORKLOAD_DIR="$SCRIPT_DIR/../01.c-builtin-and-help-lab"
CNCF_SERVER_CMD=(bash "$SCRIPT_DIR/../../bin/cncf" --discover=classes --textus.config.file "$SCRIPT_DIR/.textus.conf")

mkdir -p "$SCRIPT_DIR/target/cncf.d"
server_log="$SCRIPT_DIR/target/cncf.d/server.log"
server_pid=""

cleanup() {
  if [[ -n "$server_pid" ]]; then
    kill "$server_pid" >/dev/null 2>&1 || true
    wait "$server_pid" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT INT TERM

form_api() {
  local path="$1"
  curl -fsS -X POST "http://127.0.0.1:${CNCF_SERVER_PORT}${path}"
}

(cd "$WORKLOAD_DIR" && "${CNCF_SERVER_CMD[@]}" server) >"$server_log" 2>&1 &
server_pid=$!

ready=0
for _ in $(seq 1 40); do
  if curl -fsS "http://127.0.0.1:${CNCF_SERVER_PORT}/form-api/minimal/main/hello" >/dev/null 2>&1; then
    ready=1
    break
  fi
  if ! kill -0 "$server_pid" >/dev/null 2>&1; then
    echo "CNCF server exited while starting. See $server_log" >&2
    exit 1
  fi
  sleep 1
done
if [[ "$ready" != "1" ]]; then
  echo "CNCF server did not become ready. See $server_log" >&2
  exit 1
fi

form_api /form-api/minimal/main/hello
form_api /form-api/metrics/metrics/load-runtime-metrics
sleep 2

echo
echo "Jaeger: search service goldenport-cncf"
echo "Prometheus: query cncf_action_execution_executions_count"
