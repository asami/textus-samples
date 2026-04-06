#!/bin/sh

set -eu

FACTORY_CLASS=org.sample.operationentity.OperationEntitySampleFactory
PERSON_ID=major-minor-entity-person-1742198400000-abcd1234

bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" command help operation-entity-sample.person-app.get-person-card
bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" command operation-entity-sample.meta.describe --format yaml
bash ../../bin/cncf --component-factory-class "$FACTORY_CLASS" command operation-entity-sample.person-app.get-person-card --person-id "$PERSON_ID"
