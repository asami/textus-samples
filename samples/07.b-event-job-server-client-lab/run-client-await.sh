#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

job_id="${1:-}"

if [ -z "$job_id" ]; then
  echo "usage: bash run-client-await.sh <job-id>" >&2
  exit 1
fi

exec cncf dev client --project-dev . job-control.job.await-job-result --id "$job_id" --privilege content_admin
