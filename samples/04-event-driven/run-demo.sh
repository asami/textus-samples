#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

exec sbt --batch "runMain org.sample.eventdriven.EventFlowDemo"
