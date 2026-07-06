#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help crud
cncf command help crud.item
cncf command help crud.item.create-item
cncf command crud.meta.describe --format yaml
