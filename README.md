# textus-samples

This repository incrementally builds a catalog of executable CNCF sample patterns.
Each sample demonstrates a structural pattern rather than a business domain and is expected to remain independently buildable and runnable.

## Overview

This repository is a foundation for arranging CNCF structural patterns as small samples that are easy to compare.
Its initial goal is to build a catalog of patterns, not to recreate complex business domains.

## Local Launchers

This repository uses the installed stable launchers directly. `cncf` is the
primary command for running samples, while `cozy` is used by CML/model
generation and publication tasks. `textus` is documented here because it is the
user/application launcher that shares the same published CNCF runtime line.

Install or update the launchers once through Coursier before running samples:

```bash
cs install --force cozy \
  --channel https://www.simplemodeling.org/repository/cozy/coursier-channel.json

cs install --force cncf \
  --channel https://www.simplemodeling.org/repository/textus/coursier-channel.json

cs install --force textus \
  --channel https://www.simplemodeling.org/repository/textus/coursier-channel.json
```

Refresh runtime catalogs explicitly when a newly published stable runtime should
become `recommended`:

```bash
cozy runtime refresh
cncf runtime refresh
textus runtime refresh

cozy launcher version
cozy runtime current
cncf launcher version
cncf runtime current
textus launcher version
textus runtime current
```

Repository setup still prepares the local `cozy` command and verifies that
`cncf` is available. Use `all` when you also want to check `textus`:

```bash
bash bin/setup
bash bin/setup all
```

Repository defaults live in:

- `versions/cozy-version.conf`
- `versions/cncf-version.conf`
- `versions/cncf-server-port.conf`
- `versions/goldenport-core-version.conf`
- `versions/simplemodeling-model-version.conf`

Run samples from each sample directory with `cncf dev` directly.

Launcher roles:

- `textus` is the user/application launcher for running packaged Textus applications.
- `cncf` is the CNCF development launcher for running components, subsystems,
  and runtime surfaces while building CNCF samples.

Because these samples execute components that are under development, the
standard command is `cncf dev ...`, not `textus`. Use `--project-dev .` auto activation
for the ordinary edit/run loop, and use CAR/SAR/repository options only when
the sample is specifically demonstrating packaged source loading.


Published runtime example:

```bash
cncf dev command --project-dev . minimal.main.hello
```

CNCF core development runtime example:

```bash
cncf --runtime-dev-dir /path/to/cloud-native-component-framework \
  dev command --project-dev . minimal.main.hello
```

Packaged source examples:

```bash
cncf dev command --project-dev . --no-project-classpath --component-car-dir car.d testcomp.main.hello
cncf dev command --project-dev . --no-project-classpath --subsystem-sar-dir sar.d testcomp.main.hello
cncf dev command --project-dev . --no-project-classpath --repository-dir repository.d --textus.component=<component> <operation>
```

`--discover=classes` and the sample-local `bin/cncf` wrapper are historical
compatibility mechanisms. Current samples should use `--project-dev .` for the main development project.
Use `--component-dev-dir <dir>` only when a sample intentionally injects a
separate development component dependency; use `--component-car-dir`,
`--subsystem-sar-dir`, or `repository.d` only for packaged-source samples.

Development order follows the stages recorded in `docs/journal/2026/03/cncf-samples-project.md`.

1. `01-minimal`
2. `01.a-invocation-source-lab`
3. `01.b-startup-shapes-lab`
4. `01.c-builtin-and-help-lab`
5. `01.d-component-script`
6. `02-component`
7. `02.a-car-dir-lab`
8. `02.b-discover-classes-lab` (historical name; now uses `cncf dev --project-dev .`)
9. `03-component-cml`
10. `03.a-car-dir-cml-lab`
11. `03.b-discover-classes-cml-lab` (historical name; now uses `cncf dev --project-dev .`)
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
48. `101-distributed` (planned; excluded from normal execution verification until implemented)

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
│  ├─ 02.b-discover-classes-lab/        # historical name; uses cncf dev --project-dev .
│  ├─ 03-component-cml/
│  ├─ 03.a-car-dir-cml-lab/
│  ├─ 03.b-discover-classes-cml-lab/    # historical name; uses cncf dev --project-dev .
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
- Learning walkthrough: type the explicit `cncf ...` / `sbt ...` commands shown
  in each sample README
- Local verification shortcut: use `run.sh` or `run-sample.sh` after the
  command sequence is understood
- Local deployment: use `invoke.sh` with local packaged-source assumptions when needed
- Final deployment: prefer remote Component Repository loading

Use the root dispatcher to verify a sample quickly:

```bash
./run-sample.sh 01-minimal
```

Each sample also owns its local runner. Treat it as a shortcut, not as the
primary teaching path:

```bash
cd samples/01-minimal
./run.sh
```

For development, the preferred working style is:

- `--project-dev <project>`
  - standard launcher entry for ordinary samples and the main development project
  - auto-activates the component/subsystem being edited
- `--component-dev-dir <project>`
  - injects a separate component development directory as a dependency override
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
cncf dev server --project-dev .
cncf dev server --project-dev . --subsystem-dev-dir .
```

Programming-time example:

```bash
cd samples/01-minimal
cncf dev command --project-dev . minimal.main.hello
```

This is the preferred form for learning because it exposes the launcher mode,
the development project argument, and the selector being executed. The sample
README then explains each parameter and the expected output fields.

Deployment-style invocation can also be separated from local verification through `invoke.sh` per sample.

Historical shell utilities under `scripts/` are retained only as deprecated
migration guards. New and maintained samples should call `cncf dev` directly
from their own `run.sh` / `invoke.sh`.

## Cozy Generation

Sample generation is fixed to the following shape for now:

- sample-local `project/plugins.sbt`
  - reads the `sbt-cozy` version from [sbt-cozy-version.conf](versions/sbt-cozy-version.conf)
- sample-local `build.sbt`
  - uses `cozyDelegateCommand`
  - calls [bin/cozy](bin/cozy)
- [bin/setup](bin/setup)
  - verifies the installed `cozy` launcher and reports the configured versions
- [bin/cozy](bin/cozy)
  - reads the target `cozy` version from [cozy-version.conf](versions/cozy-version.conf) or `COZY_VERSION`
  - delegates to the installed `cozy` launcher from `PATH` or `COZY_COMMAND`
  - passes through `COZY_PROJECT_DIR` for source-checkout Cozy runtime testing
- [with-launchers.sh](scripts/with-launchers.sh)
  - runs any validation command with one-shot CNCF/Cozy launcher version overrides

Current intent:

- keep sample-side dependency versions fixed in one place
- avoid hard-wiring sample builds to a specific development path
- keep development turnaround fast while `cozy` is still used from a local workspace

Current operating rule:

1. run `bin/setup all` to verify installed `cncf` and `cozy` launchers
2. run sample build or sample script
3. `bin/cozy` delegates to the installed `cozy` launcher

Run every sample script with the batch runner only for smoke verification:

```bash
bash scripts/run-all-samples.sh
```

The runner executes each `samples/**/run.sh` directly, writes per-sample logs
under `target/all-sample-validation-*`, and records a `summary.tsv` with
`PASS` / `FAIL` rows. Set `CNCF_SAMPLE_TIMEOUT_SECONDS` to adjust the per-sample
timeout when validating slower environments.

Version-matrix validation can be run without editing version files:

```bash
scripts/with-launchers.sh \
  --cncf-version 0.4.10 \
  --cozy-version 0.2.21 \
  --sbt-cozy-version 0.1.6 \
  -- bash samples/03-component-cml/run.sh
```

For published runtime validation, use `--cncf-version <version>` and
`--cozy-version <version>` instead of the source checkout options.

Resolver policy:

- release version
  - resolve from the normal published repositories
- `-SNAPSHOT` version
  - resolve from Ivy local first
  - this assumes the matching `cozy` version has already been `publishLocal`ed


Launcher maturity path:

1. installed `cozy` command for normal sample generation
2. local published artifact or equivalent packaged runtime for `-SNAPSHOT` testing
3. Docker-based `cozy` command when containerized generation becomes necessary

The purpose of the current stage is to preserve development efficiency while keeping each sample independent from one handwritten `cozyDelegateProjectDir` setting.

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

If a sample is not implemented yet, it does not provide `run.sh` and is excluded from normal execution verification until the sample-specific command path is defined.

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
