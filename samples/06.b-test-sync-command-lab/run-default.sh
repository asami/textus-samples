#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command TestSync.Item.createItem --name beta --title Beta
cncf command TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml
