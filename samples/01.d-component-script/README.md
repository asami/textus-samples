# 01.d-component-script

## Overview

This sample is centered on one `scala-cli` script file.
It shows how a small management program can stay in a single script while still running on CNCF runtime behavior.

The concrete script example is:

- `script/main.scala`

This sample is intentionally script-first.
It is not presented as an sbt-based multi-file Component project.

## What It Demonstrates

- one file script execution
- `scala-cli`-based startup
- CNCF script DSL
- management-program style usage

## Why This Form Is Useful

The script stays close to the operational use case.
That makes it useful for small management commands and tooling where a full formal Component project would be too heavy.

The important rule is:

- the script is the wrapper
- the CNCF runtime behavior is still the contract
- for larger or more structured behavior, formal Component definition should be preferred

## How To Run

Run the commands from this directory.

Development-time invocation:

```bash
./run.sh
```

Script invocation:

```bash
./script/main.scala
```

The script uses:

- `#!/usr/bin/env -S scala-cli shebang`
- `import org.goldenport.cncf.dsl.script.*`
- `run(args) { ... }`

## Relation To CNCF

The script DSL still generates and runs through CNCF runtime behavior.
That means the sample is not a generic shell example.
It is a CNCF-backed management program in script form.

## Relation To Other Labs

- `01-minimal` shows the minimal Component contract
- `01.a` focuses on invocation source
- `01.b` focuses on startup shapes
- `01.c` focuses on builtin/help surfaces
- `01.d` focuses on single-file script form

## Result

The script should print:

- `Hello CNCF`
