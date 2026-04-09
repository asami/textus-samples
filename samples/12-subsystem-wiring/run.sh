#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

sbt --batch compile >/dev/null

CLASS_DIR="target/scala-3.3.7/classes"
if [[ ! -d "$CLASS_DIR/caller" || ! -d "$CLASS_DIR/callee" ]]; then
  echo "Compiled component classes not found." >&2
  exit 1
fi

mkdir -p "$WORK_DIR/component.d"

(cd "$CLASS_DIR" && zip -qr "$WORK_DIR/callercomp.jar" caller)
(cd "$CLASS_DIR" && zip -qr "$WORK_DIR/calleecomp.jar" callee)

create_car() {
  local component_name="$1"
  local component_jar="$2"
  local target_car="$3"
  local car_work="$WORK_DIR/${component_name}.car.d"
  mkdir -p "$car_work/component" "$car_work/meta"
  cp "$component_jar" "$car_work/component/main.jar"
  cat > "$car_work/meta/manifest.json" <<EOF
{"name":"${component_name}","version":"0.1.0","component":"${component_name}","subsystem":"testsubsystemwiring"}
EOF
  (cd "$car_work" && zip -qr "$target_car" component meta)
}

create_car "callercomp" "$WORK_DIR/callercomp.jar" "$WORK_DIR/component.d/callercomp.car"
create_car "calleecomp" "$WORK_DIR/calleecomp.jar" "$WORK_DIR/component.d/calleecomp.car"

SAR_WORK="$WORK_DIR/testsubsystemwiring.sar.d"
mkdir -p "$SAR_WORK"
cat > "$SAR_WORK/subsystem-descriptor.yaml" <<'EOF'
subsystem: testsubsystemwiring
version: 0.1.0
components:
  - component: callercomp
    coordinate: org.simplemodeling.car:callercomp:0.1.0
    api:
      hello-target:
        service: main
        operation: hello
  - component: calleecomp
    coordinate: org.simplemodeling.car:calleecomp:0.1.0
    spi:
      hello-provider:
        service: main
        operation: hello
wiring:
  callercomp:
    main:
      hello:
        target_component: calleecomp
        target_service: main
        target_operation: hello
EOF
(cd "$SAR_WORK" && zip -qr "$WORK_DIR/component.d/testsubsystemwiring.sar" subsystem-descriptor.yaml)

COMMON_ARGS=(
  --no-default-components
  --component-repository="component-dir:$WORK_DIR/component.d"
  --textus.runtime.subsystem=testsubsystemwiring
  --textus.runtime.subsystem.file="$WORK_DIR/component.d/testsubsystemwiring.sar"
)

echo "--- subsystem help"
bash ../../bin/cncf command meta.help --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- caller component help"
bash ../../bin/cncf command meta.help callercomp --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- callee component help"
bash ../../bin/cncf command meta.help calleecomp --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- caller operation help"
bash ../../bin/cncf command help callercomp.main.hello "${COMMON_ARGS[@]}"

echo
echo "--- callee operation help"
bash ../../bin/cncf command help calleecomp.main.hello "${COMMON_ARGS[@]}"

echo
echo "--- assembly report"
bash ../../bin/cncf command admin.assembly.report --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- execute caller"
bash ../../bin/cncf command callercomp.main.hello --calltree --format yaml "${COMMON_ARGS[@]}"

echo
echo "--- execute callee"
bash ../../bin/cncf command calleecomp.main.hello "${COMMON_ARGS[@]}"
