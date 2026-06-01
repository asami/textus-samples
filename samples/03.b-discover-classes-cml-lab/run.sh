#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo "--- component help"
cncf dev command --project . meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
cncf dev command --project . help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
cncf dev command --project . component-cml-sample.meta.describe --format yaml
