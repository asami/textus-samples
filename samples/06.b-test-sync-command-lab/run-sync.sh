#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project-dev . TestSync.Item.createItem --name beta --title Beta --textus.command.execution-mode sync-job-async-interface
cncf dev command --project-dev . TestSync.Item.createItem --name beta --title Beta --textus.command.execution-mode sync-job-async-interface --textus.output.shape envelope --textus.output.format yaml
