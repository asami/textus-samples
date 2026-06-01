#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf dev command --project . help event-driven
cncf dev command --project . help event-driven.event.emit-event
cncf dev command --project . help event-driven.event.load-effect
cncf dev command --project . event-driven.meta.describe --format yaml
