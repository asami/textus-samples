#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . --component-dev-dir . help crud
cncf dev command --project . --component-dev-dir . help crud.entity
cncf dev command --project . --component-dev-dir . help crud.entity.create-item
cncf dev command --project . --component-dev-dir . help job-control.job.await-job-result
cncf dev command --project . --component-dev-dir . crud.meta.describe --format yaml
bash run-demo.sh
