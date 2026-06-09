#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p repository.d car.d/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'textus-samples-03-component-cml_3-*.jar' | head -n 1)"
TMPDIR="$(mktemp -d)"
ROOT_DIR="$(pwd)"
REPO_DIR="$ROOT_DIR/repository.d"
trap 'rm -rf "$TMPDIR"' EXIT
mkdir -p "$TMPDIR/component"
cp "$JAR" "$TMPDIR/component/main.jar"
cat > "$TMPDIR/descriptor.yaml" <<'EOF'
name: component-cml-sample
version: 0.1.0
component: component-cml-sample
EOF
(cd "$TMPDIR" && zip -qr "$ROOT_DIR/repository.d/component-cml-sample.car" descriptor.yaml component)
rm -rf car.d/component
mkdir -p car.d/component
cp "$TMPDIR/descriptor.yaml" car.d/descriptor.yaml
cp "$TMPDIR/component/main.jar" car.d/component/main.jar
COMMON_ARGS=(
  --no-project-classpath
  --repository-dir "$REPO_DIR"
  --textus.component=component-cml-sample
)

echo "--- component help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" component-cml-sample.meta.describe --format yaml
