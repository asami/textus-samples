#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . --component-dev-dir . help designed-sync.item.create-item
cncf dev command --project . --component-dev-dir . designed-sync.meta.describe --format yaml
cncf dev command --project . --component-dev-dir . designed-sync.item.create-item --name beta --title Beta
