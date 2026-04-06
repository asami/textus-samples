#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help test-sync.item.create-item
bash ../../bin/cncf --discover=classes command test-sync.meta.describe --format yaml
bash run-default.sh
bash run-sync.sh
