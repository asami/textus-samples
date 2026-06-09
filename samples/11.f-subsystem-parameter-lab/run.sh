#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

BASELINE_DIR="../11-subsystem"
BASELINE_JAR="$(find "$BASELINE_DIR/target/scala-3.3.7" -maxdepth 1 -name '*.jar' | head -n 1)"

if [[ -z "${BASELINE_JAR:-}" ]]; then
  echo "Baseline component jar not found. Build ../11-subsystem first." >&2
  exit 1
fi

WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT
mkdir -p "$WORK_DIR/component.d" "$WORK_DIR/testcomp.car.d/component"
cp "$BASELINE_JAR" "$WORK_DIR/testcomp.car.d/component/main.jar"
cat > "$WORK_DIR/testcomp.car.d/component-descriptor.yaml" <<'EOF'
name: testcomp
version: 0.1.0
component: testcomp
subsystem: testsubsystem
EOF
(cd "$WORK_DIR/testcomp.car.d" && zip -qr "$WORK_DIR/component.d/testcomp.car" component-descriptor.yaml component)

echo "--- subsystem help"
cncf dev command --project-dev . --no-project-classpath --textus.subsystem.descriptor=../11-subsystem/subsystem.cml --component-dir "$WORK_DIR/component.d" meta.help --format yaml

echo
echo "--- component help"
cncf dev command --project-dev . --no-project-classpath --textus.subsystem.descriptor=../11-subsystem/subsystem.cml --component-dir "$WORK_DIR/component.d" meta.help testcomp --format yaml

echo
echo "--- operation help"
cncf dev command --project-dev . --no-project-classpath --textus.subsystem.descriptor=../11-subsystem/subsystem.cml --component-dir "$WORK_DIR/component.d" help testcomp.main.hello

echo
echo "--- execute"
cncf dev command --project-dev . --no-project-classpath --textus.subsystem.descriptor=../11-subsystem/subsystem.cml --component-dir "$WORK_DIR/component.d" testcomp.main.hello
