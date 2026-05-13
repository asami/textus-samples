#!/usr/bin/env bash
set -euo pipefail

mkdir -p car.d/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'textus-samples-03-a-car-dir-cml-lab_3-*.jar' | head -n 1)"
ROOT_DIR="$(pwd)"
REPO_DIR="$ROOT_DIR/car.d"
cp "$JAR" car.d/component/main.jar
cat > car.d/descriptor.yaml <<'EOF'
name: component-cml-sample
version: 0.1.0
component: component-cml-sample
EOF
COMMON_ARGS=(
  --component-car-dir car.d
)

echo "--- component help"
bash ../../bin/cncf "${COMMON_ARGS[@]}" command meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf "${COMMON_ARGS[@]}" command help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
bash ../../bin/cncf "${COMMON_ARGS[@]}" command component-cml-sample.meta.describe --format yaml
