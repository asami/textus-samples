#!/bin/sh

set -eu

job_id="${1:-}"

if [ -z "$job_id" ]; then
  echo "usage: bash run-client-await.sh <job-id>" >&2
  exit 1
fi

exec bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "$job_id"
