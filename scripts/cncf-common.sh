#!/usr/bin/env bash
set -euo pipefail

cncf_repo_root() {
  cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd
}

cncf_sample_dir_from_script() {
  local script_path="$1"
  cd "$(dirname "$script_path")" && pwd
}

cncf_require_value() {
  local name="$1"
  local value="${2:-}"
  if [[ -z "$value" ]]; then
    echo "Required value is missing: $name" >&2
    exit 2
  fi
}
