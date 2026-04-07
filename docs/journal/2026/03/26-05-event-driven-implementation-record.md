# 05-event-driven Implementation Record

## Status

Completed.

## Summary

`05-event-driven` is now a pure model-driven sample.

It proves:

- one command emits an event
- one CNCF event-reception path receives it
- one visible post-event effect can be loaded afterwards

## Sample Files

- `samples/07-event-driven/src/main/cozy/event.cml`
- `samples/07-event-driven/src/main/scala/org/sample/eventdriven/EventFlowDemo.scala`
- `samples/07-event-driven/run.sh`
- `samples/07-event-driven/run-demo.sh`
- `samples/07-event-driven/README.md`

## Runtime Shape

- component: `event-driven`
- service: `event`
- emitting command: `event-driven.event.emit-event`
- receiver action: `event-driven.event.record-effect`
- observation query: `event-driven.event.load-effect`
- emitted event: `item.changed`

## Confirmed Checks

- `sbt --batch cozyGenerate`
- `sbt --batch clean compile`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event.emit-event"`
- `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help event-driven.event.load-effect"`
- `sbt --batch "runMain org.sample.eventdriven.EventFlowDemo"`

## Observed End-To-End Flow

`EventFlowDemo` runs the flow inside one JVM:

1. build the generated `EventDriven` component
2. execute `emitEvent`
3. wait for the command job to complete
4. let CNCF event reception dispatch `recordEffect`
5. execute `loadEffect`

Observed effect payload:

```json
{
  "cncf.event.kind": "changed",
  "cncf.event.name": "item.changed",
  "name": "alpha",
  "source": "event-driven",
  "title": "Alpha"
}
```

## Notes

- The earlier handwritten provider path was removed.
- `--discover=classes` works for the generated help surface.
- The event sample currently uses a same-JVM demo runner for the visible effect check, so the first sample does not depend on a long-running server/client setup.
