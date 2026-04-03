#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

exec bash "$SCRIPT_DIR/../../scripts/sample-runner.sh" \
  --script-path "$SCRIPT_DIR/run-datastore.sh" \
  --sample-main-class org.sample.aggregatesinglerecord.SingleRecordAggregateDatastoreDemo
