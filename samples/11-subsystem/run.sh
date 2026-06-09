#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
cd "$SCRIPT_DIR"
trap 'rm -rf "$WORK_DIR"' EXIT

sbt --batch compile packageBin >/dev/null

COMPONENT_BINARY="$(find target/scala-3.3.7 -maxdepth 1 -name 'textus-samples-11-subsystem_3-*.jar' | head -n 1)"
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
cncf dev command --project-dev . --no-project-classpath --component-dir component.d --textus.subsystem=testsubsystem meta.help --format yaml

echo
echo "--- component help"
cncf dev command --project-dev . --no-project-classpath --component-dir component.d --textus.subsystem=testsubsystem meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf dev command --project-dev . --no-project-classpath --component-dir component.d --textus.subsystem=testsubsystem help testcomp.main.hello

echo
echo "--- execute"
cncf dev command --project-dev . --no-project-classpath --component-dir component.d --textus.subsystem=testsubsystem testcomp.main.hello
