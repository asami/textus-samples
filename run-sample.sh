#!/usr/bin/env bash
set -euo pipefail

repo_root="$(cd "$(dirname "$0")" && pwd)"

sample="${1:-}"
if [[ -z "$sample" ]]; then
  echo "Usage: $0 <sample-name> [args...]" >&2
  exit 1
fi
shift

sample_dir="$repo_root/samples/$sample"
runner="$sample_dir/run.sh"

if [[ ! -d "$sample_dir" ]]; then
  echo "Unknown sample: $sample" >&2
  exit 1
fi

if [[ ! -x "$runner" ]]; then
  echo "Sample runner is missing or not executable: $runner" >&2
  exit 1
fi

exec "$runner" "$@"
