#!/usr/bin/env bash
set -euo pipefail

mkdir -p car.d/component car.d/meta
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'cncf-samples-03-a-car-dir-cml-lab_3-*.jar' | head -n 1)"
ROOT_DIR="$(pwd)"
REPO_DIR="$ROOT_DIR/car.d"
cp "$JAR" car.d/component/main.jar
cat > car.d/descriptor.yaml <<'EOF'
name: component-cml-sample
version: 0.1.0
component: component-cml-sample
EOF
cat > car.d/meta/manifest.json <<'EOF'
{
  "name": "component-cml-sample",
  "version": "0.1.0",
  "component": "component-cml-sample"
}
EOF

echo "--- component help"
bash ../../bin/cncf \
  --sample-dir samples/01-minimal \
  --no-default-components \
  --component-repository=component-dir:"$REPO_DIR" \
  command meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --sample-dir samples/01-minimal \
  --no-default-components \
  --component-repository=component-dir:"$REPO_DIR" \
  command help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
bash ../../bin/cncf \
  --sample-dir samples/01-minimal \
  --no-default-components \
  --component-repository=component-dir:"$REPO_DIR" \
  command component-cml-sample.meta.describe --format yaml
