#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo
echo '--- help aggregate-external-update-sample ---'
cncf dev command --project . help aggregate-external-update-sample
echo
echo '--- help cancel-order ---'
cncf dev command --project . help aggregate-external-update-sample.order.cancel-order
echo
echo '--- meta describe ---'
cncf dev command --project . aggregate-external-update-sample.meta.describe --format yaml
