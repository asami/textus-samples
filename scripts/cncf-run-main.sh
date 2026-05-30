#!/usr/bin/env bash
set -euo pipefail

cat >&2 <<'MSG'
scripts/cncf-run-main.sh is deprecated.

Samples should call the installed CNCF launcher directly with cncf dev.
MSG
exit 2
