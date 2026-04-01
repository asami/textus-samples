#!/bin/bash
set -eu
cd "$(dirname "$0")"
sbt --batch clean compile
sbt --batch "runMain org.sample.aggregaterelationboundary.RelationBoundaryAggregateDemo"
