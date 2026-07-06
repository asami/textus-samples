#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

cncf command help event-driven
cncf command help event-driven.event.emit-event
cncf command help event-driven.event.load-effect
cncf command event-driven.meta.describe --format yaml
