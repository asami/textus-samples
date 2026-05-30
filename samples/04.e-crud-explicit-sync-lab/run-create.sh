#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec cncf dev command --project . --component-dev-dir . \
  --textus.command.execution-mode sync-direct-no-job \
  crud.entity.create-item \
  --name alpha \
  --title Alpha
