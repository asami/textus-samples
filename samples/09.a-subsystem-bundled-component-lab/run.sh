#!/usr/bin/env bash
set -euo pipefail

echo "--- subsystem help"
bash ../../bin/cncf \
  command meta.help --format yaml \
  --no-default-components \
  --textus.runtime.subsystem=subsystem

echo
echo "--- component help"
bash ../../bin/cncf \
  command meta.help subsystem --format yaml \
  --no-default-components \
  --textus.runtime.subsystem=subsystem

echo
echo "--- operation help"
bash ../../bin/cncf \
  command help subsystem.main.hello \
  --no-default-components \
  --textus.runtime.subsystem=subsystem

echo
echo "--- execute"
bash ../../bin/cncf \
  command subsystem.main.hello \
  --no-default-components \
  --textus.runtime.subsystem=subsystem
