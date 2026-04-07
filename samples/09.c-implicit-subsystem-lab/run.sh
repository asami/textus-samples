#!/usr/bin/env bash
set -euo pipefail

echo "--- subsystem help"
bash ../../bin/cncf --discover=classes \
  command meta.help --format yaml

echo
echo "--- component help"
bash ../../bin/cncf --discover=classes \
  command meta.help subsystem --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf --discover=classes \
  command help subsystem.main.hello

echo
echo "--- execute"
bash ../../bin/cncf --discover=classes \
  command subsystem.main.hello
