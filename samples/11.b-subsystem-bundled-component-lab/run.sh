#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BASELINE_DIR="$(cd "$SCRIPT_DIR/../11-subsystem" && pwd)"
WORK_DIR="${TMPDIR:-/tmp}/11-b-bundled-subsystem"
CAR_DIR="$WORK_DIR/base.car.d"
SAR_DIR="$WORK_DIR/explicit-subsystem.sar.d"
CAR_FILE="$WORK_DIR/base.car"
SAR_FILE="$SCRIPT_DIR/component.d/explicit-subsystem.sar"
COMPONENT_BINARY="$BASELINE_DIR/target/scala-3.3.7/textus-samples-11-subsystem_3-0.1.0-SNAPSHOT.jar"
cd "$SCRIPT_DIR"

mkdir -p "$SCRIPT_DIR/component.d"
rm -rf "$CAR_DIR" "$SAR_DIR"
rm -f "$SCRIPT_DIR/component.d/base.car" "$SCRIPT_DIR/component.d/explicit-subsystem.sar"
mkdir -p "$CAR_DIR/component" "$SAR_DIR/component"

(cd "$BASELINE_DIR" && sbt --batch compile packageBin >/dev/null)

cp "$COMPONENT_BINARY" "$CAR_DIR/component/main.jar"
cat > "$CAR_DIR/component-descriptor.yaml" <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
subsystem: testsubsystem
EOF

(cd "$CAR_DIR" && zip -qr "$CAR_FILE" component-descriptor.yaml component)

cp "$CAR_FILE" "$SAR_DIR/component/base.car"
cat > "$SAR_DIR/subsystem-descriptor.yaml" <<'EOF'
subsystem: testsubsystem
version: 0.1.0
components:
  - component: testcomp
    coordinate: org.simplemodeling.car:testcomp:0.1.0
EOF

(cd "$SAR_DIR" && zip -qr "$SAR_FILE" subsystem-descriptor.yaml component)

echo "--- subsystem help"
cncf dev command --project . --no-project-classpath --no-default-components --component-dir component.d --textus.subsystem=testsubsystem meta.help --format yaml

echo
echo "--- component help"
cncf dev command --project . --no-project-classpath --no-default-components --component-dir component.d --textus.subsystem=testsubsystem meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf dev command --project . --no-project-classpath --no-default-components --component-dir component.d --textus.subsystem=testsubsystem help testcomp.main.hello

echo
echo "--- execute"
cncf dev command --project . --no-project-classpath --no-default-components --component-dir component.d --textus.subsystem=testsubsystem testcomp.main.hello
