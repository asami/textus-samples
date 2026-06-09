#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo "--- component help"
cncf dev command --project-dev . meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf dev command --project-dev . help testcomp.main.hello

echo
echo "--- execute"
cncf dev command --project-dev . testcomp.main.hello
