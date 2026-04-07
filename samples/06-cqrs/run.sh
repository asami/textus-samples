#!/bin/sh

set -eu

ITEM_ID=org-sample-entity-item-$(date +%Y%m%d%H%M%S)-gamma111
STARTED_SERVER=0

bash ../../bin/cncf --discover=classes command help cqrs.item.create-item
bash ../../bin/cncf --discover=classes command help cqrs.entity.create-item-record
bash ../../bin/cncf --discover=classes command cqrs.meta.describe --format yaml

if ps -ax | grep -F "org.goldenport.cncf.CncfMain --discover=classes server" | grep -F "/samples/06-cqrs/" >/dev/null 2>&1; then
  :
else
  bash ../../bin/cncf --discover=classes server > target/cncf-server.log 2>&1 &
  server_pid=$!
  STARTED_SERVER=1
  trap 'if [ "$STARTED_SERVER" -eq 1 ]; then kill "$server_pid" >/dev/null 2>&1 || true; fi' EXIT INT TERM
  sleep 2
fi


job_id=$(bash ../../bin/cncf --discover=classes client cqrs.entity.create-item-record --id "$ITEM_ID" --name gamma --title Gamma)
printf '%s\n' "$job_id"
bash ../../bin/cncf --discover=classes client job-control.job.await-job-result --id "$job_id"
bash ../../bin/cncf --discover=classes client cqrs.entity.load-item --id "$ITEM_ID"
