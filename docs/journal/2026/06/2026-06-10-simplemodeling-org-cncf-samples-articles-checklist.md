# SimpleModeling.org CNCF Samples Article Series Checklist

Date: 2026-06-10
Status: working checklist

## Purpose

This checklist tracks the work to turn `cncf-samples` into a
SimpleModeling.org SmartDox article series.

The articles will live under:

```text
/Users/asami/src/dev2025/simplemodeling-org/src/main/doxsite/component-based-development
```

The first article introduces Cozy / CNCF / Textus launchers and installation.
The following articles introduce `cncf-samples`, using the downloaded
SimpleModeling.org distribution as the reader-facing source.

Existing CNCF articles in `component-based-development` are kept. The new
series uses `cncf-samples-*.dox` filenames and focuses on hands-on sample
execution.

## Preparation

- [ ] Confirm the public `cncf-samples` archive/download URL.
- [ ] Confirm the published versions of `cozy`, `cncf`, and `textus` launchers.
- [ ] Confirm the repository smoke command for the downloaded sample package:

  ```bash
  bash scripts/run-all-samples.sh
  ```

- [x] Compare existing SimpleModeling.org CNCF articles with the new sample
      series and avoid duplicate narrative.
- [x] Use existing SmartDox style:
  - [x] bilingual title line;
  - [x] `# HEAD`;
  - [x] `status=draft`;
  - [x] `SUMMARY` and `LEAD`;
  - [x] Japanese-first bilingual spans.
- [x] Use `cncf-samples-*.dox` filenames for the new article series.

## Article 0: Launcher Install

Target file:

```text
cncf-samples-launchers-install.dox
```

- [x] Explain the role split:
  - [x] `cozy`: SmartDox / CML generation and related build operations.
  - [x] `cncf`: CNCF component/runtime development launcher.
  - [x] `textus`: Textus application/user launcher.
- [x] Show installation with Coursier channels.
- [x] Show update/reinstall command examples.
- [x] Show version/current checks:

  ```bash
  cozy launcher version
  cozy runtime current
  cncf launcher version
  cncf runtime current
  textus launcher version
  textus runtime current
  ```

- [x] Explain catalog cache behavior and refresh/update guidance if needed.
- [x] Explain how to download and unpack `cncf-samples`.
- [x] Explain the all-sample smoke command and when to use it.
- [x] Link to the first sample article.

## Article Series Map

Use one sample per article as the default. Merge or split only when the article
would otherwise be too small or too large.

| Sample group | Article strategy | Notes |
| --- | --- | --- |
| `01-minimal` | article | Minimal Component / Service / Operation. |
| `01.a` / `01.b` / `01.c` | merge with 01 or one follow-up article | Invocation source, startup shape, builtin/help surfaces. |
| `01.d` | article | Component script / scala-cli path. |
| `02-component` | article | Packaged component baseline. |
| `02.a` / `02.b` | merge with 02 or one follow-up article | CAR dir and development class loading. |
| `03-component-cml` | article | CML-generated component baseline. |
| `03.a` / `03.b` / `03.c` | merge or split by size | CAR dir, development loading, method execution. |
| `04-crud` | article | Generated CRUD surface. |
| `04.a` / `04.b` / `04.c` | follow-up article | Seed import, SimpleEntity, SQLite persistence. |
| `04.d` / `04.e` / `04.f` | follow-up article | Server/client, explicit sync, nested values. |
| `05-operation` | article | Operation contract surface. |
| `05.a` / `05.b` | article or follow-up | Command/job shape and entity-backed operation. |
| `06-cqrs` | article | Command/query split. |
| `06.a` / `06.b` | merge with 06 or follow-up | Sync command contract vs test sync mode. |
| `07-event-driven` | article | Event surface. |
| `07.a` / `07.b` | follow-up article | Job trace and server/client event flow. |
| `08-job` | article | Job observation. |
| `08.a` / `08.b` | follow-up article | Job control and internal proof boundary. |
| `09-aggregate` | article | Aggregate basics. |
| `09.a` / `09.b` / `09.c` | follow-up article | Single-record aggregate and boundary semantics. |
| `10-view` | article | View/read-side surface. |
| `10.a` / `10.b` / `10.c` | follow-up article | Named views, SimpleEntity view, cache-aware search. |
| `11-subsystem` | article | Formal subsystem baseline. |
| `11.a` - `11.f` | one or two follow-up articles | Multi-component, bundled/mixed/implicit, SAR, parameter startup. |
| `12-subsystem-wiring` | article | Explicit subsystem wiring. |
| `13-observability-jaeger` | article | Jaeger trace export. |
| `13.a-observability-stack-lab` | article | Collector / Prometheus / Grafana stack. |
| `101-distributed` | deferred | Mention only as a future distributed sample. |

Draft articles created:

- [x] `cncf-samples-launchers-install.dox`
- [x] `cncf-samples-01-minimal.dox`
- [x] `cncf-samples-01-component-script.dox`
- [x] `cncf-samples-02-component-packaging.dox`
- [x] `cncf-samples-03-cml-component.dox`
- [x] `cncf-samples-04-crud-basics.dox`
- [x] `cncf-samples-04-crud-runtime.dox`
- [x] `cncf-samples-05-operation-contract.dox`
- [x] `cncf-samples-06-cqrs.dox`
- [x] `cncf-samples-07-event-driven.dox`
- [x] `cncf-samples-08-job-management.dox`
- [x] `cncf-samples-09-aggregate.dox`
- [x] `cncf-samples-10-view.dox`
- [x] `cncf-samples-11-subsystem.dox`
- [x] `cncf-samples-12-subsystem-wiring.dox`
- [x] `cncf-samples-13-observability-jaeger.dox`
- [x] `cncf-samples-13-observability-stack.dox`

## Per-Article Workflow

For each article:

- [x] Read the sample `README.md`.
- [x] Read the sample `run.sh` and any helper scripts it calls.
- [x] Inspect the sample model/source files needed to explain the article.
- [ ] Run the sample from the downloaded `cncf-samples` package.
- [ ] Capture the expected command output or concise result shape.
- [x] Draft SmartDox under `component-based-development`.
- [x] Include these sections:
  - [x] what the reader learns;
  - [x] sample directory;
  - [x] command to run;
  - [x] expected result;
  - [x] CNCF meaning;
  - [x] connection to the next article.
- [x] Keep code/output blocks short and executable.
- [x] Avoid relying on development checkout paths in reader-facing commands.
- [x] Keep article status as `draft` until rendered and browser-checked.

## Article Enrichment Pass

- [x] Add `Purpose / Why This Sample Matters` to all 17 SmartDox drafts.
- [x] Add `Concept Focus` to all 17 SmartDox drafts.
- [x] Add `Command Walkthrough` explanations to all 17 SmartDox drafts.
- [x] Add `Reading the Output` explanations to all 17 SmartDox drafts.
- [x] Add `Common Pitfalls` to all 17 SmartDox drafts.
- [x] Change article `Run` sections so the main teaching path is explicit
      command-line input, not `run.sh`.
- [x] Keep `run.sh` in the articles only as a shortcut for replaying the
      documented commands.
- [x] Update sample README wording so `run.sh` is a shortcut verification path,
      not the primary teaching path.
- [x] Update the invocation guide to distinguish learning walkthrough,
      programming-time execution, verification shortcut, and deployment-style
      invocation.

## Validation

For `cncf-samples`:

- [ ] Run from a downloaded package:

  ```bash
  bash scripts/run-all-samples.sh
  ```

- [ ] Confirm all runnable samples pass.
- [ ] Confirm `101-distributed` remains excluded/deferred unless implemented.
- [ ] Run `git diff --check` after checklist or sample documentation changes.

For `simplemodeling-org`:

- [ ] Run `git diff --check`.
- [ ] Run the site build command used by the repository.
- [ ] Browser-check generated `component-based-development` pages.
- [ ] Confirm category navigation shows the new article series.
- [ ] Confirm article links and `site:[...]` references resolve.
- [ ] Confirm Japanese/English spans render acceptably.
- [ ] Confirm code blocks preserve shell commands and sample output.

## Publication Checklist

- [ ] Confirm final `cncf-samples` archive URL.
- [ ] Confirm launcher install commands against public channels.
- [ ] Confirm sample smoke on the downloaded package after upload.
- [ ] Move reviewed articles from `draft` to `published`.
- [ ] Set `published_at` only when the article is actually published.
- [ ] Commit `cncf-samples` checklist separately from article content unless the
      article work is intentionally part of the same slice.
- [ ] Commit `simplemodeling-org` articles after site build and browser smoke.
