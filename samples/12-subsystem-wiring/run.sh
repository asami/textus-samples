#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
WORK_DIR="$(mktemp -d)"
cd "$SCRIPT_DIR"
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
  mkdir -p "$car_work/component"
  cp "$component_jar" "$car_work/component/main.jar"
  cat > "$car_work/component-descriptor.yaml" <<EOF
name: ${component_name}
version: 0.1.0
component: ${component_name}
subsystem: testsubsystemwiring
EOF
  (cd "$car_work" && zip -qr "$target_car" component-descriptor.yaml component)
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
        api: hello-target
        target_component: calleecomp
        target_spi: hello-provider
        glue:
          request/mode: passthrough
          response/mode: passthrough
EOF
cat > "$SAR_WORK/assembly-descriptor.yaml" <<'EOF'
kind: assembly-descriptor
subsystem: testsubsystemwiring
version: 0.1.0
wiring:
  - from:
      component: callercomp
      service: main
      operation: hello
      api: hello-target
    to:
      component: calleecomp
      spi: hello-provider
      service: main
      operation: hello
    glue:
      request/mode: passthrough
      response/mode: passthrough
    mode: assembly-descriptor-routing
EOF
(cd "$SAR_WORK" && zip -qr "$WORK_DIR/component.d/testsubsystemwiring.sar" subsystem-descriptor.yaml assembly-descriptor.yaml)

cat > "$WORK_DIR/override-assembly-descriptor.yaml" <<'EOF'
kind: assembly-descriptor
subsystem: testsubsystemwiring
version: 0.1.0
wiring:
  - from:
      component: callercomp
      service: main
      operation: hello
      api: hello-target
    to:
      component: calleecomp
      spi: hello-provider
      service: main
      operation: hello
    glue:
      request/mode: passthrough
      response/mode: passthrough
    mode: config-assembly-descriptor-routing
EOF

COMMON_ARGS=(
  --component-dir "$WORK_DIR/component.d"
  --textus.subsystem=testsubsystemwiring
  --textus.subsystem.file="$WORK_DIR/component.d/testsubsystemwiring.sar"
)

echo "--- subsystem help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help --format yaml

echo
echo "--- caller component help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help callercomp --format yaml

echo
echo "--- callee component help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" meta.help calleecomp --format yaml

echo
echo "--- caller operation help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" help callercomp.main.hello

echo
echo "--- callee operation help"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" help calleecomp.main.hello

echo
echo "--- assembly report"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" admin.assembly.report --format yaml

echo
echo "--- assembly descriptor"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" admin.assembly.descriptor --format yaml

echo
echo "--- assembly descriptor override"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" --textus.assembly.descriptor="$WORK_DIR/override-assembly-descriptor.yaml" admin.assembly.descriptor --format yaml

echo
echo "--- assembly diagram"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" admin.assembly.diagram

echo
echo "--- execute caller"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" callercomp.main.hello --calltree --format yaml

echo
echo "--- execute callee"
cncf dev command --project-dev . "${COMMON_ARGS[@]}" calleecomp.main.hello
