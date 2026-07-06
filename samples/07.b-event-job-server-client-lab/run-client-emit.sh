#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec cncf client event-driven.event.emit-event --name alpha --title Alpha --privilege content_admin
