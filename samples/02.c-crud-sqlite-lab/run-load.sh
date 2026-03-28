#!/usr/bin/env bash
set -euo pipefail

dir="$(cd "$(dirname "$0")" && pwd)"
cd "$dir"

dbpath="target/cncf.d/02c-crud-sqlite-lab.sqlite"
mkdir -p target/cncf.d

exec sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command --cncf.datastore.sqlite.path=$dbpath crud.entity.load-item --id alpha"
