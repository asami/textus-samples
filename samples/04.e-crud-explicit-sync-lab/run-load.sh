#!/bin/sh

set -eu

if [ "$#" -ne 1 ]; then
  echo "usage: $0 <entity-id>" >&2
  exit 1
fi

item_id="$1"

exec bash ../../bin/cncf --discover=classes command \
  crud.entity.load-item \
  --id "${item_id}"
