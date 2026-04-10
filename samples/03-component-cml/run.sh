#!/usr/bin/env bash
set -euo pipefail

mkdir -p repository.d car.d/component car.d/meta
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'cncf-samples-03-component-cml_3-*.jar' | head -n 1)"
TMPDIR="$(mktemp -d)"
ROOT_DIR="$(pwd)"
REPO_DIR="$ROOT_DIR/repository.d"
trap 'rm -rf "$TMPDIR"' EXIT
mkdir -p "$TMPDIR/component" "$TMPDIR/meta"
cp "$JAR" "$TMPDIR/component/main.jar"
cat > "$TMPDIR/descriptor.yaml" <<'EOF'
name: component-cml-sample
version: 0.1.0
component: component-cml-sample
EOF
cat > "$TMPDIR/meta/manifest.json" <<'EOF'
{
  "name": "component-cml-sample",
  "version": "0.1.0",
  "component": "component-cml-sample"
}
EOF
(cd "$TMPDIR" && zip -qr "$ROOT_DIR/repository.d/component-cml-sample.car" descriptor.yaml component meta)
rm -rf car.d/component car.d/meta
mkdir -p car.d/component car.d/meta
cp "$TMPDIR/descriptor.yaml" car.d/descriptor.yaml
cp "$TMPDIR/component/main.jar" car.d/component/main.jar
cp "$TMPDIR/meta/manifest.json" car.d/meta/manifest.json

echo "--- component help"
bash ../../bin/cncf \
  --repository-dir "$REPO_DIR" \
  --textus.runtime.component=component-cml-sample \
  command meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --repository-dir "$REPO_DIR" \
  --textus.runtime.component=component-cml-sample \
  command help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
bash ../../bin/cncf \
  --repository-dir "$REPO_DIR" \
  --textus.runtime.component=component-cml-sample \
  command component-cml-sample.meta.describe --format yaml
