#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
# shellcheck source=./cncf-common.sh
source "$SCRIPT_DIR/cncf-common.sh"

usage() {
  cat >&2 <<'EOF'
Usage:
  sample-runner.sh --script-path <path> [--cncf-main-class <fqcn>] [--sample-main-class <fqcn>] [--command-path <path>] [--discover-classes] [--workspace <path>] [--component-repository <spec>]... [--repository-dir <path>] [--component-dir <path>] [--] [args...]
EOF
  exit 1
}

script_path=""
cncf_main_class="org.goldenport.cncf.CncfMain"
sample_main_class=""
command_path=""
discover_classes="0"
workspace=""
component_repositories=()
repository_dirs=()
component_dirs=()

while [[ $# -gt 0 ]]; do
  case "$1" in
    --script-path)
      script_path="${2:-}"
      shift 2
      ;;
    --cncf-main-class)
      cncf_main_class="${2:-}"
      shift 2
      ;;
    --sample-main-class)
      sample_main_class="${2:-}"
      shift 2
      ;;
    --command-path)
      command_path="${2:-}"
      shift 2
      ;;
    --discover-classes)
      discover_classes="1"
      shift
      ;;
    --workspace)
      workspace="${2:-}"
      shift 2
      ;;
    --component-repository)
      component_repositories+=("${2:-}")
      shift 2
      ;;
    --repository-dir)
      repository_dirs+=("${2:-}")
      shift 2
      ;;
    --component-dir)
      component_dirs+=("${2:-}")
      shift 2
      ;;
    --)
      shift
      break
      ;;
    *)
      break
      ;;
  esac
done

cncf_require_value "--script-path" "$script_path"

repo_root="$(cncf_repo_root)"
sample_dir="$(cncf_sample_dir_from_script "$script_path")"
relative_sample_dir="${sample_dir#"$repo_root"/}"

extra_repo_args=()
if [[ ${#component_repositories[@]} -gt 0 ]]; then
  for repo in "${component_repositories[@]}"; do
    extra_repo_args+=(--component-repository "$repo")
  done
fi
if [[ ${#repository_dirs[@]} -gt 0 ]]; then
  for dir in "${repository_dirs[@]}"; do
    extra_repo_args+=(--repository-dir "$dir")
  done
fi
if [[ ${#component_dirs[@]} -gt 0 ]]; then
  for dir in "${component_dirs[@]}"; do
    extra_repo_args+=(--component-dir "$dir")
  done
fi

forward_args=(
  --cncf-main-class "$cncf_main_class"
)

if [[ -n "$sample_main_class" ]]; then
  forward_args+=(--sample-main-class "$sample_main_class")
fi
if [[ -n "$command_path" ]]; then
  forward_args+=(--command-path "$command_path")
fi
if [[ "$discover_classes" == "1" ]]; then
  forward_args+=(--discover-classes)
fi
if [[ -n "$workspace" ]]; then
  forward_args+=(--workspace "$workspace")
fi
if [[ ${#extra_repo_args[@]} -gt 0 ]]; then
  forward_args+=("${extra_repo_args[@]}")
fi

cd "$sample_dir"
exec bash "$repo_root/bin/cncf" \
  "${forward_args[@]}" \
  -- \
  "$@"
