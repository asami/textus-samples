#!/usr/bin/env bash
set -euo pipefail

echo "--- subsystem help"
bash ../../bin/cncf \
  command meta.help --format yaml \
  --no-default-components \
  --textus.runtime.subsystem=testsubsystem

echo
echo "--- component help"
bash ../../bin/cncf \
  command meta.help testcomp --format yaml \
  --no-default-components \
  --textus.runtime.subsystem=testsubsystem

echo
echo "--- operation help"
bash ../../bin/cncf \
  command help testcomp.main.hello \
  --no-default-components \
  --textus.runtime.subsystem=testsubsystem

echo
echo "--- execute"
bash ../../bin/cncf \
  command testcomp.main.hello \
  --no-default-components \
  --textus.runtime.subsystem=testsubsystem
