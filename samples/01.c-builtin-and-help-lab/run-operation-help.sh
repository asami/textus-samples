#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"
exec cncf dev command --project . --component-dev-dir . help minimal.main.hello "$@"
