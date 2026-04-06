#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help crud.entity.load-item
bash ../../bin/cncf --discover=classes command help crud.entity.search-item-record
bash ../../bin/cncf --discover=classes command crud.entity.load-item --id major-minor-entity-item-20260327000000-aaa111
bash ../../bin/cncf --discover=classes command crud.entity.search-item-record --name alpha
bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml
