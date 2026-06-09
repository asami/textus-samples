#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

LOAD_ID="tokyo-sales-entity-person-1742198400000-abcd1234"

echo "--- help"
cncf dev command --project-dev . help named-view-sample.view.load-person-summary

echo
echo "--- summary load"
cncf dev command --project-dev . named-view-sample.view.load-person-summary --id "$LOAD_ID"

echo
echo "--- summary search"
cncf dev command --project-dev . named-view-sample.view.search-person-summary-record --city Tokyo

echo
echo "--- custom query search"
cncf dev command --project-dev . named-view-sample.view.search-person --view search_by_city --city Tokyo

echo
echo "--- detail load"
cncf dev command --project-dev . named-view-sample.view.load-person-detail --id "$LOAD_ID"

echo
echo "--- meta describe"
cncf dev command --project-dev . named-view-sample.meta.describe --format yaml
