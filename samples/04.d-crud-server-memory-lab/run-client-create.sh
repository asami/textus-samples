#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec cncf dev client --project . --component-dev-dir . crud.entity.create-item --name alpha --title Alpha
