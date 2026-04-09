#!/usr/bin/env bash
set -euo pipefail

BASELINE_DIR="../11-subsystem"
BASELINE_JAR="$(find "$BASELINE_DIR/target/scala-3.3.7" -maxdepth 1 -name '*.jar' | head -n 1)"

if [[ -z "${BASELINE_JAR:-}" ]]; then
  echo "Baseline component jar not found. Build ../11-subsystem first." >&2
  exit 1
fi

rm -rf sar.d/component sar.d/explicit-subsystem
mkdir -p sar.d/component sar.d/meta sar.d/explicit-subsystem/component sar.d/explicit-subsystem/meta

WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

mkdir -p "$WORK_DIR/base.car.d/component" "$WORK_DIR/base.car.d/meta"
cp "$BASELINE_JAR" "$WORK_DIR/base.car.d/component/main.jar"
cat > "$WORK_DIR/base.car.d/meta/manifest.json" <<'EOF'
{"name": "testcomp", "version": "0.1.0", "component": "testcomp", "subsystem": "testsubsystem"}
EOF
(cd "$WORK_DIR/base.car.d" && zip -qr "$WORK_DIR/base.car" component meta)

cp "$WORK_DIR/base.car" sar.d/component/base.car
cp "$WORK_DIR/base.car" sar.d/explicit-subsystem/component/base.car
cat > sar.d/meta/manifest.json <<'EOF'
{"name": "explicit-subsystem", "version": "0.1.0", "subsystem": "testsubsystem"}
EOF
cp sar.d/meta/manifest.json sar.d/explicit-subsystem/meta/manifest.json

echo "--- subsystem help"
bash ../../bin/cncf \
  --no-default-components \
  command meta.help --format yaml \
  --cncf.config.file=subsystem-sar-dir.conf

echo
echo "--- component help"
bash ../../bin/cncf \
  --no-default-components \
  command meta.help testcomp --format yaml \
  --cncf.config.file=subsystem-sar-dir.conf

echo
echo "--- operation help"
bash ../../bin/cncf \
  --no-default-components \
  command help testcomp.main.hello \
  --cncf.config.file=subsystem-sar-dir.conf

echo
echo "--- execute"
bash ../../bin/cncf \
  --no-default-components \
  command testcomp.main.hello \
  --cncf.config.file=subsystem-sar-dir.conf
