#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"
sbt package >/dev/null
PACKAGE_JAR="$(find target -name '*.jar' | sort | tail -n 1)"
mkdir -p "$SCRIPT_DIR/../component-repository.d"
cp "$PACKAGE_JAR" "$SCRIPT_DIR/../component-repository.d/MinimalComponent.jar"
exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/invoke.sh" \
  --component-repository "component-dir:../component-repository.d" \
  --command-path minimal.main.hello \
  -- \
  "$@"
