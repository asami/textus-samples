#!/usr/bin/env bash
set -euo pipefail

echo "--- subsystem help"
bash ../../bin/cncf \
  --no-default-components \
  command meta.help --format yaml \
  --cncf.config.file=subsystem-sar-dir.conf

echo
echo "--- component help"
bash ../../bin/cncf \
  --no-default-components \
  command meta.help subsystem --format yaml \
  --cncf.config.file=subsystem-sar-dir.conf

echo
echo "--- operation help"
bash ../../bin/cncf \
  --no-default-components \
  command help subsystem.main.hello \
  --cncf.config.file=subsystem-sar-dir.conf

echo
echo "--- execute"
bash ../../bin/cncf \
  --no-default-components \
  command subsystem.main.hello \
  --cncf.config.file=subsystem-sar-dir.conf
