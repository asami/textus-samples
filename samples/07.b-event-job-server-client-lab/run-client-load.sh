#!/bin/sh

set -eu

exec bash ../../bin/cncf --discover=classes client event-driven.event.load-effect
