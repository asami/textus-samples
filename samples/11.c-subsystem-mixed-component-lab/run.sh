#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
cd "$SCRIPT_DIR"
trap 'rm -rf "$WORK_DIR"' EXIT

sbt --batch compile >/dev/null

CLASS_DIR="target/scala-3.3.7/classes"
if [[ ! -d "$CLASS_DIR/genericcomp" || ! -d "$CLASS_DIR/bundledcomp" ]]; then
  echo "Compiled component classes not found." >&2
  exit 1
fi

mkdir -p "$WORK_DIR/component.d"

(cd "$CLASS_DIR" && zip -qr "$WORK_DIR/genericcomp.jar" genericcomp)
(cd "$CLASS_DIR" && zip -qr "$WORK_DIR/bundledcomp.jar" bundledcomp)

create_car() {
  local component_name="$1"
  local component_jar="$2"
  local target_car="$3"
  local subsystem_name="$4"
  local car_work="$WORK_DIR/${component_name}.car.d"
  mkdir -p "$car_work/component"
  cp "$component_jar" "$car_work/component/main.jar"
  cat > "$car_work/component-descriptor.yaml" <<EOF
name: ${component_name}
version: 0.1.0
component: ${component_name}
subsystem: ${subsystem_name}
EOF
  (cd "$car_work" && zip -qr "$target_car" component-descriptor.yaml component)
}

create_car "genericcomp" "$WORK_DIR/genericcomp.jar" "$WORK_DIR/component.d/genericcomp.car" "testsubsystemmixed"
create_car "bundledcomp" "$WORK_DIR/bundledcomp.jar" "$WORK_DIR/bundledcomp.car" "testsubsystemmixed"

SAR_WORK="$WORK_DIR/testsubsystemmixed.sar.d"
mkdir -p "$SAR_WORK/component"
cat > "$SAR_WORK/subsystem-descriptor.yaml" <<'EOF'
subsystem: testsubsystemmixed
version: 0.1.0
components:
  - component: genericcomp
    coordinate: org.simplemodeling.car:genericcomp:0.1.0
  - component: bundledcomp
    coordinate: org.simplemodeling.car:bundledcomp:0.1.0
EOF
cp "$WORK_DIR/bundledcomp.car" "$SAR_WORK/component/bundledcomp.car"
(cd "$SAR_WORK" && zip -qr "$WORK_DIR/component.d/testsubsystemmixed.sar" subsystem-descriptor.yaml component)

COMMON_ARGS=(
  --no-default-components
  --component-dir "$WORK_DIR/component.d"
  --textus.subsystem=testsubsystemmixed
)

echo "--- subsystem help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help --format yaml

echo
echo "--- generic component help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help genericcomp --format yaml

echo
echo "--- bundled component help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help bundledcomp --format yaml

echo
echo "--- generic operation help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" help genericcomp.main.hello

echo
echo "--- bundled operation help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" help bundledcomp.main.hello

echo
echo "--- execute generic"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" genericcomp.main.hello

echo
echo "--- execute bundled"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" bundledcomp.main.hello
