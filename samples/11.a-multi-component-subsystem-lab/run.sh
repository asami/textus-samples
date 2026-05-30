#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
cd "$SCRIPT_DIR"
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
cncf dev command --project . "${COMMON_ARGS[@]}" meta.help --format yaml

echo
echo "--- alpha component help"
cncf dev command --project . "${COMMON_ARGS[@]}" meta.help alphacomp --format yaml

echo
echo "--- beta component help"
cncf dev command --project . "${COMMON_ARGS[@]}" meta.help betacomp --format yaml

echo
echo "--- alpha operation help"
cncf dev command --project . "${COMMON_ARGS[@]}" help alphacomp.main.hello

echo
echo "--- beta operation help"
cncf dev command --project . "${COMMON_ARGS[@]}" help betacomp.main.hello

echo
echo "--- execute alpha"
cncf dev command --project . "${COMMON_ARGS[@]}" alphacomp.main.hello

echo
echo "--- execute beta"
cncf dev command --project . "${COMMON_ARGS[@]}" betacomp.main.hello
