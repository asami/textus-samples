#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.create-person
bash ../../bin/cncf --discover=classes command help crud-nested-value-sample.entity.load-person
bash ../../bin/cncf --discover=classes command crud-nested-value-sample.meta.describe --format yaml
bash run-datastore.sh
