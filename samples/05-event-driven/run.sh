#!/bin/sh

set -eu

bash ../../bin/cncf --discover=classes command help event-driven
bash ../../bin/cncf --discover=classes command help event-driven.event.emit-event
bash ../../bin/cncf --discover=classes command help event-driven.event.load-effect
bash ../../bin/cncf --discover=classes command event-driven.meta.describe --format yaml
