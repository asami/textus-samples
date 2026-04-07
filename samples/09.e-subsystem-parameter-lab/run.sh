#!/usr/bin/env bash
set -euo pipefail

echo "--- subsystem help"
bash ../../bin/cncf \
  command meta.help --format yaml \
  --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml \
  --component-repository=component-dir:../09-subsystem/component.d

echo
echo "--- component help"
bash ../../bin/cncf \
  command meta.help testcomp --format yaml \
  --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml \
  --component-repository=component-dir:../09-subsystem/component.d

echo
echo "--- operation help"
bash ../../bin/cncf \
  command help testcomp.main.hello \
  --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml \
  --component-repository=component-dir:../09-subsystem/component.d

echo
echo "--- execute"
bash ../../bin/cncf \
  command testcomp.main.hello \
  --textus.runtime.subsystem.descriptor=../09-subsystem/subsystem.cml \
  --component-repository=component-dir:../09-subsystem/component.d
