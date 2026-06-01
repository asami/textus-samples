#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . help crud-nested-value-sample.entity.create-person
cncf dev command --project . help crud-nested-value-sample.entity.load-person
cncf dev command --project . crud-nested-value-sample.meta.describe --format yaml
bash run-datastore.sh
