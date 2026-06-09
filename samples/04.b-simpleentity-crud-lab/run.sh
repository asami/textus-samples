#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project-dev . help simple-entity-crud-lab
cncf dev command --project-dev . help simple-entity-crud-lab.item
cncf dev command --project-dev . help simple-entity-crud-lab.item.create-item
cncf dev command --project-dev . simple-entity-crud-lab.meta.describe --format yaml
