# 06-aggregate Implementation Plan

## Purpose

Define the first executable plan for `06-aggregate` based on:

- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/aggregate-behavior-execution-model-design-note.md`
- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/design/aggregate-view-semantic-boundary.md`
- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/notes/entity-runtime-architecture.md`
- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/notes/partitioned-entity-realm.md`
- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/journal/2026/03/aggregate-view-design-handoff.md`

This plan fixes the first implementation line for `06-aggregate`.

## Canonical Understanding

For `06-aggregate`, use the following interpretation.

- `Entity`
  - persisted source-of-truth graph
- `Aggregate`
  - write-boundary model built from entity state
- `Behavior`
  - shared execution base that provides protected DSL
- `AggregateBehavior`
  - aggregate-oriented execution logic built on `Behavior`
- `ActionCall`
  - invocation and orchestration owner
- `AggregateSpace`
  - aggregate execution environment
  - not a second persistence truth

This means:

- do not treat Aggregate as an alternative persistence source
- do not fold Behavior back into raw entity mutation
- do not define Aggregate itself as the protected DSL holder
- do not add a separate Processor abstraction in the first line

## Aggregate Pattern Choice

`06-aggregate` adopts the application-join aggregate pattern.

Meaning:

- an aggregate is constructed from multiple entities at application/runtime level
- aggregate load/search demonstrates runtime aggregate construction

This sample does **not** use the single-record encoded-object pattern as its main line.

That encoded pattern is still valid for real applications.
For example, `Cart` is likely to be implemented that way in a real product.
But it is not the best first sample for explaining CNCF aggregate runtime structure.

## First Sample Concept

Use a small `Order` aggregate.

### Aggregate Root

- `Order`

### Member

- `OrderLine`

### First Behavior

- `addLine`

### First Invariant

- `quantity > 0`

### Read Routes

- aggregate load
- aggregate search

This concept is small enough to demonstrate:

- root/member boundary
- application-level join aggregate construction
- state transition
- invariant
- aggregate-shaped load/search

without requiring a broad domain story.

## First Completion Line

`06-aggregate` is complete at the first line when:

1. one `Order` aggregate model exists
2. one aggregate load route works
3. one aggregate search route works
4. one `addLine`-style behavior executes through AggregateBehavior
5. one invariant failure or state transition effect is observable

## Runtime Contract To Freeze First

### 1. Aggregate object

Aggregate is the write-boundary model built from entity state.
It is not defined as the protected DSL holder itself.

Expected direction:

```scala
final class OrderAggregate(...) {
  ...
}
```

### 2. Behavior / AggregateBehavior

Behavior is the first explicit execution abstraction.
It provides the protected DSL used by execution logic.

Expected direction:

```scala
trait Behavior[A] {
  protected def load_root(id: EntityId): Consequence[A]
  protected def save_root(target: A): Consequence[Unit]
  protected def emit_event(event: EventRecord): Consequence[Unit]
  protected def ensure_condition(condition: Boolean, message: => String): Consequence[Unit]
}
```

```scala
trait AggregateBehavior[A] extends Behavior[A] {
  def run(target: A, ctx: ExecutionContext): Consequence[OperationResponse]
}
```

Application logic is expected to be implemented by a class that extends `AggregateBehavior`.

Protected-method naming in this line follows the existing codebase convention:

- protected API uses `snake_case`
- runtime public methods may keep the current public naming convention

Examples:

- `load_root`
- `save_root`
- `emit_event`
- `ensure_condition`

### 3. ActionCall relation

Current `ActionCall` already exposes protected execution DSL in CNCF.

For the first line, the intended direction is:

- common protected execution DSL is lifted into `Behavior`
- `ActionCall` keeps its invocation and orchestration role
- `AggregateBehavior` reuses the same protected DSL
- `ActionCall.execute` may resolve an `AggregateBehavior`, invoke it, and continue its own logic

This means the first line should move toward:

```scala
abstract class ActionCall(...) extends Behavior[?]
```

without redefining `ActionCall` itself as domain behavior.

### 4. Behavior resolution scenario

The first line adopts delegated aggregate behavior.

Meaning:

- `ActionCall` is not required to be `AggregateBehavior` itself
- `ActionCall.execute` resolves `AggregateBehavior`
- the main domain logic is executed by the delegated behavior
- `ActionCall` may perform additional orchestration before or after delegated behavior execution

This keeps the first line aligned with application-provided behavior via Factory.

The self-behavior scenario is deferred unless a concrete need appears.

### 5. AggregateSpace role

`AggregateSpace` is treated as:

- collection/repository registry
- aggregate execution environment

It may later grow toward:

- WorkingSet
- WorkSpace

but the first line must not redesign it as a second persistence model.

### 6. Locking / concurrency assumption

The first line assumes one aggregate root id is one execution boundary.

The partition-aware concurrency design in:

- `/Users/asami/src/dev2025/cloud-native-component-framework/docs/notes/partitioned-entity-realm.md`

remains the future alignment.

For the first line:

- same aggregate root must execute serially
- no broad concurrency redesign is part of `06-aggregate`

## Step Plan

### Step 1. Read path first

Implement and verify:

- aggregate load
- aggregate search

using the current aggregate collection/runtime shape.

Goal:

- prove the aggregate projection path exists before behavior write execution is added

### Step 2. Aggregate runtime object shape

Introduce the smallest aggregate object for `Order`.

Goal:

- make aggregate boundary and behavior boundary visible separately

### Step 3. Single behavior write path

Introduce one write behavior:

- `addLine`

Goal:

- the write route must go through AggregateBehavior, not direct entity mutation in sample code
- the execution logic must use `Behavior` protected DSL with protected-method naming convention
- the first implementation must use delegated `AggregateBehavior`

### Step 4. Invariant proof

Add one observable invalid case:

- quantity <= 0 fails

Goal:

- show that the aggregate boundary has actual rule enforcement

### Step 5. Documentation and runnable commands

Finalize:

- README
- phase checklist
- implementation record

and make the runnable verification commands explicit.

## What Not To Do In The First Line

- no full view synchronization
- no separate Processor abstraction
- no aggregate snapshot feature
- no broad event-driven projection closure
- no new persistence truth besides Entity
- no sample-local handwritten repository layer
- no single-record encoded aggregate as the main explanatory pattern

## Expected Outcome

After the first line, `06-aggregate` should already prove:

- aggregate-oriented access is distinct from plain CRUD
- aggregate write logic has its own execution abstraction
- aggregate boundary is not just naming, but an executable runtime boundary
