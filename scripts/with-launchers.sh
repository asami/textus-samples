#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

DEFAULT_CNCF_VERSION="$(tr -d '[:space:]' < "$REPO_ROOT/versions/cncf-version.conf")"
DEFAULT_COZY_VERSION="$(tr -d '[:space:]' < "$REPO_ROOT/versions/cozy-version.conf")"
DEFAULT_SBT_COZY_VERSION="$(tr -d '[:space:]' < "$REPO_ROOT/versions/sbt-cozy-version.conf")"

cncf_version="${CNCF_VERSION:-$DEFAULT_CNCF_VERSION}"
cncf_runtime="${CNCF_RUNTIME:-}"
cncf_runtime_dev_dir="${CNCF_RUNTIME_DEV_DIR:-}"
cozy_version="${COZY_VERSION:-$DEFAULT_COZY_VERSION}"
cozy_project_dir="${COZY_PROJECT_DIR:-}"
sbt_cozy_version="${SBT_COZY_VERSION:-$DEFAULT_SBT_COZY_VERSION}"
cncf_command="${CNCF_COMMAND:-}"
cozy_command="${COZY_COMMAND:-}"

usage() {
  cat <<'EOF'
Usage:
  scripts/with-launchers.sh [options] -- <command> [args...]

Options:
  --cncf-version <version>          Use this CNCF runtime version and export CNCF_VERSION.
  --cncf-runtime <version>          Use this cncf launcher runtime version only.
  --cncf-runtime-dev-dir <dir>      Use a local CNCF checkout as the cncf runtime.
  --cncf-command <path>             Installed cncf launcher path.
  --cozy-version <version>          Export COZY_VERSION for the cozy launcher/delegates.
  --cozy-project-dir <dir>          Use a local Cozy checkout through COZY_PROJECT_DIR.
  --cozy-command <path>             Installed cozy launcher path.
  --sbt-cozy-version <version>      Export SBT_COZY_VERSION for sample sbt plugin resolution.

Example:
  scripts/with-launchers.sh \
    --cncf-version 0.4.10-SNAPSHOT \
    --cncf-runtime-dev-dir /Users/asami/src/dev2025/cloud-native-component-framework \
    --cozy-version 0.2.20-SNAPSHOT \
    --cozy-project-dir /Users/asami/src/dev2025/cozy \
    --sbt-cozy-version 0.1.6 \
    -- bash samples/03-component-cml/run.sh
EOF
}

canonical_path() {
  local path="$1"
  local dir
  dir="$(cd "$(dirname "$path")" && pwd -P)"
  printf '%s/%s\n' "$dir" "$(basename "$path")"
}

find_command_excluding_repo_bin() {
  local name="$1"
  local self="$REPO_ROOT/bin/$name"
  local candidate=""
  candidate="$(command -v "$name" || true)"
  if [[ -z "$candidate" ]]; then
    return 1
  fi
  if [[ -f "$self" && "$(canonical_path "$candidate")" == "$(canonical_path "$self")" ]]; then
    return 1
  fi
  printf '%s\n' "$candidate"
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --cncf-version)
      cncf_version="${2:-}"
      cncf_runtime="$cncf_version"
      shift 2
      ;;
    --cncf-version=*)
      cncf_version="${1#--cncf-version=}"
      cncf_runtime="$cncf_version"
      shift
      ;;
    --cncf-runtime)
      cncf_runtime="${2:-}"
      shift 2
      ;;
    --cncf-runtime=*)
      cncf_runtime="${1#--cncf-runtime=}"
      shift
      ;;
    --cncf-runtime-dev-dir)
      cncf_runtime_dev_dir="${2:-}"
      shift 2
      ;;
    --cncf-runtime-dev-dir=*)
      cncf_runtime_dev_dir="${1#--cncf-runtime-dev-dir=}"
      shift
      ;;
    --cncf-command)
      cncf_command="${2:-}"
      shift 2
      ;;
    --cncf-command=*)
      cncf_command="${1#--cncf-command=}"
      shift
      ;;
    --cozy-version)
      cozy_version="${2:-}"
      shift 2
      ;;
    --cozy-version=*)
      cozy_version="${1#--cozy-version=}"
      shift
      ;;
    --cozy-project-dir)
      cozy_project_dir="${2:-}"
      shift 2
      ;;
    --cozy-project-dir=*)
      cozy_project_dir="${1#--cozy-project-dir=}"
      shift
      ;;
    --cozy-command)
      cozy_command="${2:-}"
      shift 2
      ;;
    --cozy-command=*)
      cozy_command="${1#--cozy-command=}"
      shift
      ;;
    --sbt-cozy-version)
      sbt_cozy_version="${2:-}"
      shift 2
      ;;
    --sbt-cozy-version=*)
      sbt_cozy_version="${1#--sbt-cozy-version=}"
      shift
      ;;
    -h|--help|help)
      usage
      exit 0
      ;;
    --)
      shift
      break
      ;;
    *)
      usage >&2
      exit 1
      ;;
  esac
done

if [[ $# -eq 0 ]]; then
  usage >&2
  exit 1
fi

if [[ -z "$cncf_command" ]]; then
  cncf_command="$(find_command_excluding_repo_bin cncf || true)"
fi
if [[ -z "$cozy_command" ]]; then
  cozy_command="$(find_command_excluding_repo_bin cozy || true)"
fi
if [[ -z "$cncf_command" ]]; then
  echo "installed cncf launcher not found; set CNCF_COMMAND or --cncf-command" >&2
  exit 3
fi
if [[ -z "$cozy_command" ]]; then
  echo "installed cozy launcher not found; set COZY_COMMAND or --cozy-command" >&2
  exit 3
fi

work_dir="$(mktemp -d "${TMPDIR:-/tmp}/cncf-samples-launchers.XXXXXX")"
cleanup() {
  rm -rf "$work_dir"
}
trap cleanup EXIT

cat > "$work_dir/cncf" <<EOF
#!/usr/bin/env bash
set -euo pipefail
args=()
if [[ -n "${cncf_runtime_dev_dir}" ]]; then
  args+=(--runtime-dev-dir "${cncf_runtime_dev_dir}")
elif [[ -n "${cncf_runtime}" ]]; then
  args+=(--runtime "${cncf_runtime}")
fi
exec "${cncf_command}" "\${args[@]}" "\$@"
EOF

cat > "$work_dir/cozy" <<EOF
#!/usr/bin/env bash
set -euo pipefail
export COZY_VERSION="${cozy_version}"
if [[ -n "${cozy_project_dir}" ]]; then
  export COZY_PROJECT_DIR="${cozy_project_dir}"
fi
exec "${cozy_command}" "\$@"
EOF
chmod +x "$work_dir/cncf" "$work_dir/cozy"

export CNCF_VERSION="$cncf_version"
export COZY_VERSION="$cozy_version"
export SBT_COZY_VERSION="$sbt_cozy_version"
if [[ -n "$cozy_project_dir" ]]; then
  export COZY_PROJECT_DIR="$cozy_project_dir"
fi
export PATH="$work_dir:$PATH"

exec "$@"
