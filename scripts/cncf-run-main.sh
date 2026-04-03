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

runtime_cp_file="target/cncf.d/runtime-classpath.txt"
class_dir="$(find target -path '*/classes' -type d 2>/dev/null | sort | tail -n 1)"

needs_prepare="0"
if [[ ! -f "$runtime_cp_file" || -z "$class_dir" || ! -d "$class_dir" ]]; then
  needs_prepare="1"
elif find src/main build.sbt project/plugins.sbt -type f -newer "$runtime_cp_file" -print -quit 2>/dev/null | grep -q .; then
  needs_prepare="1"
fi

if [[ "$needs_prepare" == "1" ]]; then
  mkdir -p "$(dirname "$runtime_cp_file")"
  prepare_log="$(mktemp)"
  if sbt --batch cozyPrepareRuntime >"$prepare_log" 2>&1; then
    cat "$prepare_log"
  else
    cat "$prepare_log" >&2
    if grep -q "Not a valid command: cozyPrepareRuntime" "$prepare_log"; then
      cp_log="$(mktemp)"
      trap 'rm -f "$prepare_log" "$cp_log"' EXIT
      sbt --batch "Compile / compile" "export Compile / fullClasspath" >"$cp_log" 2>&1
      sed '$d' "$cp_log"
      classpath_line="$(grep -v '^\[' "$cp_log" | tail -n 1)"
      if [[ -z "$classpath_line" ]]; then
        echo "Failed to extract runtime classpath from plain sbt project." >&2
        exit 1
      fi
      printf '%s' "$classpath_line" | tr ':' '\n' >"$runtime_cp_file"
    else
      exit 1
    fi
  fi
  rm -f "$prepare_log"
fi

if [[ ! -f "$runtime_cp_file" ]]; then
  echo "Runtime classpath file not found: $runtime_cp_file" >&2
  exit 1
fi

classpath="$(paste -sd: "$runtime_cp_file")"
java_args=("$main_class")
if [[ "$discover_classes" == "1" ]]; then
  java_args+=(--discover=classes)
fi
if [[ -n "$workspace" ]]; then
  java_args+=(--workspace "$workspace")
fi
if [[ ${#component_repositories[@]} -gt 0 ]]; then
  for repo in "${component_repositories[@]}"; do
    java_args+=(--component-repository="$repo")
  done
fi
if [[ -n "$command_path" ]]; then
  java_args+=(command "$command_path")
fi
if [[ $# -gt 0 ]]; then
  java_args+=("$@")
fi

exec java -cp "$classpath" "${java_args[@]}"
