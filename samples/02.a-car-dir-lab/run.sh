#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

mkdir -p car.d/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'textus-samples-02-a-car-dir-lab_3-*.jar' | head -n 1)"
cp "$JAR" car.d/component/main.jar
cat > car.d/descriptor.yaml <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
EOF
COMMON_ARGS=(
  --no-project-classpath
  --component-car-dir car.d
)

echo "--- component help"
cncf command "${COMMON_ARGS[@]}" meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf command "${COMMON_ARGS[@]}" help testcomp.main.hello

echo
echo "--- execute"
cncf command "${COMMON_ARGS[@]}" testcomp.main.hello
