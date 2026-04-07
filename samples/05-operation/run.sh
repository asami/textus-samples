#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help operation-contract-sample.greeting.greeting
bash ../../bin/cncf --discover=classes command operation-contract-sample.meta.describe --format yaml
