#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p component.d car.d/testcomp/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'textus-samples-02-component_3-*.jar' | head -n 1)"
TMPDIR="$(mktemp -d)"
ROOT_DIR="$(pwd)"
trap 'rm -rf "$TMPDIR"' EXIT
mkdir -p "$TMPDIR/component"
cp "$JAR" "$TMPDIR/component/main.jar"
cat > "$TMPDIR/component-descriptor.yaml" <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
EOF
(cd "$TMPDIR" && zip -qr "$ROOT_DIR/component.d/testcomp.car" component-descriptor.yaml component)
cp "$TMPDIR/component/main.jar" car.d/testcomp/component/main.jar
cp "$TMPDIR/component-descriptor.yaml" car.d/testcomp/component-descriptor.yaml
COMMON_ARGS=(
  --no-project-classpath
  --component-dir component.d
  --textus.component=testcomp
)

echo "--- component help"
cncf command "${COMMON_ARGS[@]}" meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf command "${COMMON_ARGS[@]}" help testcomp.main.hello
