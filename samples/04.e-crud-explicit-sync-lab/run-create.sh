#!/bin/sh

set -eu

exec bash ../../bin/cncf --discover=classes command \
  --textus.command.execution-mode sync-direct-no-job \
  crud.entity.create-item \
  --name alpha \
  --title Alpha
