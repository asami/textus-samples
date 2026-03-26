# 01.b-startup-shapes-lab Verification Instruction

Status: `Active Instruction`

This is the active verification work-order document for `01.b-startup-shapes-lab`.
Do not rewrite this file into a result note or completion report.
If verification is completed, record the result by appending to an implementation record or by creating a separate verification record file.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/01.b-startup-shapes-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.b-startup-shapes-lab.md)

## Purpose

Verify that `01.b-startup-shapes-lab` is not only documented, but also executable as a hands-on lab for comparing CNCF startup shapes.

The lab keeps the intended sample contract:

- local Scala implementation class: `MinimalComponent`
- CNCF component name: `minimal`
- command selector: `minimal.main.hello`
- startup shapes under comparison: `command`, `server`, `client`

The verification work focuses on:

- confirming that the helper scripts match the README
- confirming that verification is performed sequentially, not in parallel
- confirming that the server startup is complete before the dependent checks begin
- confirming that the server shape can be reached through HTTP by using `curl`
- confirming that the client shape is checked against the running server
- confirming that the command shape executes successfully
- confirming that the server and client shapes are explained with realistic startup lines
- confirming that the lab remains self-contained inside `samples/01.b-startup-shapes-lab`

## Work Order

1. Verify the local source and helper scripts exist:

```bash
cd samples/01.b-startup-shapes-lab
find . -maxdepth 3 -type f | sort
```

Expected key files:

- `src/main/scala/minimal/MinimalComponent.scala`
- `component.d/minimal.md`
- `run-command.sh`
- `run-server.sh`
- `run-client.sh`
- `run.sh`
- `invoke.sh`

2. Verify the server startup shape first:

```bash
cd samples/01.b-startup-shapes-lab
./run-server.sh
```

Expected result:

- the command enters the server startup path
- the startup line contains no direct selector
- the process is intended to remain active until interrupted

Do not start `client`, `command`, or `invoke` checks until server startup is complete.

3. Verify REST access through `curl` while the server is running:

```bash
cd samples/01.b-startup-shapes-lab
curl -i http://localhost:8080/minimal/main/hello
```

Expected result:

- the request reaches the running CNCF server
- the verifier records the concrete REST entry used in the environment
- the verifier understands that `http://localhost:8080/` is the correct base URL in the current default server setup
- the verifier understands that `/` is the test-oriented top page, while the `curl` check should use the explicit path for the current sample component
- the verifier confirms that the HTTP path follows the CNCF canonical shape `/component/service/operation`
- the verifier records:
  - URL
  - HTTP method
  - request payload if any
  - response status
  - response body

4. Verify the client startup shape after the server is confirmed:

```bash
cd samples/01.b-startup-shapes-lab
./run-client.sh
```

Expected result:

- the command enters the client startup path
- by default the lab shows `client --help`
- the startup line contains no direct selector
- the client check is executed only after the server check, not in parallel with startup

5. Verify the command shape after the server-dependent checks:

```bash
cd samples/01.b-startup-shapes-lab
./run-command.sh
```

Expected result:

- the selector is `minimal.main.hello`
- visible output is `Hello CNCF`

6. Verify the deployment-style command path:

```bash
cd samples/01.b-startup-shapes-lab
./invoke.sh
```

Expected result:

- the jar is packaged locally
- the artifact is copied into `samples/component-repository.d`
- the selector remains `minimal.main.hello`

7. Compare the README against the actual behavior:
   - `run.sh` delegates to `run-command.sh`
   - `run-command.sh` demonstrates command mode
   - `run-server.sh` demonstrates server mode
   - `run-client.sh` demonstrates client mode
   - the verification order is sequential
   - the examples and explanations match the actual startup lines

8. Update the following only after the verification results are clear:
   - `docs/phase/samples/01.b-startup-shapes-lab.md`
   - `samples/01.b-startup-shapes-lab/README.md`
   - a verification or implementation record if the observed behavior differs from the current README

## Acceptance Criteria

`01.b-startup-shapes-lab` is honestly verified only when all of the following are true:

- the lab is self-contained in `samples/01.b-startup-shapes-lab`
- `./run-server.sh` exposes the server startup shape clearly
- at least one REST call from `curl` is verified against the running server shape
- the record distinguishes the base URL `http://localhost:8080/` from the explicit REST path used for `curl`
- the REST path targets the sample component as `/minimal/main/hello`
- `./run-client.sh` exposes the client startup shape clearly after server startup is complete
- `./run-command.sh` succeeds and shows the command shape clearly after the server-dependent checks
- `./invoke.sh` still resolves `minimal.main.hello` through the repository path
- the README matches the observed behavior
- [`docs/phase/samples/01.b-startup-shapes-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.b-startup-shapes-lab.md) remains an honest status record

## Related Documents

- [`samples/01.b-startup-shapes-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/01.b-startup-shapes-lab/README.md)
- [`docs/phase/samples/01.b-startup-shapes-lab.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.b-startup-shapes-lab.md)
- [`samples/01.a-invocation-source-lab/README.md`](/Users/asami/src/dev2026/cncf-samples/samples/01.a-invocation-source-lab/README.md)
