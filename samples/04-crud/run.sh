#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project-dev . help crud
cncf dev command --project-dev . help crud.item
cncf dev command --project-dev . help crud.item.create-item
cncf dev command --project-dev . crud.meta.describe --format yaml
