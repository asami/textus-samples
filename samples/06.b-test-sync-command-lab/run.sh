#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . help test-sync.item.create-item
cncf dev command --project . test-sync.meta.describe --format yaml
bash run-default.sh
bash run-sync.sh
