#!/usr/bin/env bash
set -euo pipefail

LOAD_ID="tokyo-sales-entity-person-1742198400000-abcd1234"

echo "--- help"
bash ../../bin/cncf --discover=classes \
  command help view-sample.view.load-person

echo
echo "--- load"
bash ../../bin/cncf --discover=classes \
  command view-sample.view.load-person --id "$LOAD_ID"

echo
echo "--- search"
bash ../../bin/cncf --discover=classes \
  command view-sample.view.search-person-record --name Alice
