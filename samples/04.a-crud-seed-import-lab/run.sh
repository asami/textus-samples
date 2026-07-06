#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help crud.entity.load-item
cncf command help crud.entity.search-item-record
cncf command crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111
cncf command crud.entity.search-item-record --name alpha
cncf command crud.meta.describe --format yaml
