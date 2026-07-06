#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help operation-contract-sample.greeting.greeting
cncf command operation-contract-sample.meta.describe --format yaml
