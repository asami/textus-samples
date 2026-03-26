# 01-minimal Checklist

Status authority: `This file is the only progress and completion authority for 01-minimal.`

## Purpose

Deliver the smallest executable CNCF unit with one Component, one user-defined Service, and one user-defined Operation, while making the CNCF execution model visible.

Stage Status:
- Current status: `DONE`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Minimal Component / Service / Operation
- Selector format
- Development-time vs deployment-style invocation
- CLI execution
- Expected hello output

## Step

- Make `minimal.main.hello` executable through the CLI and align the sample with the current execution and deployment model.

## Checklist

- [x] `samples/01-minimal/src/main/scala` contains the code required for execution
- [x] The command path is `minimal.main.hello`
- [x] `./run.sh` is defined as the development-time entry point
- [x] `./invoke.sh` is defined as the deployment-style entry point
- [x] `./run.sh` uses class discovery for the actively developed component
- [x] `./invoke.sh` points to the sample virtual repository under `samples/component-repository.d`
- [x] `./run.sh` succeeds under the current development-time model
- [x] `./invoke.sh` succeeds under the current deployment-style model
- [x] The sample README explains selector format and command path
- [x] The sample README explains `run.sh` vs `invoke.sh`
- [x] The sample README explains development-time class loading vs deployment-time repository loading
- [x] The sample README states the purity constraints for `hello`
- [x] The sample README mentions framework-visible service taxonomy where applicable
- [x] The output is confirmed as `Hello CNCF`
- [x] The implementation remains stateless
- [x] The implementation remains deterministic
- [x] The implementation does not depend on external systems
- [x] The sample remains independent from other samples

## Exit Criteria

- [x] `samples/01-minimal/src/main/scala` contains the code required for execution
- [x] Build succeeds
- [x] Development-time CLI execution works
- [x] Deployment-style invocation works
- [x] Expected output is confirmed
- [x] README is complete and aligned with the current execution model
