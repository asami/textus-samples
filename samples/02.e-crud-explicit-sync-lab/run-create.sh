#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

exec sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes --textus.runtime.command.execution-mode sync-direct-no-job command crud.entity.create-item --name alpha --title Alpha"
