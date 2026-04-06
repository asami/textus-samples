#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ $# -ne 1 ]]; then
  echo "usage: $0 <job-id>" >&2
  exit 1
fi

job_id="$1"

exec bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "$job_id"
