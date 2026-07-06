#!/usr/bin/env bash
set -euo pipefail

cat >&2 <<'MSG'
scripts/sample-runner.sh is deprecated.

Samples should call the installed CNCF launcher directly with cncf, for example:

  cncf command <component.service.operation>

Use each sample's run.sh as the canonical executable documentation.
MSG
exit 2
