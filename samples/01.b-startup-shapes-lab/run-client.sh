#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ $# -eq 0 ]]; then
  exec cncf dev client --project . --component-dev-dir . --help
fi

exec cncf dev client --project . --component-dev-dir . "$@"
