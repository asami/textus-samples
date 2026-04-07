#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec bash ../../bin/cncf --discover=classes client crud.entity.create-item --name alpha --title Alpha
