#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"
sbt package >/dev/null
PACKAGE_JAR="$(find target/scala-3.3.7 -maxdepth 1 -name '*.jar' | sort | tail -n 1)"
mkdir -p "$SCRIPT_DIR/../component.d"
cp "$PACKAGE_JAR" "$SCRIPT_DIR/../component.d/MinimalComponent.jar"
exec bash "$SCRIPT_DIR/../../bin/cncf" \
  --component-dir "$SCRIPT_DIR/../component.d" \
  command minimal.main.hello \
  "$@"
