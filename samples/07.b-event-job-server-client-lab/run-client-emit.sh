#!/bin/sh

set -eu

exec bash ../../bin/cncf --discover=classes client event-driven.event.emit-event --name alpha --title Alpha
