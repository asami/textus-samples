#!/usr/bin/env bash
set -euo pipefail

echo "--- help"
bash ../../bin/cncf --discover=classes \
  command help simple-entity-view-sample.view.load-person

echo
echo "--- load"
bash ../../bin/cncf --discover=classes \
  command simple-entity-view-sample.view.load-person \
  --id tokyo-sales-entity-person-1742198400000-abcd1234

echo
echo "--- search"
bash ../../bin/cncf --discover=classes \
  command simple-entity-view-sample.view.search-person-record --name Alice

echo
echo "--- meta"
bash ../../bin/cncf --discover=classes \
  command simple-entity-view-sample.meta.describe --format yaml
