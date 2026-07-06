#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help job-control-lab
cncf command help job-control-lab.item.create-item
cncf command job-control-lab.meta.describe --format yaml
