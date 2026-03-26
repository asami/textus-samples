#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

if [[ $# -eq 0 ]]; then
  exec sbt --batch "runMain org.goldenport.cncf.CncfMain client --help"
fi

sbt_command="runMain org.goldenport.cncf.CncfMain client"
for arg in "$@"; do
  sbt_command+=" ${arg}"
done

exec sbt --batch "$sbt_command"
