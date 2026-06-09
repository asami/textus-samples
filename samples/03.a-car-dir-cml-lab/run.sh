#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p car.d/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'textus-samples-03-a-car-dir-cml-lab_3-*.jar' | head -n 1)"
cp "$JAR" car.d/component/main.jar
cat > car.d/descriptor.yaml <<'EOF'
name: component-cml-sample
version: 0.1.0
component: component-cml-sample
EOF
COMMON_ARGS=(
  --no-project-classpath
  --component-car-dir car.d
)

echo "--- component help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" component-cml-sample.meta.describe --format yaml
