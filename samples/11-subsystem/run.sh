#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

sbt --batch compile packageBin >/dev/null

COMPONENT_BINARY="$(find target/scala-3.3.7 -maxdepth 1 -name '*.jar' | head -n 1)"
if [[ -z "${COMPONENT_BINARY:-}" ]]; then
  echo "Component jar not found." >&2
  exit 1
fi

mkdir -p component.d "$WORK_DIR/testcomp.car.d/component" "$WORK_DIR/testsubsystem.sar.d"
rm -f component.d/testcomp.car component.d/testsubsystem.sar

cp "$COMPONENT_BINARY" "$WORK_DIR/testcomp.car.d/component/main.jar"
cat > "$WORK_DIR/testcomp.car.d/component-descriptor.yaml" <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
subsystem: testsubsystem
EOF
(cd "$WORK_DIR/testcomp.car.d" && zip -qr "$SCRIPT_DIR/component.d/testcomp.car" component-descriptor.yaml component)

cat > "$WORK_DIR/testsubsystem.sar.d/subsystem-descriptor.yaml" <<'EOF'
subsystem: testsubsystem
version: 0.1.0
components:
  - component: testcomp
    coordinate: org.simplemodeling.car:testcomp:0.1.0
EOF
(cd "$WORK_DIR/testsubsystem.sar.d" && zip -qr "$SCRIPT_DIR/component.d/testsubsystem.sar" subsystem-descriptor.yaml)

echo "--- subsystem help"
bash ../../bin/cncf \
  command meta.help --format yaml \
  --textus.subsystem=testsubsystem

echo
echo "--- component help"
bash ../../bin/cncf \
  command meta.help testcomp --format yaml \
  --textus.subsystem=testsubsystem

echo
echo "--- operation help"
bash ../../bin/cncf \
  command help testcomp.main.hello \
  --textus.subsystem=testsubsystem

echo
echo "--- execute"
bash ../../bin/cncf \
  command testcomp.main.hello \
  --textus.subsystem=testsubsystem
