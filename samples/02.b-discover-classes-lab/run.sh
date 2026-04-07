#!/usr/bin/env bash
set -euo pipefail

sbt --batch clean compile >/dev/null

echo "--- component help"
bash ../../bin/cncf \
  --discover=classes \
  command meta.help testcomp --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --discover=classes \
  command help testcomp.main.hello

echo
echo "--- execute"
bash ../../bin/cncf \
  --discover=classes \
  command testcomp.main.hello
