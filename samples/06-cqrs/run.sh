#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

ITEM_ID=org-sample-entity-item-$(date +%s)000-gamma111

cncf dev command --project-dev . help cqrs.item.create-item
cncf dev command --project-dev . help cqrs.entity.create-item-record
cncf dev command --project-dev . cqrs.meta.describe --format yaml

sbt --batch "runMain org.sample.cqrs.CqrsSampleRunner $ITEM_ID"
