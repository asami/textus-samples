#!/usr/bin/env bash
set -euo pipefail

mkdir -p car.d/component
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'cncf-samples-02-a-car-dir-lab_3-*.jar' | head -n 1)"
cp "$JAR" car.d/component/main.jar
cat > car.d/descriptor.yaml <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
EOF
echo "--- component help"
bash ../../bin/cncf \
  --component-car-dir car.d \
  command meta.help testcomp --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --component-car-dir car.d \
  command help testcomp.main.hello

echo
echo "--- execute"
bash ../../bin/cncf \
  --component-car-dir car.d \
  command testcomp.main.hello
