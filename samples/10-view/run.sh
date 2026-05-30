#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

LOAD_ID="tokyo-sales-entity-person-1742198400000-abcd1234"

echo "--- help"
cncf dev command --project . --component-dev-dir . help view-sample.view.load-person

echo
echo "--- load"
cncf dev command --project . --component-dev-dir . view-sample.view.load-person --id "$LOAD_ID"

echo
echo "--- search"
cncf dev command --project . --component-dev-dir . view-sample.view.search-person-record --name Alice
