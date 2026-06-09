#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ $# -ne 1 ]]; then
  echo "usage: $0 <job-id>" >&2
  exit 1
fi

job_id="$1"
SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19542}"

exec cncf dev client --project-dev . \
  job-control.job.await-job-result \
  --baseurl "http://localhost:${SERVER_PORT}" \
  --id "$job_id"
