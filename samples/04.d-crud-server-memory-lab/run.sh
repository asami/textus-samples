#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help crud
bash ../../bin/cncf --discover=classes command help crud.entity
bash ../../bin/cncf --discover=classes command help crud.entity.create-item
bash ../../bin/cncf --discover=classes command help job-control.job.await-job-result
bash ../../bin/cncf --discover=classes command crud.meta.describe --format yaml
bash run-demo.sh
