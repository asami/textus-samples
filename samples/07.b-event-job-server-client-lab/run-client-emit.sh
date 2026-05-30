#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec cncf dev client --project . --component-dev-dir . event-driven.event.emit-event --name alpha --title Alpha
