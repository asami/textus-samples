#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"
cd "$SCRIPT_DIR"

sbt --batch compile >/dev/null

echo
echo '--- help aggregate-relation-boundary-sample ---'
cncf command help aggregate-relation-boundary-sample
echo
echo '--- help aggregate service ---'
cncf command help aggregate-relation-boundary-sample.aggregate
echo
echo '--- meta describe ---'
cncf command aggregate-relation-boundary-sample.meta.describe --format yaml
