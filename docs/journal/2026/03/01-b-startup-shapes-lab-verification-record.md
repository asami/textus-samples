# 01.b-startup-shapes-lab Verification Record

Date: 2026-03-26

## Summary

`01.b-startup-shapes-lab` is executable as a hands-on comparison of CNCF startup shapes.
The command, server, client, and repository-based paths were verified from the lab directory.

## Verified Results

- `./run-command.sh`
  - succeeded
  - startup line: `runMain org.goldenport.cncf.CncfMain --discover=classes command minimal.main.hello`
  - visible output: `Hello CNCF`
- `./run-client.sh`
  - succeeded
  - startup line: `runMain org.goldenport.cncf.CncfMain client --help`
  - visible output: client help text
- `./run-server.sh`
  - started successfully
  - startup line: `runMain org.goldenport.cncf.CncfMain --discover=classes server`
  - the process remains active as intended
- `curl -i http://localhost:8080/minimal/main/hello`
  - succeeded against the running server
  - response: `HTTP/1.1 200 OK`
  - body: `Hello CNCF`
- `./invoke.sh`
  - succeeded
  - startup line: `runMain org.goldenport.cncf.CncfMain --component-repository=component-dir:../component-repository.d command minimal.main.hello`
  - visible output: `Hello CNCF`

## Observations

- The lab is self-contained under `samples/01.b-startup-shapes-lab`.
- The README matches the observed command, server, and client startup shapes.
- The client was verified only after the server startup had completed.
- The `minimal.main.hello` HTTP route is now reachable as documented.
- The `run-server.sh` path is intentionally long-running, so it needs interruption or timeout during verification.

## Issues

No blocking issues were found during verification.
