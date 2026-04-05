#!/usr/bin/env bash
set -euo pipefail

LOAD_ID="tokyo-sales-entity-person-1742198400000-abcd1234"

echo "--- help"
bash ../../bin/cncf --discover=classes \
  command help named-view-sample.view.load-person-summary

echo
echo "--- summary load"
bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.load-person-summary --id "$LOAD_ID"

echo
echo "--- summary search"
bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.search-person-summary-record --city Tokyo

echo
echo "--- custom query search"
bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.search-person --view search_by_city --city Tokyo

echo
echo "--- detail load"
bash ../../bin/cncf --discover=classes \
  command named-view-sample.view.load-person-detail --id "$LOAD_ID"

echo
echo "--- meta describe"
bash ../../bin/cncf --discover=classes \
  command named-view-sample.meta.describe --format yaml
