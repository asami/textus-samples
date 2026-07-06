#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help crud
cncf command help crud.entity
cncf command help crud.entity.create-item
cncf command help job-control.job.await-job-result
cncf command crud.meta.describe --format yaml
bash run-demo.sh
