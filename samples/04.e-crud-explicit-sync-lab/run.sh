#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . help crud
cncf dev command --project . help crud.entity
cncf dev command --project . help crud.entity.create-item
cncf dev command --project . help crud.entity.load-item
cncf dev command --project . crud.meta.describe --format yaml
bash run-demo.sh
