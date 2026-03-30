#!/bin/bash
set -eu
cd "$(dirname "$0")"
sbt --batch "runMain org.sample.crudnestedvalue.NestedValueDemo"
