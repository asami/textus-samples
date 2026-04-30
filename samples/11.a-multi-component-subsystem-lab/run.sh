#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

sbt --batch compile >/dev/null

CLASS_DIR="target/scala-3.3.7/classes"
if [[ ! -d "$CLASS_DIR/alpha" || ! -d "$CLASS_DIR/beta" ]]; then
  echo "Compiled component classes not found." >&2
  exit 1
fi

mkdir -p "$WORK_DIR/component.d"

(cd "$CLASS_DIR" && zip -qr "$WORK_DIR/alphacomp.jar" alpha)
(cd "$CLASS_DIR" && zip -qr "$WORK_DIR/betacomp.jar" beta)

create_car() {
  local component_name="$1"
  local component_jar="$2"
  local target_car="$3"
  local car_work="$WORK_DIR/${component_name}.car.d"
  mkdir -p "$car_work/component"
  cp "$component_jar" "$car_work/component/main.jar"
  cat > "$car_work/component-descriptor.yaml" <<EOF
name: ${component_name}
version: 0.1.0
component: ${component_name}
subsystem: testsubsystemmulti
EOF
  (cd "$car_work" && zip -qr "$target_car" component-descriptor.yaml component)
}

create_car "alphacomp" "$WORK_DIR/alphacomp.jar" "$WORK_DIR/component.d/alphacomp.car"
create_car "betacomp" "$WORK_DIR/betacomp.jar" "$WORK_DIR/component.d/betacomp.car"

SAR_WORK="$WORK_DIR/testsubsystemmulti.sar.d"
mkdir -p "$SAR_WORK"
cat > "$SAR_WORK/subsystem-descriptor.yaml" <<'EOF'
subsystem: testsubsystemmulti
version: 0.1.0
components:
  - component: alphacomp
    coordinate: org.simplemodeling.car:alphacomp:0.1.0
  - component: betacomp
    coordinate: org.simplemodeling.car:betacomp:0.1.0
EOF
(cd "$SAR_WORK" && zip -qr "$WORK_DIR/component.d/testsubsystemmulti.sar" subsystem-descriptor.yaml)

COMMON_ARGS=(
  --no-default-components
  --component-dir "$WORK_DIR/component.d"
  --textus.subsystem=testsubsystemmulti
)

echo "--- subsystem help"
bash ../../bin/cncf command meta.help --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- alpha component help"
bash ../../bin/cncf command meta.help alphacomp --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- beta component help"
bash ../../bin/cncf command meta.help betacomp --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- alpha operation help"
bash ../../bin/cncf command help alphacomp.main.hello "${COMMON_ARGS[@]}"

echo
echo "--- beta operation help"
bash ../../bin/cncf command help betacomp.main.hello "${COMMON_ARGS[@]}"

echo
echo "--- execute alpha"
bash ../../bin/cncf command alphacomp.main.hello "${COMMON_ARGS[@]}"

echo
echo "--- execute beta"
bash ../../bin/cncf command betacomp.main.hello "${COMMON_ARGS[@]}"
