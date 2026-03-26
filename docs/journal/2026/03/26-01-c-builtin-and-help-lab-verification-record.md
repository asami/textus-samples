# 01.c-builtin-and-help-lab Verification Record

Status: `Completed`

Verified on 2026-03-26.

## Summary

`01.c-builtin-and-help-lab` is executable as a hands-on lab for distinguishing:

- sample-defined command paths
- runtime-provided help surfaces
- runtime-provided admin surfaces

The lab keeps the intended sample contract:

- local Scala implementation class: `MinimalComponent`
- CNCF component name: `minimal`
- sample-defined selector: `minimal.main.hello`

## Step 1: Source And Helper Scripts

Command:

```bash
cd samples/01.c-builtin-and-help-lab
find . -maxdepth 3 -type f | sort
```

Observed result:

- the expected source and helper scripts are present, including:
  - `src/main/scala/minimal/MinimalComponent.scala`
  - `component.d/minimal.md`
  - `run-subsystem-help.sh`
  - `run-component-help.sh`
  - `run-operation-help.sh`
  - `run-admin-ping.sh`
  - `run.sh`
  - `invoke.sh`
- the directory also contains the built sample jar under `target/scala-3.3.7/`

## Step 2: Subsystem Help

Command:

```bash
cd samples/01.c-builtin-and-help-lab
./run-subsystem-help.sh
```

Observed result:

- the command succeeded
- the output reflected subsystem-level structured help
- the output was not just `minimal.main.hello`
- the subsystem help listed `Minimal`, `admin`, `client`, `debug`, and `spec`

## Step 3: Component Help

Command:

```bash
cd samples/01.c-builtin-and-help-lab
./run-component-help.sh
```

Observed result:

- the command succeeded
- the output reflected help for the `minimal` component
- the output distinguished sample component visibility from runtime-provided help surfaces
- the output listed `main`, `meta`, and `system` under the `Minimal` component

## Step 4: Operation Help

Command:

```bash
cd samples/01.c-builtin-and-help-lab
./run-operation-help.sh
```

Observed result:

- the command succeeded
- the target was `minimal.main.hello`
- `help` was the navigation-oriented entry
- `meta.help` was the structured introspection surface
- the output identified `Minimal.main.hello` with usage `command Minimal.main.hello`

## Step 5: Admin Ping

Command:

```bash
cd samples/01.c-builtin-and-help-lab
./run-admin-ping.sh
```

Observed result:

- the command succeeded
- the output showed a builtin/runtime-provided command surface
- this command is not implemented in `MinimalComponent.scala`
- the output reported `runtime: goldenport-cncf` and `mode: command`

## Step 6: Deployment-Style Invoke

Command:

```bash
cd samples/01.c-builtin-and-help-lab
./invoke.sh
```

Observed result:

- the jar was packaged locally
- the artifact was copied into `samples/component-repository.d`
- the selector remained `minimal.main.hello`
- output: `Hello CNCF`
- the invoke path resolved through `--component-repository=component-dir:../component-repository.d`

## Step 7: Client Observation

Server command used:

```bash
cd samples/01.b-startup-shapes-lab
./run-server.sh
```

Client command used:

```bash
cd samples/01.b-startup-shapes-lab
./run-client.sh
```

Observed result:

- the client path succeeded against the already running server
- the observed surface was the builtin client help entry
- this demonstrates the realistic remote-facing entry point
- the client output described remote CNCF server usage and examples

## Step 8: HTTP Observation

Base URL:

- `http://localhost:8080`

HTTP path:

- `/minimal/main/hello`

Command:

```bash
curl -i http://localhost:8080/minimal/main/hello
```

Observed result:

- response status: `HTTP/1.1 200 OK`
- response body: `Hello CNCF`
- the HTTP surface was reachable on `http://localhost:8080`

## Step 9: README Comparison

Observed result:

- command mode is primary for this lab
- `client` is described as a realistic remote-facing entry
- `server` + `curl` is described as HTTP-surface observation
- sample-defined selectors and runtime-provided selectors are clearly distinguished

## Notes

- `command` is the primary learning surface for this lab
- `client` is a realistic remote-facing entry point when a server is already running
- `server` + `curl` is the HTTP-surface observation path
- The only issue encountered during verification was that `invoke.sh` initially lacked the executable bit; that was corrected and the script then passed
- Phase status was updated to `DONE` after all verification checkboxes were completed
