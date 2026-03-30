#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

dbpath="target/cncf.d/02f-crud-nested-value-lab.sqlite"
mkdir -p target/cncf.d

exec sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=$dbpath --textus.runtime.command.execution-mode sync-direct-no-job crud-nested-value-sample.entity.create-person --name alice --address.street Marunouchi-1-2-3 --address.city Tokyo --address.country.value JP"
