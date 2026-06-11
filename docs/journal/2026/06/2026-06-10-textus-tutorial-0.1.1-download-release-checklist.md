# textus-tutorial 0.1.1 Download Release Checklist

Date: 2026-06-10
Status: working checklist

## Purpose

This checklist records the release procedure for the `cncf-samples` download
package published as `textus-tutorial` version `0.1.1`.

The public name remains:

```text
textus-tutorial
```

The public path remains:

```text
textus/tutorial/textus-tutorial
```

The upload step is manual. A human runs:

```bash
/Users/asami/src/maven-repository/upload.sh
```

Codex does not run `upload.sh`. After the manual upload completes, Codex runs
the public URL checks and downloaded-package smoke described below.

SimpleModeling.org articles are written for the post-upload publication state:
they may show the final public download URL before the archive is uploaded.
Before upload, that URL is an expected publication target, not a live endpoint.

## Release Inputs

- [x] Release version is `0.1.1`.
- [x] Public name remains `textus-tutorial`.
- [x] Public path remains `textus/tutorial/textus-tutorial`.
- [x] SimpleModeling.org article text uses the final post-upload download URL.
- [x] `sbt-cozy` is updated to a bridge-compatible version.
- [x] `upload.sh` is explicitly manual.
- [x] Public archive URL is verified after upload.
- [x] Downloaded package smoke is verified after upload.

## Local Generation

Run from:

```bash
/Users/asami/src/dev2026/cncf-samples
```

Expected commands:

```bash
sbt --batch cozyPlanDistributeSamples
sbt --batch cozyPublishProject
sbt --batch cozyDistributeSamples
sbt --batch cozyIndexWarehouse
```

For this 0.1.1 preparation, the collection archive shape required the fixed
Cozy sample distributor from the Cozy checkout. Until that Cozy change is in
the installed runtime, generate archives with:

```bash
cd /Users/asami/src/dev2025/cozy
sbt --batch "run distribute-samples /Users/asami/src/dev2026/cncf-samples --warehouse /Users/asami/src/maven-repository --name textus-tutorial --version 0.1.1"
```

Important: if `sbt --batch cozyDistributeSamples` is run again before upload,
rerun the fixed Cozy checkout command above afterwards. The upload target for
the collection archive must be the repository-root shaped zip containing
`samples/`, `scripts/`, and `versions/`.

Expected collection archive:

```text
/Users/asami/src/maven-repository/repository/download/textus/tutorial/textus-tutorial/0.1.1/textus-tutorial-0.1.1.zip
```

Expected per-sample archives are created for distributable sample projects:

```text
/Users/asami/src/maven-repository/repository/download/textus/tutorial/textus-tutorial/<sample>/0.1.1/<sample>-0.1.1.zip
```

## Local Verification Before Upload

- [x] `git diff --check` passes in `cncf-samples`.
- [x] `sbt --batch cozyPlanDistributeSamples` passes.
- [x] `sbt --batch cozyPublishProject` passes.
- [x] Archive generation passes using the fixed Cozy checkout command above.
- [x] `sbt --batch cozyIndexWarehouse` passes.
- [x] `textus-tutorial-0.1.1.zip` exists in the local warehouse.
- [x] The local archive can be extracted under `/tmp`.
- [x] The extracted archive has the expected repository root shape.
- [x] The extracted archive contains `scripts/run-all-samples.sh`.
- [x] The extracted archive contains `samples/<sample>/run.sh` for executable
      samples.
- [x] `bash scripts/run-all-samples.sh` passes from the extracted archive.
- [x] `101-distributed` remains deferred because it has no normal `run.sh`.
- [x] Public metadata titles use the current `textus-samples-<sample>` names
      and no stale historical numbering for the active samples.
- [x] SimpleModeling.org article sample references all resolve to directories
      contained in the extracted archive.

Local verification result:

```text
archive: /Users/asami/src/maven-repository/repository/download/textus/tutorial/textus-tutorial/0.1.1/textus-tutorial-0.1.1.zip
extracted smoke root: /tmp/textus-tutorial-0.1.1-preupload-check-2
log root: /tmp/textus-tutorial-0.1.1-preupload-check-2/logs-escalated
result: PASS, all 49 executable samples passed
note: sandboxed smoke failed because sbt could not write ~/.sbt/boot/sbt.boot.lock;
      the same extracted package passed when run outside the filesystem sandbox.
```

## Manual Handoff

After local verification succeeds, stop Codex-driven execution and ask the
human operator to run:

```bash
/Users/asami/src/maven-repository/upload.sh
```

Do not run the upload command from Codex.

The SimpleModeling.org article drafts should already be publication-oriented at
this point. Do not rewrite them to say that the URL will be provided later; the
post-upload check below verifies that the already-written URL is live.

## Codex Post-Upload Verification

After the human reports that `upload.sh` has completed, Codex should run:

```bash
curl -I https://www.simplemodeling.org/repository/download/textus/tutorial/textus-tutorial/0.1.1/textus-tutorial-0.1.1.zip
curl -L -o /tmp/textus-tutorial-0.1.1.zip https://www.simplemodeling.org/repository/download/textus/tutorial/textus-tutorial/0.1.1/textus-tutorial-0.1.1.zip
```

Then verify the downloaded package:

```bash
rm -rf /tmp/textus-tutorial-0.1.1
mkdir -p /tmp/textus-tutorial-0.1.1
cd /tmp/textus-tutorial-0.1.1
unzip /tmp/textus-tutorial-0.1.1.zip
test -f scripts/run-all-samples.sh
test -f versions/cncf-version.conf
find samples -mindepth 2 -maxdepth 2 -name run.sh -print | sort
find samples -maxdepth 2 -type d -name 101-distributed -print
test ! -e samples/101-distributed/run.sh
CNCF_SAMPLE_VALIDATION_LOG_DIR="/tmp/textus-tutorial-0.1.1-smoke-$(date +%Y%m%d-%H%M%S)" \
  bash scripts/run-all-samples.sh
```

Post-upload checklist:

- [x] Public `HEAD` returns success for the collection archive.
- [x] Public archive downloads to `/tmp/textus-tutorial-0.1.1.zip`.
- [x] Public archive extracts successfully.
- [x] Extracted root contains `samples/`, `scripts/run-all-samples.sh`,
      `versions/`, and executable sample `run.sh` scripts.
- [x] Downloaded-package smoke passes.
- [x] `101-distributed` remains deferred because no normal `run.sh` is present.
- [x] Failure details, if any, are recorded with HTTP status, archive path,
      sample name, and log path.

Post-upload verification result:

```text
HEAD: HTTP/2 200
content-type: application/zip
content-length: 1091511
public archive: https://www.simplemodeling.org/repository/download/textus/tutorial/textus-tutorial/0.1.1/textus-tutorial-0.1.1.zip
downloaded archive: /tmp/textus-tutorial-0.1.1.zip
extracted smoke root: /tmp/textus-tutorial-0.1.1-public-smoke
log root: /tmp/textus-tutorial-0.1.1-public-smoke/logs
result: PASS, all 49 executable samples passed
101-distributed: present as deferred sample, no run.sh
```

## Deferred Publication Work

- [x] Verify that SimpleModeling.org article download URLs match the live
      public archive after upload.
- [ ] Move article drafts from `status=draft` to `status=published` only after
      final article review.
- [ ] Set `published_at` only at actual publication time.
