#!/usr/bin/env bash
set -eu

sbt --batch clean compile "runMain org.sample.aggregatesinglerecord.SingleRecordAggregateDatastoreDemo"
