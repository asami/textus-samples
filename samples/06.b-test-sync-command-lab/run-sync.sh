#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . --component-dev-dir . --textus.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta
cncf dev command --project . --component-dev-dir . --textus.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml
