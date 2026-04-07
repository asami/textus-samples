#!/usr/bin/env bash
set -euo pipefail

mkdir -p component.d car.d/testcomp/component car.d/testcomp/meta
sbt --batch clean compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'cncf-samples-02-component_3-*.jar' | head -n 1)"
TMPDIR="$(mktemp -d)"
ROOT_DIR="$(pwd)"
trap 'rm -rf "$TMPDIR"' EXIT
mkdir -p "$TMPDIR/component" "$TMPDIR/meta"
cp "$JAR" "$TMPDIR/component/main.jar"
cat > "$TMPDIR/meta/manifest.json" <<'EOF'
{
  "name": "testcomp",
  "version": "0.1.0",
  "component": "testcomp"
}
EOF
(cd "$TMPDIR" && zip -qr "$ROOT_DIR/component.d/testcomp.car" component meta)
cp "$TMPDIR/component/main.jar" car.d/testcomp/component/main.jar
cp "$TMPDIR/meta/manifest.json" car.d/testcomp/meta/manifest.json

echo "--- component help"
bash ../../bin/cncf \
  --component-repository=component-dir:component.d \
  command meta.help testcomp --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --component-repository=component-dir:component.d \
  command help testcomp.main.hello
