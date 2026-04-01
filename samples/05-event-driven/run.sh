#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven"
