#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ $# -ne 1 ]]; then
  echo "usage: $0 <entity-id>" >&2
  exit 1
fi

item_id="$1"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$0" \
  --discover-classes \
  -- \
  command \
  crud.entity.load-item \
  --id "${item_id}"
