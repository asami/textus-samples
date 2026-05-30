#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

dbpath="target/cncf.d/02f-crud-nested-value-lab.sqlite"
mkdir -p target/cncf.d
rm -f "$dbpath"

person_id="$(
  bash run-create.sh |
    awk '/^id: / {print $2}' |
    tail -n 1
)"

bash run-load.sh "$person_id"
