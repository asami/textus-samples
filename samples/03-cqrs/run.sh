#!/usr/bin/env bash
set -euo pipefail

exec sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Cqrs"
