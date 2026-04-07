#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

bash ../../bin/cncf --discover=classes command help job-control-lab
bash ../../bin/cncf --discover=classes command help job-control-lab.item.create-item
bash ../../bin/cncf --discover=classes command job-control-lab.meta.describe --format yaml
