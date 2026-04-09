#!/usr/bin/env bash
set -euo pipefail

BASELINE_DIR="../11-subsystem"
BASELINE_JAR="$(find "$BASELINE_DIR/target/scala-3.3.7" -maxdepth 1 -name '*.jar' | head -n 1)"

if [[ -z "${BASELINE_JAR:-}" ]]; then
  echo "Baseline component jar not found. Build ../11-subsystem first." >&2
  exit 1
fi

WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT
mkdir -p "$WORK_DIR/component.d" "$WORK_DIR/testcomp.car.d/component" "$WORK_DIR/testcomp.car.d/meta"
cp "$BASELINE_JAR" "$WORK_DIR/testcomp.car.d/component/main.jar"
cat > "$WORK_DIR/testcomp.car.d/meta/manifest.json" <<'EOF'
{"name": "testcomp", "version": "0.1.0", "component": "testcomp", "subsystem": "testsubsystem"}
EOF
(cd "$WORK_DIR/testcomp.car.d" && zip -qr "$WORK_DIR/component.d/testcomp.car" component meta)

echo "--- subsystem help"
bash ../../bin/cncf \
  command meta.help --format yaml \
  --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml \
  --component-repository=component-dir:"$WORK_DIR/component.d"

echo
echo "--- component help"
bash ../../bin/cncf \
  command meta.help testcomp --format yaml \
  --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml \
  --component-repository=component-dir:"$WORK_DIR/component.d"

echo
echo "--- operation help"
bash ../../bin/cncf \
  command help testcomp.main.hello \
  --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml \
  --component-repository=component-dir:"$WORK_DIR/component.d"

echo
echo "--- execute"
bash ../../bin/cncf \
  command testcomp.main.hello \
  --textus.runtime.subsystem.descriptor=../11-subsystem/subsystem.cml \
  --component-repository=component-dir:"$WORK_DIR/component.d"
