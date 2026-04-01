#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

exec sbt --no-server --batch "runMain org.sample.jobcontroldemo.JobControlDemo"
