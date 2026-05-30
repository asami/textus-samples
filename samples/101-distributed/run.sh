#!/usr/bin/env bash
set -euo pipefail

sample_name="$(basename "$(cd "$(dirname "$0")" && pwd)")"
echo "Run command is not configured for $sample_name yet." >&2
echo "Define the sample runtime source and wire this script to a direct cncf dev command." >&2
exit 1
