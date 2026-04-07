#!/usr/bin/env bash
set -euo pipefail

sbt --batch compile >/dev/null

echo "--- component help"
bash ../../bin/cncf \
  --discover=classes \
  command meta.help component-cml-sample --format yaml

echo
echo "--- operation help"
bash ../../bin/cncf \
  --discover=classes \
  command help component-cml-sample.greeting.greeting

echo
echo "--- metadata"
bash ../../bin/cncf \
  --discover=classes \
  command component-cml-sample.meta.describe --format yaml
