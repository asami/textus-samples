#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command TestSync.Item.createItem --name beta --title Beta
bash ../../bin/cncf --discover=classes command TestSync.Item.createItem --name beta --title Beta --textus.output.shape envelope --textus.output.format yaml
