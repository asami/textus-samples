#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo "--- component help"
cncf command meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
cncf command help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
cncf command component-cml-sample.meta.describe --format yaml
