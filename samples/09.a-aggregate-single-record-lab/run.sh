#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo
echo '--- help single-record-sample ---'
cncf dev command --project . --component-dev-dir . help single-record-sample
echo
echo '--- help aggregate load-order ---'
cncf dev command --project . --component-dev-dir . help single-record-sample.aggregate.load-order
echo
echo '--- meta describe ---'
cncf dev command --project . --component-dev-dir . single-record-sample.meta.describe --format yaml
