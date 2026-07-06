#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help simple-entity-crud-lab
cncf command help simple-entity-crud-lab.item
cncf command help simple-entity-crud-lab.item.create-item
cncf command simple-entity-crud-lab.meta.describe --format yaml
