# CNCF Samples Launcher Startup Handoff

Date: 2026-06-01
Status: handoff note

## Purpose

This note records the current startup and validation model for `cncf-samples`.
It is intended as a handoff for continuing the migration from historical sample
wrappers to direct launcher-based execution.

This is not a normative CNCF launcher specification. The normal user-facing
entry points remain the repository README files and each sample's `run.sh` /
`invoke.sh` scripts.

## Current Direction

`cncf-samples` should demonstrate the current CNCF development workflow.

The standard runtime entry point is the installed `cncf` launcher:

```bash
cncf dev ...
```

The `textus` launcher is not the normal entry point for these samples. `textus`
is the user/application launcher for packaged Textus applications. `cncf` is the
CNCF development launcher for running components, subsystems, and runtime
surfaces while building samples.

The sample-local `bin/cncf` wrapper is deprecated. It should not be used by new
or maintained samples. It now exits with a migration message instead of trying
to preserve the old Coursier invocation behavior.

## Default Startup Shape

Run samples from the sample directory or through the sample's own script.

Typical direct command:

```bash
cd samples/01-minimal
cncf dev command --project . minimal.main.hello
```

Typical sample-owned runner:

```bash
cd samples/01-minimal
./run.sh
```

The preferred source-development shape is:

```bash
cncf dev command --project . <operation-selector>
```

`--project .` is the standard launcher entry for ordinary samples. It activates
the component/subsystem being edited from the project metadata. New samples
should not add `--component-dev-dir .` for the main project.

## Source Runtime Validation

To run a sample against a local CNCF core checkout, put the runtime selector
before `dev`:

```bash
cncf --runtime-dev-dir /Users/asami/src/dev2025/cloud-native-component-framework \
  dev command --project . minimal.main.hello
```

This validates the sample against the development CNCF runtime rather than the
published runtime selected by the installed launcher.

## Packaged Source Loading

Use packaged-source options only when the sample is specifically demonstrating
that loading mode.

Component CAR directory:

```bash
cncf dev command --project . \
  --no-project-classpath \
  --component-car-dir car.d \
  testcomp.main.hello
```

Subsystem SAR directory:

```bash
cncf dev command --project . \
  --no-project-classpath \
  --subsystem-sar-dir sar.d \
  testcomp.main.hello
```

Repository directory:

```bash
cncf dev command --project . \
  --no-project-classpath \
  --repository-dir repository.d \
  --textus.component=<component> \
  <operation>
```

`cwd/component.d`, `cwd/repository.d`, `car.d`, and `sar.d` are still useful for
packaged-loader samples and loader debugging. They are not the default edit/run
workflow.

## Historical Discovery Mode

`--discover=classes` is historical. It should not be used as the standard sample
pattern.

The historical discover-class labs may keep their names for continuity, but
maintained scripts should use current launcher forms such as:

```bash
cncf dev command --project . <operation>
```

Use `--component-dev-dir <dir>` only when the sample intentionally injects a
separate development component dependency into the current project. It is not a
replacement for `--project .` on the main sample project.

## Cozy Generation Path

Cozy is still used for sample generation, but sample execution should use the
`cncf` launcher.

Generation path:

- sample-local `project/plugins.sbt` reads the `sbt-cozy` version from
  `versions/sbt-cozy-version.conf` unless `SBT_COZY_VERSION` is set;
- sample-local `build.sbt` uses `cozyDelegateCommand`;
- `cozyDelegateCommand` calls repository-local `bin/cozy`;
- `bin/cozy` delegates to the installed `cozy` launcher from `PATH` or
  `COZY_COMMAND`;
- `COZY_VERSION` selects the Cozy runtime version;
- `COZY_PROJECT_DIR` allows source-checkout Cozy runtime testing.

Repository setup verifies launcher availability:

```bash
bin/setup all
```

Useful focused checks:

```bash
bin/setup cozy
bin/setup cncf
```

## Version Files

Repository defaults are stored in `versions/`:

```text
versions/cncf-version.conf
versions/cozy-version.conf
versions/sbt-cozy-version.conf
versions/cncf-server-port.conf
versions/goldenport-core-version.conf
versions/simplemodeling-model-version.conf
```

Current values at the time of this handoff:

```text
cncf-version = 0.4.7
cozy-version = 0.2.20-SNAPSHOT
sbt-cozy-version = 0.1.6
cncf-server-port = 19532
```

These files define repository defaults. Temporary version-matrix validation
should use `scripts/with-launchers.sh` rather than editing version files.

## Version Matrix Validation

Use `scripts/with-launchers.sh` to test a combination of CNCF and Cozy launcher
versions/runtimes without changing repository defaults.

Example using local CNCF and Cozy checkouts:

```bash
scripts/with-launchers.sh \
  --cncf-version 0.4.10-SNAPSHOT \
  --cncf-runtime-dev-dir /Users/asami/src/dev2025/cloud-native-component-framework \
  --cozy-version 0.2.20-SNAPSHOT \
  --cozy-project-dir /Users/asami/src/dev2025/cozy \
  --sbt-cozy-version 0.1.6 \
  -- bash samples/03-component-cml/run.sh
```

Example using published launcher runtimes:

```bash
scripts/with-launchers.sh \
  --cncf-version <cncf-version> \
  --cozy-version <cozy-version> \
  --sbt-cozy-version <sbt-cozy-version> \
  -- bash samples/03-component-cml/run.sh
```

The helper creates temporary `cncf` and `cozy` commands at the front of `PATH`.
This lets existing scripts call plain `cncf` / `cozy` while the validation run
uses the requested versions.

## Current Validation Baseline

The current launcher startup path has been validated with:

```bash
bash -n bin/cozy bin/setup scripts/with-launchers.sh
```

```bash
scripts/with-launchers.sh \
  --cncf-version 0.4.10-SNAPSHOT \
  --cncf-runtime-dev-dir /Users/asami/src/dev2025/cloud-native-component-framework \
  --cozy-version 0.2.20-SNAPSHOT \
  --cozy-project-dir /Users/asami/src/dev2025/cozy \
  --sbt-cozy-version 0.1.6 \
  -- ./bin/setup all
```

```bash
scripts/with-launchers.sh \
  --cncf-version 0.4.10-SNAPSHOT \
  --cncf-runtime-dev-dir /Users/asami/src/dev2025/cloud-native-component-framework \
  --cozy-version 0.2.20-SNAPSHOT \
  --cozy-project-dir /Users/asami/src/dev2025/cozy \
  --sbt-cozy-version 0.1.6 \
  -- bash samples/03-component-cml/run.sh
```

`git diff --check` also passed after the launcher-startup changes.

This does not mean every sample is currently green. Full sample validation is a
separate activity. Some server/client CRUD samples still need independent
follow-up validation and fixes.

## Migration Rules For Future Samples

New or maintained samples should follow these rules:

- call installed `cncf` directly;
- use `cncf dev ...`, not `textus`, for development samples;
- use `--project .` for the main sample project;
- avoid `../../bin/cncf` and sample-local launcher wrappers;
- avoid `--discover=classes` in new scripts;
- use `--component-dev-dir <dir>` only for separate component dependency
  injection;
- use `--component-car-dir`, `--subsystem-sar-dir`, or `--repository-dir` only
  for packaged-source demonstrations;
- use `scripts/with-launchers.sh` for version/runtimes matrix checks;
- keep Cozy generation behind `sbt-cozy` and `bin/cozy`, not in sample runtime
  scripts.

## Known Follow-Ups

- Continue migrating any remaining scripts that still depend on historical
  wrapper assumptions.
- Complete full all-sample validation with CNCF development runtime selected
  through the `cncf` launcher.
- Keep server/client samples explicit about port ownership and cleanup.
- Decide whether historical discover-class labs should be renamed or retained
  as historical labels.

## Related Files

- `README.md`
- `bin/cncf`
- `bin/cozy`
- `bin/setup`
- `scripts/with-launchers.sh`
- `versions/*.conf`
