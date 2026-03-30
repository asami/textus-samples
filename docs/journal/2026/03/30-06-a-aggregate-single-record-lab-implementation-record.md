# 06.a-aggregate-single-record-lab Implementation Record

date=2026-03-30
status=done

## Result

- `Order` stores `lines: Vector[OrderLine]` in one record
- `OrderLine` is handled as an embedded value object
- `Order.toRecord()` encodes `lines` as embedded records, not strings
- `Order.createC(record)` restores the aggregate from that one record

## Verified

- `sbt --batch clean compile`
- `bash run.sh`
- `bash run-datastore.sh`
- `sbt --batch "testOnly cozy.modeler.EmbeddedValueObjectGenerationSpec"`

The demo output confirmed:

- `record.lines = [{name, quantity}, ...]`
- `restored.lines = [{name, quantity}, ...]`
- `line-count = 2`

The datastore demo confirmed:

- `EntityStoreSpace.create -> load` preserves embedded `OrderLine` records
- loaded `Order` still has `line-count = 2`
- saved and loaded records have the same embedded `lines` shape

## Framework Work Completed

- Cozy/Kaleidox now preserve raw attribute type names so value object references are not collapsed to `string`
- simple-modeler resolves value object collection attributes such as `Vector[OrderLine]`
- generated value objects now provide `ValueReader`
- generated entity/value serialization now uses `Recordable` so nested value objects are encoded as records
- generated record loading for object collections decodes `Seq[Record]` back into value objects
- generated `.take` wrappers were updated to `.TAKE`, so the sample runs without the previous deprecation warning flood
- sample follow-up demo now verifies datastore roundtrip as well as in-memory record roundtrip
