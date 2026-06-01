#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

echo "--- help"
cncf dev command --project . help simple-entity-view-sample.view.load-person

echo
echo "--- load"
cncf dev command --project . simple-entity-view-sample.view.load-person \
  --id tokyo-sales-entity-person-1742198400000-abcd1234

echo
echo "--- search"
cncf dev command --project . simple-entity-view-sample.view.search-person-record --name Alice

echo
echo "--- meta"
cncf dev command --project . simple-entity-view-sample.meta.describe --format yaml
