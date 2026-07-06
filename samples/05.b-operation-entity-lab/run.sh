#!/bin/sh

set -eu

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

FACTORY_CLASS=org.sample.operationentity.OperationEntitySampleFactory
PERSON_ID=major-minor-entity-person-1742198400000-abcd1234

cncf command --component-factory-class "$FACTORY_CLASS" help operation-entity-sample.person-app.get-person-card
cncf command --component-factory-class "$FACTORY_CLASS" operation-entity-sample.meta.describe --format yaml
cncf command --component-factory-class "$FACTORY_CLASS" operation-entity-sample.person-app.get-person-card --personId "$PERSON_ID"
