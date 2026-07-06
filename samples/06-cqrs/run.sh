#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

ITEM_ID=major-minor-entity-item-$(date +%s)000-gamma111

cncf command help cqrs.item.create-item
cncf command help cqrs.entity.create-item-record
cncf command cqrs.meta.describe --format yaml

sbt --batch "runMain org.sample.cqrs.CqrsSampleRunner $ITEM_ID"
