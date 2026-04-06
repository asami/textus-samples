#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help designed-sync.item.create-item
bash ../../bin/cncf --discover=classes command designed-sync.meta.describe --format yaml
bash ../../bin/cncf --discover=classes command designed-sync.item.create-item --name beta --title Beta
