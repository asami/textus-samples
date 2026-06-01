#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . help operation-contract-sample.greeting.greeting
cncf dev command --project . operation-contract-sample.meta.describe --format yaml
