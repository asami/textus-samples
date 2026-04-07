package org.sample.aggregateexternalupdate

type ShipmentOrder = entity.aggregate.ShipmentOrder
object ShipmentOrder:
  export entity.aggregate.ShipmentOrder.*

type User = entity.aggregate.User
object User:
  export entity.aggregate.User.*
