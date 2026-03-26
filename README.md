# cncf-samples

This repository incrementally builds a catalog of executable CNCF sample patterns.
Each sample demonstrates a structural pattern rather than a business domain and is expected to remain independently buildable and runnable.

## Overview

This repository is a foundation for arranging CNCF structural patterns as small samples that are easy to compare.
Its initial goal is to build a catalog of patterns, not to recreate complex business domains.

開発順序は `docs/journal/2026/03/cncf-samples-project.md` に記録された以下の段階に従います。

1. `01-minimal`
2. `01.a-invocation-source-lab`
3. `01.b-startup-shapes-lab`
4. `01.c-builtin-and-help-lab`
5. `01.d-component-script`
6. `02-crud`
7. `03-cqrs`
8. `04-event-driven`
9. `05-job`
10. `06-subsystem`
11. `07-subsystem-wiring`
12. `101-distributed`

## AI Directive

This project adopts `ai/directive` as the shared AI contract.
`AGENT.md` and `RULE.md` are exposed as symbolic links to `ai/directive/core`.

AI behavior is interpreted in the following order.

1. `ai/directive/core`
2. Active profile
   - `ai/directive/chatgpt-desktop`
   - `ai/directive/codex`
3. Project-specific development documents
   - `docs/rules`
   - `docs/spec`
   - `docs/design`
   - `docs/notes`
   - `docs/journal`

## Repository Layout

```text
.
├─ AGENT.md
├─ RULE.md
├─ ai/
│  └─ directive/
├─ docs/
│  ├─ architecture/
│  ├─ design/
│  ├─ journal/
│  ├─ notes/
│  ├─ patterns/
│  ├─ rules/
│  └─ spec/
├─ guide/
│  └─ invocation/
├─ samples/
│  ├─ 01-minimal/
│  ├─ 01.a-invocation-source-lab/
│  ├─ 01.b-startup-shapes-lab/
│  ├─ 01.c-builtin-and-help-lab/
│  ├─ 01.d-component-script/
│  ├─ 02-crud/
│  ├─ 03-cqrs/
│  ├─ 04-event-driven/
│  ├─ 05-job/
│  ├─ 06-subsystem/
│  ├─ 07-subsystem-wiring/
│  └─ 101-distributed/
└─ shared/
   ├─ common-lib/
   └─ test-utils/
```

## Sample Standard

Each sample follows this baseline layout.

```text
sample-name/
├─ AGENT.md
├─ RULE.md
├─ README.md
├─ build.sbt
├─ component.d/
└─ src/main/scala/
```

From `04-event-driven` onward, `docker/` may be added when needed.
Each sample-level `AGENT.md` and `RULE.md` is a symlink back to the repository-level directive so that the same AI contract remains visible even when a sample is opened on its own.

## How To Work

- `docs/` is for sample development documentation
- `guide/` is for user-facing documentation
- Each sample must not depend on other samples
- The root `build.sbt` only carries repository-level metadata
- Implementation and execution are handled per sample directory
- Shared code may live under `shared/` only when sample independence is preserved

For sample-status documents, use this split:

- `docs/phase/samples/<sample>.md`
  - the only progress and completion authority
- `docs/journal/.../*completion-instruction.md`
  - the active work-order document when one exists
- `docs/journal/.../*implementation-record.md`
  - implementation history only, not status authority

Operational rule:

- never rewrite a `*completion-instruction.md` file into a completion note or result report
- record results by appending to an implementation record or by creating a separate completion record file
- never treat an implementation record as the authority over a phase checklist

Directive policy:

- use `ai/directive` as the default operating rule set
- do not add project-local AI rules unless there is a concrete repository-specific reason
- prefer documenting sample facts, work orders, and status over creating local AI behavior rules

## Running Samples

Stage-oriented execution model:

- Programming time: run from the sample directory through `sbt`
- Local verification: use `run.sh` or `run-sample.sh`
- Local deployment: use `invoke.sh` with local repository assumptions when needed
- Final deployment: prefer remote Component Repository loading

Use the root dispatcher to run a sample:

```bash
./run-sample.sh 01-minimal
```

Each sample also owns its local runner:

```bash
cd samples/01-minimal
./run.sh
```

For development, the preferred working style is to run from the sample directory so that `cwd/component.d` is the active local component source.

Programming-time example:

```bash
cd samples/01-minimal
sbt --batch "runMain org.goldenport.cncf.CncfMain command minimal.main.hello"
```

Deployment-style invocation can also be separated from local verification through `invoke.sh` per sample.

Shared shell utilities live under `scripts/`:

- `scripts/cncf-run-main.sh`
  - runs `org.goldenport.cncf.CncfMain` by default with sample-local `sbt runMain`
  - can fall back to a sample-local main class only when needed
- `scripts/sample-runner.sh`
  - resolves the sample directory from the calling script and delegates to `cncf-run-main.sh`

Preferred pattern:

- Use the CNCF library main first
- Introduce a sample-local main only when the CNCF main is insufficient

User-facing invocation guidance lives under:

- `guide/invocation/component-and-subsystem-invocation-guide.md`
- `guide/script/component-script-examples.md`

Deployment guidance in this repository assumes:

- default final deployment from a remote Component Repository
- optional deployment from a local repository or shared component directory
- sample-level deployment simulation from `samples/component-repository.d`

If a sample is not implemented yet, its `run.sh` exits with a clear message until the sample-specific command path is defined.

## Current Status

At the current stage, the repository foundation is ahead of the actual sample implementations.
The first implementation target is `samples/01-minimal`.
