#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help designed-sync.item.create-item
cncf command designed-sync.meta.describe --format yaml
cncf command designed-sync.item.create-item --name beta --title Beta
