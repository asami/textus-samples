#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

exec cncf dev client --project-dev . event-driven.event.load-effect --privilege content_admin
