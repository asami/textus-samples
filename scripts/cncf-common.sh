#!/usr/bin/env bash
set -euo pipefail

cncf_repo_root() {
  cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd
}

cncf_sample_dir_from_script() {
  local script_path="$1"
  cd "$(dirname "$script_path")" && pwd
}

cncf_sample_dir_from_cwd() {
  local repo_root
  repo_root="$(cncf_repo_root)"
  local cwd
  cwd="$(pwd)"
  case "$cwd" in
    "$repo_root"/*)
      printf '%s\n' "${cwd#"$repo_root"/}"
      ;;
    *)
      echo "" 
      ;;
  esac
}

cncf_require_value() {
  local name="$1"
  local value="${2:-}"
  if [[ -z "$value" ]]; then
    echo "Required value is missing: $name" >&2
    exit 2
  fi
}
