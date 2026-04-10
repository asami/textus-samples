# 12-subsystem-wiring

## Overview

This sample is the first phase 12 subsystem wiring line.
It extends `11.a-multi-component-subsystem-lab` by adding an explicit component-to-component call inside one subsystem.

The wiring shown here is intentionally minimal:

- one subsystem
- two generic component artifacts
- one caller component
- one callee component
- explicit delegated call from caller to callee

## Intended Use Case

Use this sample when you want to learn the first explicit subsystem wiring step after phase 11 composition:

- multiple components coexist in one subsystem
- one component calls another component through the subsystem surface
- the delegated relationship is described in subsystem wiring metadata

## Current Status

This sample is runnable.

The current runtime line is:

- `component.d/callercomp.car`
- `component.d/calleecomp.car`
- `component.d/testsubsystemwiring.sar`
- `--textus.runtime.subsystem=testsubsystemwiring`
- `--textus.runtime.subsystem.file=component.d/testsubsystemwiring.sar`

## What This Sample Shows

This sample shows:

- one subsystem descriptor listing two components
- component-level `api` / `spi` declarations in the subsystem descriptor
- one `wiring` section in the subsystem descriptor
- `callercomp.main.hello` reading the target operation from descriptor wiring metadata
- `callercomp.main.hello` using the framework mediation helper to apply `glue` passthrough modes during delegated execution
- `callercomp.main.hello` delegating to `calleecomp.main.hello`
- `callercomp.main.hello` returning declared `ports` and resolved `wiring_bindings`
- `callercomp.main.hello` returning `wiring` in the YAML result
- `callercomp.main.hello --calltree` returning `calltree` in the YAML result
- `admin.assembly.descriptor` returning a descriptor-oriented wiring document

## Files

- `run.sh`
  - builds both component CARs, the subsystem SAR, and runs the walkthrough
- `src/main/scala/caller/CallerComponent.scala`
  - caller component that delegates through subsystem wiring metadata
- `src/main/scala/callee/CalleeComponent.scala`
  - callee component that returns the final greeting

## Setup

### Prepare repository tools

```bash
bash ../../bin/setup
```

### Build the sample

```bash
sbt --batch compile
```

## Run The Whole Scenario

This command builds both component artifacts and runs the subsystem wiring walkthrough.

```bash
bash run.sh
```

Expected result:

```yaml
message: callercomp delegated to calleecomp.main.hello -> Hello from calleecomp in testsubsystemwiring
delegated_to:
  component: calleecomp
  service: main
  operation: hello
callee_result: Hello from calleecomp in testsubsystemwiring
glue_applied:
  request_mode: passthrough
  response_mode: passthrough
ports:
  - component: callercomp
    api:
      - name: hello-target
        service: main
        operation: hello
    spi: []
  - component: calleecomp
    api: []
    spi:
      - name: hello-provider
        service: main
        operation: hello
wiring_bindings:
  - from:
      component: callercomp
      service: main
      operation: hello
      api: hello-target
    to:
      component: calleecomp
      spi: hello-provider
      service: main
      operation: hello
    glue:
      request/mode: passthrough
      response/mode: passthrough
    mode: api-spi-routing
wiring:
  callercomp:
    main:
      hello:
        api: hello-target
        target_component: calleecomp
        target_spi: hello-provider
        glue:
          request/mode: passthrough
          response/mode: passthrough
calltree: ...
```

## What This Sample Generates

`run.sh` builds the sample classes and then generates:

- `callercomp.car`
- `calleecomp.car`
- `testsubsystemwiring.sar`

These are execution artifacts only.
They are created in a temporary working directory and are not committed inputs.

## Port And Wiring Shape

The subsystem descriptor currently includes `api` / `spi` declarations and a nested `wiring` block:

```yaml
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
  callercomp:
    main:
      hello:
        api: hello-target
        target_component: calleecomp
        target_spi: hello-provider
        glue:
          request/mode: passthrough
          response/mode: passthrough
```

The current runtime still uses delegated routing in the caller implementation.
However, the caller now delegates through the framework `Subsystem.executeWired` helper so descriptor `glue` modes are applied while constructing the delegated call and interpreting the callee response:

- `api`
  - caller-side required port
- `spi`
  - callee-side provided port
- `wiring`
  - the current route that binds the caller-side operation and `api` to the callee-side `spi`
- `glue`
  - optional adapter rule between `api` and `spi`
  - this sample implements `request/mode: passthrough` and `response/mode: passthrough`

`admin.assembly.report` and the caller result both project:

- declared `ports`
- raw `wiring`
- derived `wiring_bindings`

The runtime currently resolves:

- caller operation -> caller `api`
- target component + target `spi`
- callee `spi` -> concrete service / operation
- `glue` passthrough modes are applied by the framework mediation helper during the delegated call
- the resolved `glue` metadata is also carried into the binding result

This keeps the phase 12 walkthrough concrete while moving the sample closer to the intended port-based runtime model.

## Assembly Report

This sample still shows how to retrieve the current assembly result from the admin surface.

```bash
bash ../../bin/cncf command admin.assembly.report --format yaml --component-dir <temporary-component-dir> --textus.runtime.subsystem=testsubsystemwiring --textus.runtime.subsystem.file=<temporary-subsystem-sar>
```

The report currently includes:

- `subsystem`
- `ports`
  - declared `api` / `spi` ports by component
- `wiring`
  - the descriptor-provided wiring block
- `wiring_bindings`
  - the resolved `api -> spi -> operation` binding
  - includes `glue` when the descriptor specifies it
- `components`
  - the loaded component names and origins
- `warnings`
  - assembly warnings captured during loading

The caller result carries `wiring`, and carries `calltree` when the generic meta option `--calltree` is enabled.
If `--calltree` is omitted, the same operation returns the delegated result without the `calltree` field.

This gives the sample a concrete path from:

- subsystem descriptor
- runtime assembly
- admin-observable result

which is the intended foundation for later descriptor export or standalone assembly descriptors.

## Assembly Descriptor

This sample also retrieves the resolved assembly in a descriptor-oriented form.

```bash
bash ../../bin/cncf command admin.assembly.descriptor --format yaml --component-dir <temporary-component-dir> --textus.runtime.subsystem=testsubsystemwiring --textus.runtime.subsystem.file=<temporary-subsystem-sar>
```

The descriptor export currently includes:

- `kind: assembly-descriptor`
- `subsystem`
- `version`
- `components`
- `ports`
- `wiring`
- `wiring_bindings`
- `warnings`

This is the CLI-facing document form of the wiring diagram.
It is separate from the subsystem descriptor, which expresses intent.
