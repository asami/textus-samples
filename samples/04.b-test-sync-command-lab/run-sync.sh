#!/usr/bin/env bash
set -euo pipefail

exec sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes --textus.runtime.command.execution-mode sync-job-async-interface command TestSync.Item.createItem --name beta --title Beta"
