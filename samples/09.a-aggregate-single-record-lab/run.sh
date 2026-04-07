#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"

sbt --batch compile >/dev/null

echo
echo '--- help single-record-sample ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command help single-record-sample
echo
echo '--- help aggregate load-order ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command help single-record-sample.aggregate.load-order
echo
echo '--- meta describe ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command single-record-sample.meta.describe --format yaml
