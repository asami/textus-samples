#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help simple-entity-crud-lab
bash ../../bin/cncf --discover=classes command help simple-entity-crud-lab.item
bash ../../bin/cncf --discover=classes command help simple-entity-crud-lab.item.create-item
bash ../../bin/cncf --discover=classes command simple-entity-crud-lab.meta.describe --format yaml
