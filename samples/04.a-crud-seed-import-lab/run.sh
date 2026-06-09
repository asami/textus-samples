#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project-dev . help crud.entity.load-item
cncf dev command --project-dev . help crud.entity.search-item-record
cncf dev command --project-dev . crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111
cncf dev command --project-dev . crud.entity.search-item-record --name alpha
cncf dev command --project-dev . crud.meta.describe --format yaml
