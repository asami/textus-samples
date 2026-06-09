#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project-dev . help event-driven
cncf dev command --project-dev . help event-driven.event.emit-event
cncf dev command --project-dev . help event-driven.event.load-effect
cncf dev command --project-dev . event-driven.meta.describe --format yaml
