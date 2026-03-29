#!/usr/bin/env bash
set -eu

sbt --batch "runMain org.sample.aggregate.OrderAggregateDemo"
