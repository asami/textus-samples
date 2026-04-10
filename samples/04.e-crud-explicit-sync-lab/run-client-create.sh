#!/bin/sh

set -eu

exec bash ../../bin/cncf --discover=classes client \
  --textus.command.execution-mode sync-direct-no-job \
  crud.entity.create-item \
  --name alpha \
  --title Alpha
