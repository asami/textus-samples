#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

echo "--- subsystem help"
cncf command meta.help --format yaml

echo
echo "--- component help"
cncf command meta.help subsystem --format yaml

echo
echo "--- operation help"
cncf command help subsystem.main.hello

echo
echo "--- execute"
cncf command subsystem.main.hello
