#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

FACTORY_CLASS=org.sample.operationcommand.OperationCommandContractSampleFactory

mkdir -p target/cncf.d

cncf dev command --project-dev . --component-factory-class "$FACTORY_CLASS" help operation-command-contract-sample.greeting.submit-greeting
cncf dev command --project-dev . --component-factory-class "$FACTORY_CLASS" operation-command-contract-sample.meta.describe --format yaml

cncf dev server --project-dev . --component-factory-class "$FACTORY_CLASS" > target/cncf.d/server.log 2>&1 &
server_pid=$!
trap 'kill "$server_pid" >/dev/null 2>&1 || true' EXIT INT TERM

sleep 2

job_id=$(cncf dev client --project-dev . --component-factory-class "$FACTORY_CLASS" operation-command-contract-sample.greeting.submit-greeting --name Alice)
printf '%s\n' "$job_id"
cncf dev client --project-dev . --component-factory-class "$FACTORY_CLASS" job-control.job.await-job-result --id "$job_id"
