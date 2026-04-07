#!/bin/sh

set -eu

FACTORY_CLASS=org.sample.operationcommand.OperationCommandContractSampleFactory

mkdir -p target/cncf.d

bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" command help operation-command-contract-sample.greeting.submit-greeting
bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" command operation-command-contract-sample.meta.describe --format yaml

bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" server > target/cncf.d/server.log 2>&1 &
server_pid=$!
trap 'kill "$server_pid" >/dev/null 2>&1 || true' EXIT INT TERM

sleep 2

job_id=$(bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" client operation-command-contract-sample.greeting.submit-greeting --name Alice)
printf '%s\n' "$job_id"
bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" client job-control.job.await-job-result --id "$job_id"
