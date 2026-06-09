#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project-dev . help job-control-lab
cncf dev command --project-dev . help job-control-lab.item.create-item
cncf dev command --project-dev . job-control-lab.meta.describe --format yaml
