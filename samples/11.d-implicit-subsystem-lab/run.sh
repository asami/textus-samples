#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

echo "--- subsystem help"
cncf dev command --project . meta.help --format yaml

echo
echo "--- component help"
cncf dev command --project . meta.help subsystem --format yaml

echo
echo "--- operation help"
cncf dev command --project . help subsystem.main.hello

echo
echo "--- execute"
cncf dev command --project . subsystem.main.hello
