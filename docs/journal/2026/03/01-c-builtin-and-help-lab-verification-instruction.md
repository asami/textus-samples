# 01.c-builtin-and-help-lab Verification Instruction

Status: `Active Instruction`

This is the active verification work-order document for `01.c-builtin-and-help-lab`.
Do not rewrite this file into a result note or completion report.
If verification is completed, record the result by appending to an implementation record or by creating a separate verification record file.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/01.c-builtin-and-help-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.c-builtin-and-help-lab.md)

## Purpose

Verify that `01.c-builtin-and-help-lab` is executable as a hands-on lab for distinguishing:

- sample-defined command paths
- builtin/runtime-provided help surfaces
- builtin/runtime-provided admin surfaces

The lab keeps the intended sample contract:

- local Scala implementation class: `MinimalComponent`
- CNCF component name: `minimal`
- sample-defined selector: `minimal.main.hello`

The lab also introduces runtime-provided surfaces:

- `meta.help`
- `help <selector>`
- `admin.system.ping`

## Verification Policy

Use this fixed order and do not skip steps:

1. `./run-subsystem-help.sh`
2. `./run-component-help.sh`
3. `./run-operation-help.sh`
4. `./run-admin-ping.sh`
5. `./invoke.sh`
6. `client` observation
7. `server` + `curl` observation

After each step:

1. run the command
2. copy the important output cue
3. write the result into the matching section of the verification record

Verification record:

- [`docs/journal/2026/03/26-01-c-builtin-and-help-lab-verification-record.md`](/Users/asami/src/dev2026/cncf-samples/docs/journal/2026/03/26-01-c-builtin-and-help-lab-verification-record.md)

Important rule:

- do not mark the phase checklist `DONE` until every section in the verification record is filled

## Work Order

1. Verify the local source and helper scripts exist:

```bash
cd samples/01.c-builtin-and-help-lab
find . -maxdepth 3 -type f | sort
```

Expected key files:

- `src/main/scala/minimal/MinimalComponent.scala`
- `component.d/minimal.md`
- `run-subsystem-help.sh`
- `run-component-help.sh`
- `run-operation-help.sh`
- `run-admin-ping.sh`
- `run.sh`
- `invoke.sh`

2. Verify subsystem-level structured help through command mode:

```bash
cd samples/01.c-builtin-and-help-lab
./run-subsystem-help.sh
```

Expected cue:

- output contains `type: subsystem` or `components:`

Pass condition:

- the command succeeds
- the output is clearly subsystem help, not `Hello CNCF`

3. Verify component-level structured help for `minimal`:

```bash
cd samples/01.c-builtin-and-help-lab
./run-component-help.sh
```

Expected cue:

- output contains `minimal`

Pass condition:

- the command succeeds
- the output is clearly about the `minimal` component

4. Verify operation help through the navigation-oriented `help` entry:

```bash
cd samples/01.c-builtin-and-help-lab
./run-operation-help.sh
```

Expected cue:

- output contains `minimal.main.hello` or operation-help fields

Pass condition:

- the command succeeds
- the target is clearly `minimal.main.hello`
- the record note states:
  - `help` is navigation-oriented
  - `meta.help` is structured introspection

5. Verify a builtin admin command:

```bash
cd samples/01.c-builtin-and-help-lab
./run-admin-ping.sh
```

Expected cue:

- output contains `runtime:` or `mode:` or `pong`

Pass condition:

- the command succeeds
- the record note states that this command is not implemented in `MinimalComponent.scala`

6. Verify deployment-style execution still resolves the sample-defined selector:

```bash
cd samples/01.c-builtin-and-help-lab
./invoke.sh
```

Expected cue:

- output contains `Hello CNCF`

Pass condition:

- the command succeeds
- deployment-style execution still resolves `minimal.main.hello`

7. Verify the realistic operational entry through `client`:

Use an already running server, for example from `01.b-startup-shapes-lab`, and then confirm that `client` is the more realistic remote-facing entry point in actual operation.

At minimum, record:

- the startup command used for the server
- the client command used
- what builtin/help-oriented surface was observed through the client path

Pass condition:

- the record explains why `client` is the realistic remote-facing entry point

8. Verify the HTTP-facing entry through `server` + `curl`:

Use an already running server, for example from `01.b-startup-shapes-lab`, and then confirm that HTTP-facing observation is possible via `curl`.

At minimum, record:

- the base URL
- the HTTP path used
- the response status
- the response body

Pass condition:

- the record explains why `server` + `curl` is HTTP-surface observation, not the main operational entry

9. Compare the README against the actual behavior:
   - command mode is primary for this lab
   - `client` is described as a realistic remote-facing entry
   - `server` + `curl` is described as HTTP-surface observation
   - sample-defined selectors and runtime-provided selectors are clearly distinguished

10. Update the following only after verification results are clear:
   - `docs/phase/samples/01.c-builtin-and-help-lab.md`
   - `samples/01.c-builtin-and-help-lab/README.md`
   - `docs/journal/2026/03/26-01-c-builtin-and-help-lab-verification-record.md`

## Acceptance Criteria

`01.c-builtin-and-help-lab` is honestly verified only when all of the following are true:

- the lab is self-contained in `samples/01.c-builtin-and-help-lab`
- `./run-subsystem-help.sh` succeeds
- `./run-component-help.sh` succeeds
- `./run-operation-help.sh` succeeds
- `./run-admin-ping.sh` succeeds
- `./invoke.sh` still resolves `minimal.main.hello`
- the verification record distinguishes:
  - sample-defined selector
  - runtime-provided help surfaces
  - runtime-provided admin surfaces
- the verification record explains:
  - `command` for development-time testing, learning, and management commands
  - `client` for realistic remote usage
  - `server` + `curl` for HTTP-surface observation
- [`docs/phase/samples/01.c-builtin-and-help-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.c-builtin-and-help-lab.md) remains an honest status record

## Mini-Low Execution Note

If a smaller model is assigned this work, it should do only this:

1. follow the seven steps in order
2. after each step, fill one section in the verification record
3. do not summarize early
4. do not mark the checklist `DONE` until all record sections are filled

## Related Documents

- [`samples/01.c-builtin-and-help-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/01.c-builtin-and-help-lab/README.md)
- [`docs/phase/samples/01.c-builtin-and-help-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.c-builtin-and-help-lab.md)
- [`samples/01.b-startup-shapes-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/01.b-startup-shapes-lab/README.md)
