#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

sbt_command="runMain org.goldenport.cncf.CncfMain --discover=classes server"
for arg in "$@"; do
  sbt_command+=" ${arg}"
done

exec sbt --batch "$sbt_command"
