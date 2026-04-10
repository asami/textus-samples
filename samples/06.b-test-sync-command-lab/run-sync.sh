#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command --textus.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta
bash ../../bin/cncf --discover=classes command --textus.command.execution-mode sync-job-async-interface TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml
