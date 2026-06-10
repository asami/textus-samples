#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
cd "$REPO_ROOT"

timeout_seconds="${CNCF_SAMPLE_TIMEOUT_SECONDS:-300}"
log_root="${CNCF_SAMPLE_VALIDATION_LOG_DIR:-target/all-sample-validation-$(date +%Y%m%d-%H%M%S)}"
summary_file="$log_root/summary.tsv"

mkdir -p "$log_root"
: > "$summary_file"

timeout_cmd=""
if command -v timeout >/dev/null 2>&1; then
  timeout_cmd="timeout"
elif command -v gtimeout >/dev/null 2>&1; then
  timeout_cmd="gtimeout"
fi

run_sample() {
  local run="$1"
  local sample
  local name
  local logfile
  local start
  local end
  local code

  sample="$(dirname "$run")"
  name="${sample#samples/}"
  logfile="$log_root/${name//\//__}.log"

  printf 'RUN\t%s\n' "$name" | tee -a "$summary_file"
  start="$(date +%s)"
  if [[ -n "$timeout_cmd" ]]; then
    "$timeout_cmd" "$timeout_seconds" bash "$run" >"$logfile" 2>&1
  else
    bash "$run" >"$logfile" 2>&1
  fi
  code="$?"
  if [[ "$code" == "0" ]] && grep -Eq '"error"[[:space:]]*:|Enum\(Error\):error|type:[[:space:]]*error' "$logfile"; then
    code=90
  fi
  end="$(date +%s)"

  if [[ "$code" == "0" ]]; then
    printf 'PASS\t%s\t%ss\t%s\n' "$name" "$((end - start))" "$logfile" | tee -a "$summary_file"
    return 0
  else
    printf 'FAIL\t%s\tcode=%s\t%ss\t%s\n' "$name" "$code" "$((end - start))" "$logfile" | tee -a "$summary_file"
    return 1
  fi
}

failed=0
while IFS= read -r run; do
  if ! run_sample "$run"; then
    failed=1
  fi
done < <(find samples -mindepth 2 -name run.sh | sort)

printf 'LOGDIR\t%s\n' "$log_root" | tee -a "$summary_file"
exit "$failed"
