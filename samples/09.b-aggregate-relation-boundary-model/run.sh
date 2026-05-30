#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo
echo '--- help aggregate-relation-boundary-sample ---'
cncf dev command --project . --component-dev-dir . help aggregate-relation-boundary-sample
echo
echo '--- help aggregate service ---'
cncf dev command --project . --component-dev-dir . help aggregate-relation-boundary-sample.aggregate
echo
echo '--- meta describe ---'
cncf dev command --project . --component-dev-dir . aggregate-relation-boundary-sample.meta.describe --format yaml
