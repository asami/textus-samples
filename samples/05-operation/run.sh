#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . --component-dev-dir . help operation-contract-sample.greeting.greeting
cncf dev command --project . --component-dev-dir . operation-contract-sample.meta.describe --format yaml
