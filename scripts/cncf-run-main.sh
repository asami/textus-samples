#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
# shellcheck source=./cncf-common.sh
source "$SCRIPT_DIR/cncf-common.sh"

usage() {
  cat >&2 <<'EOF'
Usage:
  cncf-run-main.sh --sample-dir <dir> [--cncf-main-class <fqcn>] [--sample-main-class <fqcn>] [--command-path <path>] [--discover-classes] [--workspace <path>] [--component-repository <spec>]... [--] [args...]

Examples:
  cncf-run-main.sh --sample-dir samples/01-minimal \
    --cncf-main-class org.goldenport.cncf.CncfMain \
    --command-path minimal.main.hello
EOF
  exit 1
}

sample_dir=""
cncf_main_class="org.goldenport.cncf.CncfMain"
sample_main_class=""
command_path=""
discover_classes="0"
workspace=""
component_repositories=()

while [[ $# -gt 0 ]]; do
  case "$1" in
    --sample-dir)
      sample_dir="${2:-}"
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
    --)
      shift
      break
      ;;
    *)
      break
      ;;
  esac
done

cncf_require_value "--sample-dir" "$sample_dir"

repo_root="$(cncf_repo_root)"
cd "$repo_root/$sample_dir"

main_class="$cncf_main_class"
if [[ -n "$sample_main_class" ]]; then
  main_class="$sample_main_class"
fi

sbt_args=(--batch "runMain ${main_class}")
if [[ "$discover_classes" == "1" ]]; then
  sbt_args[1]+=" --discover=classes"
fi
if [[ -n "$workspace" ]]; then
  sbt_args[1]+=" --workspace ${workspace}"
fi
if [[ ${#component_repositories[@]} -gt 0 ]]; then
  for repo in "${component_repositories[@]}"; do
    sbt_args[1]+=" --component-repository=${repo}"
  done
fi
if [[ -n "$command_path" ]]; then
  sbt_args[1]+=" command ${command_path}"
fi
if [[ $# -gt 0 ]]; then
  for arg in "$@"; do
    sbt_args[1]+=" ${arg}"
  done
fi

exec sbt "${sbt_args[@]}"
