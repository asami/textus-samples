#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help crud
bash ../../bin/cncf --discover=classes command help crud.item
bash ../../bin/cncf --discover=classes command help crud.item.create-item
bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml
