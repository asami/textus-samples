#!/usr/bin/env bash
set -euo pipefail

mkdir -p car.d/component car.d/meta
sbt --batch compile packageBin >/dev/null
JAR="$(find target/scala-3.3.7 -name 'cncf-samples-02-a-car-dir-lab_3-*.jar' | head -n 1)"
cp "$JAR" car.d/component/main.jar
cat > car.d/descriptor.yaml <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
EOF
cat > car.d/meta/manifest.json <<'EOF'
{
  "name": "testcomp",
  "version": "0.1.0",
  "component": "testcomp"
}
EOF

echo "--- component help"
bash ../../bin/cncf \
  --no-default-components \
  --component-repository=component-dir:car.d \
  command meta.help testcomp --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --no-default-components \
  --component-repository=component-dir:car.d \
  command help testcomp.main.hello

echo
echo "--- execute"
bash ../../bin/cncf \
  --no-default-components \
  --component-repository=component-dir:car.d \
  command testcomp.main.hello
