#!/usr/bin/env bash

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p repository.d car.d/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'textus-samples-03-c-method-execution-cml-lab_3-*.jar' | head -n 1)"
TMPDIR="$(mktemp -d)"
ROOT_DIR="$(pwd)"
REPO_DIR="$ROOT_DIR/repository.d"
trap 'rm -rf "$TMPDIR"' EXIT
mkdir -p "$TMPDIR/component"
cp "$JAR" "$TMPDIR/component/main.jar"
cat > "$TMPDIR/descriptor.yaml" <<'EOF'
name: component-method-execution-sample
version: 0.1.0
component: component-method-execution-sample
EOF
(cd "$TMPDIR" && zip -qr "$ROOT_DIR/repository.d/component-method-execution-sample.car" descriptor.yaml component)
rm -rf car.d/component
mkdir -p car.d/component
cp "$TMPDIR/descriptor.yaml" car.d/descriptor.yaml
cp "$TMPDIR/component/main.jar" car.d/component/main.jar
COMMON_ARGS=(
  --no-project-classpath
  --repository-dir "$REPO_DIR"
  --textus.component=component-method-execution-sample
)

echo "--- component help"
cncf command "${COMMON_ARGS[@]}" meta.help component-method-execution-sample --format yaml

echo
echo "--- operation help"
cncf command "${COMMON_ARGS[@]}" help component-method-execution-sample.greeting.compose-greeting

echo
echo "--- execute"
cncf command "${COMMON_ARGS[@]}" component-method-execution-sample.greeting.compose-greeting --name Alice
