# 10-Phase-12-Wiring-Direction Note

## Purpose

Record the current phase 12 wiring discussion before the runtime wiring model is fully implemented.

This note is a working journal memo.
It captures:

- the agreed wiring direction
- what the current sample already demonstrates
- what remains an implementation gap

## Agreed Direction

### 1. Wiring Is Defined At Subsystem Level

- subsystem descriptor may specify wiring explicitly
- unspecified wiring may be completed by convention

The intended model is therefore:

- explicit subsystem wiring first
- convention-based completion second

### 2. Wiring Should Use Ports

Wiring should not be treated as an opaque direct component-to-component jump.

The intended expression is:

- component ports
- `api`
- `spi`

The wiring model should therefore evolve toward:

- `caller.api.x -> callee.spi.y`

rather than a raw informal link.

### 3. Port Model Is The Binding Unit

The port itself should be treated as the primary binding unit.

The intended role split is:

- `api`
  - a required port seen from the calling side
  - expresses what kind of service the caller expects
- `spi`
  - a provided port seen from the callee side
  - expresses what kind of service the callee offers
- wiring
  - binds one `api` port to one `spi` port

The intended consequence is:

- caller components do not name concrete callee implementations as the primary model
- runtime wiring resolves a required port to a provided port
- component-to-component routing is a resolved assembly result, not the most abstract source form

### 4. Runtime Mediation Is Preferred Over DI-Style Reference Injection

The discussion clarified that phase 12 wiring should not be modeled primarily as DI-style field or constructor injection.

The preferred direction is:

- runtime mediation
- descriptor-driven routing
- subsystem-level interception

This means:

- the caller issues a mediated operation request
- the subsystem runtime resolves and dispatches that request
- interception points remain available for:
  - authorization
  - logging
  - metrics
  - tracing
  - retry and timeout policy
  - future governance and policy checks

### 5. Assembly Result Must Be Observable

The result of assembly must be observable from the runtime.

This includes:

- selected components
- resolved ports
- resolved port bindings
- selected winners when duplicate component names exist
- warnings
- resolved wiring
- convention-completed wiring

The intended retrieval surface is:

- `admin.assembly.*`

and later:

- dashboard / admin console

### 6. Descriptor And Assembly Result Are Distinct

The discussion clarified a useful distinction:

- subsystem descriptor
  - intent
  - explicit wiring declarations
  - explicit port declarations where needed
- assembly result / assembly descriptor
  - resolved runtime view
  - resolved port bindings
  - winner selection after duplicate handling
  - warning information
  - convention-completed wiring

That assembly result may later be:

- written back into a descriptor-like form
- exported as an assembly descriptor

## Current Sample State

`12-subsystem-wiring` currently demonstrates the first practical delegated-call line:

- one subsystem
- two generic component artifacts
- `callercomp`
- `calleecomp`
- one delegated call from `callercomp.main.hello` to `calleecomp.main.hello`

The sample currently uses subsystem descriptor `wiring` metadata as input for the delegated call.

## Current Runtime State

### Already Implemented

- generic subsystem startup includes builtin components by default
- duplicate component selection is recorded as assembly warnings
- `admin.assembly.warnings` exists
- `admin.assembly.report` has been added
- phase 12 sample can retrieve the assembly report from the admin surface
- `admin.assembly.report` now returns:
  - `ports`
  - raw `wiring`
  - resolved `wiring_bindings`
  - loaded components
  - warnings
- resolved wiring now supports:
  - `api`
  - `spi`
  - `glue`
  metadata in the reported binding result
- caller result can return:
  - delegated result
  - `ports`
  - `wiring`
  - `wiring_bindings`
  - `calltree` when `--calltree` is enabled

### Current Remaining Gap

The runtime now exposes the resolved assembly result, and the phase 12 sample now applies minimal `glue` execution semantics.

That means:

- `glue` is preserved and observable
- `callercomp` applies `request/mode: passthrough`
- `callercomp` applies `response/mode: passthrough`
- the result includes `glue_applied`

This proves the basic execution shape, but it is still sample-local.
The framework does not yet provide a general runtime mediation layer for applying `glue`.

## Temporary Sample Technique

The current `12-subsystem-wiring` sample therefore uses a temporary technique:

- `callercomp` reads wiring metadata directly from the subsystem descriptor path when needed
- `callercomp` applies the current passthrough `glue` modes itself
- this allows the delegated call walkthrough to succeed before framework-level wiring interpretation is implemented

This is a temporary bridge, not the intended final runtime design.

## Next Runtime Task

The next runtime task is:

- move `glue` execution from sample-local caller logic into framework-level runtime mediation

After that, the next larger step is:

- formalize convention-based completion of unspecified wiring
- allow convention-based completion of unspecified wiring
- evolve from direct delegated sample logic toward more general runtime mediation

## Expected Future Shape

The intended future phase 12 runtime shape is:

1. subsystem descriptor provides explicit wiring
2. subsystem descriptor and component metadata provide `api` / `spi` ports
3. runtime completes missing parts by convention
4. runtime resolves port-level `api` / `spi` bindings
5. runtime records the resolved assembly result
6. `admin.assembly.*` exposes that result for CLI, admin, and dashboard use
7. optional `glue` participates in request / response adaptation, not only observability

## Suggested Descriptor Shape

The exact schema is still open, but the current discussion points toward a shape such as:

```yaml
subsystem: testsubsystemwiring
components:
  - component: callercomp
    api:
      hello-target:
        service: main
        operation: hello
  - component: calleecomp
    spi:
      hello-provider:
        service: main
        operation: hello
wiring:
  - from:
      component: callercomp
      api: hello-target
    to:
      component: calleecomp
      spi: hello-provider
```

This is not the final format.
It is recorded here to make the intended abstraction level explicit:

- ports first
- concrete component routing second
- runtime-resolved assembly result as the observable outcome

## Future Extension: Glue In Wiring

The discussion also identified a likely future extension:

- wiring may need an explicit `glue` section

This is not part of the current minimum phase 12 line.
It is a likely development path after direct port binding is established.

The purpose of `glue` would be:

- adapt request shape between `api` and `spi`
- adapt response shape between `spi` and `api`
- fill structural or naming gaps between caller-side and callee-side contracts
- carry mediation-oriented policy where needed

Typical future uses may include:

- field mapping
- default value injection
- request / response transformation
- protocol or contract bridging
- policy-oriented adaptation around the delegated call

The intended conceptual stack would then be:

- `api`
  - caller-side required contract
- `spi`
  - callee-side provided contract
- `glue`
  - an adapter layer that reconciles the two

## ExtensionPoint Position

The discussion also revisited whether `ExtensionPoint` should be exposed through ports.

The current judgment is:

- under a DI-style model, exposing `ExtensionPoint` through a visible port would feel natural
- under the current runtime mediation / interpreter model, that need becomes much weaker

The current preferred direction is therefore:

- do not make `ExtensionPoint` part of the phase 12 main port model
- keep `ExtensionPoint` primarily as a runtime capability or assembly mechanism unless a stronger use case appears later

This keeps the phase 12 main line focused on:

- business interaction ports
- runtime mediation
- observable resolved assembly

rather than mixing those concerns with lower-level runtime extension hooks.
