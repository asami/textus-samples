#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [ "$#" -ne 1 ]; then
  echo "usage: $0 <entity-id>" >&2
  exit 1
fi

item_id="$1"
SERVER_PORT="${CNCF_SAMPLE_SERVER_PORT:-19543}"

exec cncf client \
  crud.entity.load-item \
  --baseurl "http://localhost:${SERVER_PORT}" \
  --id "${item_id}"
