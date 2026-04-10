# Component Script Examples

## Purpose

This note explains how CNCF Components can be used to build small administrative scripts.

The point is not to introduce a separate scripting runtime.
The point is to show that a Component command surface can already act as a scriptable management interface.

Typical use cases:

- health checks
- maintenance commands
- metadata inspection
- one-shot administration tasks

## Core Idea

If a Component exposes a command path such as:

```text
component.service.operation
```

then a shell script can call that command path through the CNCF runtime.

This makes it easy to build simple management commands without inventing another integration layer first.

## Smallest Pattern

The basic pattern is:

1. prepare a Component with a stable command path
2. invoke it through `org.goldenport.cncf.CncfMain`
3. wrap that invocation in a shell script if needed

Conceptually:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain command admin.system.ping"
```

or after deployment:

```bash
cncf command admin.system.ping
```

The exact deployed launcher may differ, but the command path remains the important stable contract.

## Why This Matters

This approach is useful because:

- administrative behavior stays inside the Component model
- commands are discoverable through the same CNCF structure
- operational scripts remain small
- management commands and application commands use the same selector model

In practice, this means many "small scripts" can simply be thin wrappers around Component operations.

## Example Shapes

### 1. Ping Script

```bash
#!/usr/bin/env bash
set -euo pipefail

sbt --batch "runMain org.goldenport.cncf.CncfMain command minimal.system.ping"
```

Purpose:

- confirm the runtime is alive
- expose a simple operator-facing check

### 2. Help Script

```bash
#!/usr/bin/env bash
set -euo pipefail

sbt --batch "runMain org.goldenport.cncf.CncfMain command minimal.meta.help"
```

Purpose:

- show available operations
- make a Component easier to inspect from the shell

### 3. Maintenance Script

```bash
#!/usr/bin/env bash
set -euo pipefail

sbt --batch "runMain org.goldenport.cncf.CncfMain command admin.cache.clear"
```

Purpose:

- wrap an operational task in a stable command path
- keep the script itself small and declarative

## Development-Time Pattern

During programming, a script may still run through `sbt` and class discovery.

Typical shape:

```bash
sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command <component>.<service>.<operation>"
```

This is useful while the Component is still being built.

## Deployment-Time Pattern

After deployment, the preferred model is:

- load Components from a Component Repository
- keep the script as a thin invocation wrapper
- avoid embedding business logic into the shell script itself

For sample-level simulation in this repository:

- `invoke.sh` should use:
  - `samples/component.d` for active packaged sources
  - `samples/repository.d` for searchable packaged sources

For final deployment:

- prefer a remote Component Repository
- allow a local repository or shared component directory when needed

## Design Rule

When a script is needed, prefer this order:

1. implement the management behavior as a Component operation
2. expose a stable selector
3. add a thin shell wrapper only if that improves usability

Avoid the opposite order:

- do not put real operational logic into the shell first and only later try to map it into Components

## Relationship To Samples

This guide is related to:

- `01-minimal`
- `01.a-minimal-lab`

because those samples establish the basic command-path model that later script-like management use cases can reuse.

## Summary

Small management scripts are a natural extension of the CNCF command model.

The shell script should stay thin.
The real capability should live in the Component.
