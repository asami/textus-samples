#!/bin/sh

set -eu

ITEM_ID=org-sample-entity-item-$(date +%s)000-gamma111

bash ../../bin/cncf --discover=classes command help cqrs.item.create-item
bash ../../bin/cncf --discover=classes command help cqrs.entity.create-item-record
bash ../../bin/cncf --discover=classes command cqrs.meta.describe --format yaml

sbt --batch "runMain org.sample.cqrs.CqrsSampleRunner $ITEM_ID"
