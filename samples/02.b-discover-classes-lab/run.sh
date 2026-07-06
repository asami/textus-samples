#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo "--- component help"
cncf command meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf command help testcomp.main.hello

echo
echo "--- execute"
cncf command testcomp.main.hello
