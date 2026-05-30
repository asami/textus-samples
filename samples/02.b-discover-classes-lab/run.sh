#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo "--- component help"
cncf dev command --project . --component-dev-dir . meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf dev command --project . --component-dev-dir . help testcomp.main.hello

echo
echo "--- execute"
cncf dev command --project . --component-dev-dir . testcomp.main.hello
