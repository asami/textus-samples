# textus-samples

This repository incrementally builds a catalog of executable CNCF sample patterns.
Each sample demonstrates a structural pattern rather than a business domain and is expected to remain independently buildable and runnable.

## Overview

This repository is a foundation for arranging CNCF structural patterns as small samples that are easy to compare.
Its initial goal is to build a catalog of patterns, not to recreate complex business domains.

## Local CNCF Command

This repository uses a sample-local `cncf` command.

Prepare it once:

```bash
bash bin/setup
```

This repository-level setup prepares both:

- the local `cozy` command used for sample generation
- the sample-local `bin/cncf` wrapper, which delegates to the coursier-installed `cncf` command

Use explicit version overrides when needed:

```bash
bash bin/setup \
  --cncf-version 0.4.2-SNAPSHOT \
  --cncf-server-port 19532 \
  --core-version 0.3.2-SNAPSHOT \
  --simplemodeling-model-version 0.1.2-SNAPSHOT
```

Repository defaults live in:

- `versions/cozy-version.conf`
- `versions/cncf-version.conf`
- `versions/cncf-server-port.conf`
- `versions/goldenport-core-version.conf`
- `versions/simplemodeling-model-version.conf`

Install the CNCF launcher once through Coursier before running samples:

```bash
cs install cncf
```

Then each sample can use the compatibility wrapper:

```bash
bash ../../bin/cncf ...
```

or explicitly choose the runtime version:

```bash
bash ../../bin/cncf --cncf-version 0.4.2-SNAPSHOT ...
```

For CNCF core development, point the wrapper at a local CNCF runtime checkout:

```bash
bash ../../bin/cncf --runtime-dev-dir /path/to/cloud-native-component-framework --discover=classes ...
```

`--discover=classes` is retained as a legacy programming-time compatibility input in `bin/cncf`; it uses the wrapper-prepared project classpath and suppresses automatic `component-dev-dir` activation. Prefer explicit repository/component sources for packaged-artifact samples.

Development order follows the stages recorded in `docs/journal/2026/03/cncf-samples-project.md`.

1. `01-minimal`
2. `01.a-invocation-source-lab`
3. `01.b-startup-shapes-lab`
4. `01.c-builtin-and-help-lab`
5. `01.d-component-script`
6. `02-component`
7. `02.a-car-dir-lab`
8. `02.b-discover-classes-lab`
9. `03-component-cml`
10. `03.a-car-dir-cml-lab`
11. `03.b-discover-classes-cml-lab`
12. `03.c-method-execution-cml-lab`
13. `04-crud`
14. `04.a-crud-seed-import-lab`
15. `04.b-simpleentity-crud-lab`
16. `04.c-crud-sqlite-lab`
17. `04.d-crud-server-memory-lab`
18. `04.e-crud-explicit-sync-lab`
19. `04.f-crud-nested-value-lab`
20. `05-operation`
21. `05.a-operation-command-lab`
22. `05.b-operation-entity-lab`
23. `06-cqrs`
24. `06.a-designed-sync-command-lab`
25. `06.b-test-sync-command-lab`
26. `07-event-driven`
27. `07.a-event-job-trace-lab`
28. `07.b-event-job-server-client-lab`
29. `08-job`
30. `08.a-job-control-lab`
31. `08.b-job-control-demo-lab`
32. `09-aggregate`
33. `09.a-aggregate-single-record-lab`
34. `09.b-aggregate-relation-boundary-model`
35. `09.c-aggregate-external-update-semantics`
36. `10-view`
37. `10.a-view-definition-lab`
38. `10.b-simpleentity-view-lab`
39. `10.c-view-cache-lab`
40. `11-subsystem`
41. `11.a-multi-component-subsystem-lab`
42. `11.b-subsystem-bundled-component-lab`
43. `11.c-subsystem-mixed-component-lab`
44. `11.d-implicit-subsystem-lab`
45. `11.e-sar-dir-lab`
46. `11.f-subsystem-parameter-lab`
47. `12-subsystem-wiring`
48. `101-distributed`

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
│  ├─ 02-component/
│  ├─ 02.a-car-dir-lab/
│  ├─ 02.b-discover-classes-lab/
│  ├─ 03-component-cml/
│  ├─ 03.a-car-dir-cml-lab/
│  ├─ 03.b-discover-classes-cml-lab/
│  ├─ 03.c-method-execution-cml-lab/
│  ├─ 04-crud/
│  ├─ 04.a-crud-seed-import-lab/
│  ├─ 04.b-simpleentity-crud-lab/
│  ├─ 04.c-crud-sqlite-lab/
│  ├─ 04.d-crud-server-memory-lab/
│  ├─ 04.e-crud-explicit-sync-lab/
│  ├─ 04.f-crud-nested-value-lab/
│  ├─ 05-operation/
│  ├─ 05.a-operation-command-lab/
│  ├─ 05.b-operation-entity-lab/
│  ├─ 06-cqrs/
│  ├─ 06.a-designed-sync-command-lab/
│  ├─ 06.b-test-sync-command-lab/
│  ├─ 07-event-driven/
│  ├─ 07.a-event-job-trace-lab/
│  ├─ 07.b-event-job-server-client-lab/
│  ├─ 08-job/
│  ├─ 08.a-job-control-lab/
│  ├─ 08.b-job-control-demo-lab/
│  ├─ 09-aggregate/
│  ├─ 09.a-aggregate-single-record-lab/
│  ├─ 09.b-aggregate-relation-boundary-model/
│  ├─ 09.c-aggregate-external-update-semantics/
│  ├─ 10-view/
│  ├─ 10.a-view-definition-lab/
│  ├─ 10.b-simpleentity-view-lab/
│  ├─ 10.c-view-cache-lab/
│  ├─ 11-subsystem/
│  ├─ 11.a-multi-component-subsystem-lab/
│  ├─ 11.b-subsystem-bundled-component-lab/
│  ├─ 11.c-subsystem-mixed-component-lab/
│  ├─ 11.d-implicit-subsystem-lab/
│  ├─ 11.e-sar-dir-lab/
│  ├─ 11.f-subsystem-parameter-lab/
│  ├─ 12-subsystem-wiring/
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

From `07-event-driven` onward, `docker/` may be added when needed.
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
- Local deployment: use `invoke.sh` with local packaged-source assumptions when needed
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

For development, the preferred working style is:

- `--component-dev-dir <project>`
  - runs a component development directory without building a CAR first
  - reads runtime classpath data from `target/cncf.d/runtime-classpath.txt`
  - reads CAR-root resources from `src/main/car`
- `--subsystem-dev-dir <project>`
  - runs an application/subsystem development root without building a SAR first
  - reads the subsystem descriptor from the root or `subsystem/`
  - reads component development output from `component/`
- `cwd/component.d`
  - active local packaged source
- `cwd/repository.d`
  - local packaged search repository
- `car.d` / `sar.d`
  - expanded archive layouts for loader debugging and inspection
  - use `--component-car-dir` or `--subsystem-sar-dir` explicitly when running them

Development roots infer their component or subsystem identity from descriptors,
so the usual edit/run form does not need `--textus.component` or
`--textus.subsystem`:

```bash
cncf --component-dev-dir . server
cncf --subsystem-dev-dir . server
```

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

## Cozy Generation

Sample generation is fixed to the following shape for now:

- sample-local `project/plugins.sbt`
  - reads the `sbt-cozy` version from [sbt-cozy-version.conf](versions/sbt-cozy-version.conf)
- sample-local `build.sbt`
  - uses `cozyDelegateCommand`
  - calls [bin/cozy](bin/cozy)
- [bin/setup](bin/setup)
  - prepares the cozy launcher for the configured version
  - verifies dependency resolution before samples use it
- [bin/cozy](bin/cozy)
  - reads the target `cozy` version from [cozy-version.conf](versions/cozy-version.conf)
  - uses the prepared launcher created by [bin/setup](bin/setup)
  - executes `cozy.Cozy` through `sbt runMain`

Current intent:

- keep sample-side dependency versions fixed in one place
- avoid hard-wiring sample builds to a specific development path
- keep development turnaround fast while `cozy` is still used from a local workspace

Current operating rule:

1. run `bin/setup cozy`
2. run sample build or sample script
3. `bin/cozy` uses the prepared launcher

Resolver policy:

- release version
  - resolve from the normal published repositories
- `-SNAPSHOT` version
  - resolve from Ivy local first
  - this assumes the matching `cozy` version has already been `publishLocal`ed

This is a development-stage operating mode.
The intended later path is:

1. local command wrapper
2. local published artifact or equivalent packaged runtime
3. Docker-based `cozy` command

The purpose of the current stage is to preserve development efficiency without coupling each sample directly to one handwritten `cozyDelegateProjectDir` setting.

## Using sbt-cozy

`textus-samples` consumes `sbt-cozy` as a normal published sbt plugin.

Each sample-local `project/plugins.sbt` reads the version from [sbt-cozy-version.conf](versions/sbt-cozy-version.conf) and adds:

- `Resolver.defaultLocal`
- `addSbtPlugin("org.goldenport" % "sbt-cozy" % sbtCozyVersion)`

In practice, sample users only need two things:

1. the desired `sbt-cozy` version in [sbt-cozy-version.conf](versions/sbt-cozy-version.conf)
2. a repository path that can resolve that version

Operationally this means:

- released `sbt-cozy` versions are resolved from the normal Maven/sbt plugin repositories
- during `sbt-cozy` development, `Resolver.defaultLocal` allows an Ivy-local `publishLocal` build to override that resolution

This keeps `sbt-cozy` version management centralized while keeping sample usage the same as any other sbt plugin dependency.

User-facing invocation guidance lives under:

- `guide/invocation/component-and-subsystem-invocation-guide.md`
- `guide/script/component-script-examples.md`

Deployment guidance in this repository assumes:

- default final deployment from a remote Component Repository
- optional deployment from a local repository or shared component directory
- sample-level deployment simulation from `samples/component.d` or `samples/repository.d`

If a sample is not implemented yet, its `run.sh` exits with a clear message until the sample-specific command path is defined.

## Site Publication

`textus-samples` is distributed as source code. Users unpack the source archive
and run the samples themselves from the sample directories.

Deployment to `simplemodeling.org` is handled by `cozy` or `sbt-cozy`, not by a
sample-local deployment script. Public publication metadata is committed in
`project.yaml`:

```yaml
project:
  name: textus-tutorial
  title: Textus Tutorial
  kind: sample-multi
  path: textus/tutorial/textus-tutorial
```

Local output and warehouse paths are read from `.cozy/config.yaml`, which is
intentionally ignored by git:

```yaml
publication:
  output: /path/to/simplemodeling-org/publish.d
  samples_dir: samples
  source_manifest:
    excludes:
      - target
      - .git
      - .bsp
      - .metals
      - .idea
      - repository.d

distribution:
  repository: /path/to/distribution-warehouse
  require_release_version: true

warehouse:
  repository: /path/to/distribution-warehouse
  repository_artifacts:
    include:
      - car
      - sar
    modules:
      - textus-tutorial
```

The warehouse repository is intended for machine-readable publication artifacts
under `https://simplemodeling.org/repository/download/textus/tutorial/textus-tutorial/...`.
The publication output is the site source area consumed by the
`simplemodeling.org` build.

The root project enables `sbt-cozy` for repository-level publication tasks.
Normal operation is:

```bash
sbt cozyPlanDistributeSamples
sbt cozyPublishProject
sbt cozyDistributeSamples
sbt cozyIndexWarehouse
```

`cozyPlanDistributeSamples` is the dry-run path. It prints the planned
collection and per-sample ZIP archive paths without writing to the warehouse.

`cozyPublishProject` generates the SmartDox publication source metadata,
including the expected download paths for the sample archives.

`cozyDistributeSamples` writes user-facing sample ZIP archives into the
configured warehouse under:

```text
repository/download/<publication.path>/<version>/<publication>-<version>.zip
repository/download/<publication.path>/<sample>/<version>/<sample>-<version>.zip
```

The collection archive contains all child sample directories. Each per-sample
archive contains one child sample project.

`cozyIndexWarehouse` indexes the warehouse and generates the download/release
metadata consumed by the public site. It also checks that the expected download
paths written by `cozyPublishProject` exist in the warehouse. Maven repository
metadata is handled by Cozy's separate `publish-maven-repository` operation, not
by the sample publication flow.

SmartDox consumes the generated publication sources and warehouse index for the
public site. Source archive generation is owned by the `cozy`/`sbt-cozy`
publication pipeline, not by sample-local shell scripts. Release distribution
rejects `SNAPSHOT` versions; use `cozyPlanDistributeSamples` when checking
planned paths during development.

## Current Status

At the current stage, the repository foundation is ahead of the actual sample implementations.
The first implementation target is `samples/01-minimal`.
