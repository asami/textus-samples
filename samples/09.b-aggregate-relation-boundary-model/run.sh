#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"

sbt --batch compile >/dev/null

echo
echo '--- help aggregate-relation-boundary-sample ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command help aggregate-relation-boundary-sample
echo
echo '--- help aggregate service ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command help aggregate-relation-boundary-sample.aggregate
echo
echo '--- meta describe ---'
bash "$ROOT_DIR/bin/cncf" --discover=classes command aggregate-relation-boundary-sample.meta.describe --format yaml
