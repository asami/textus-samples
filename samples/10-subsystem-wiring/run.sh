#!/usr/bin/env bash
set -euo pipefail

sample_name="$(basename "$(cd "$(dirname "$0")" && pwd)")"
echo "Run command is not configured for $sample_name yet." >&2
echo "Wire this script to scripts/sample-runner.sh once the sample main class is defined." >&2
exit 1
