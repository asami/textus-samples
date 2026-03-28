#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

exec sbt --batch "runMain org.goldenport.cncf.CncfMain client http post /event-driven/event/emit-event name=alpha title=Alpha"
