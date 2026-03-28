#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

if [[ $# -ne 1 ]]; then
  echo "usage: $0 <entity-id>" >&2
  exit 1
fi

item_id="$1"

exec sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command crud.entity.load-item --id ${item_id}"
