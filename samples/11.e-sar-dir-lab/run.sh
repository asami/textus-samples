#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

BASELINE_DIR="../11-subsystem"
BASELINE_JAR="$(find "$BASELINE_DIR/target/scala-3.3.7" -maxdepth 1 -name 'textus-samples-11-subsystem_3-*.jar' | head -n 1)"

if [[ -z "${BASELINE_JAR:-}" ]]; then
  echo "Baseline component jar not found. Build ../11-subsystem first." >&2
  exit 1
fi

rm -rf sar.d/component sar.d/explicit-subsystem
mkdir -p sar.d/component sar.d/explicit-subsystem/component

WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

mkdir -p "$WORK_DIR/base.car.d/component"
cp "$BASELINE_JAR" "$WORK_DIR/base.car.d/component/main.jar"
cat > "$WORK_DIR/base.car.d/component-descriptor.yaml" <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
subsystem: testsubsystem
EOF
(cd "$WORK_DIR/base.car.d" && zip -qr "$WORK_DIR/base.car" component-descriptor.yaml component)

cp "$WORK_DIR/base.car" sar.d/component/testcomp.car
cp "$WORK_DIR/base.car" sar.d/explicit-subsystem/component/testcomp.car
cat > sar.d/subsystem-descriptor.yaml <<'EOF'
subsystem: testsubsystem
version: 0.1.0
components:
  - component: testcomp
    coordinate: org.simplemodeling.car:testcomp:0.1.0
EOF
cp sar.d/subsystem-descriptor.yaml sar.d/explicit-subsystem/subsystem-descriptor.yaml

COMMON_ARGS=(
  --no-default-components
  --subsystem-sar-dir sar.d
  --textus.subsystem=testsubsystem
)

echo "--- subsystem help"
cncf command --no-project-classpath "${COMMON_ARGS[@]}" meta.help --format yaml

echo
echo "--- component help"
cncf command --no-project-classpath "${COMMON_ARGS[@]}" meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf command --no-project-classpath "${COMMON_ARGS[@]}" help testcomp.main.hello

echo
echo "--- execute"
cncf command --no-project-classpath "${COMMON_ARGS[@]}" testcomp.main.hello
