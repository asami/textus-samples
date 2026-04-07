#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"

bash "$ROOT_DIR/bin/setup" cozy
sbt --batch clean compile >/dev/null

echo
echo '--- help aggregate-external-update-sample ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command help aggregate-external-update-sample
echo
echo '--- help cancel-order ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command help aggregate-external-update-sample.order.cancel-order
echo
echo '--- meta describe ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command aggregate-external-update-sample.meta.describe --format yaml
