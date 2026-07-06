# 01-minimal Checklist

Status authority: `This file is the only progress and completion authority for 01-minimal.`

## Purpose

Deliver the smallest executable Textus operation sample without introducing CML.
This is the entry point for learning Textus runtime operation before learning
Component authoring by CML.

Stage Status:
- Current status: `DONE`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Minimal Component / Service / Operation
- Selector format
- Textus command execution
- Development-directory startup
- Component repository startup
- CLI execution
- Expected hello output
- Explicitly no CML in this line

## Step

- Make `minimal.main.hello` executable through the `cncf` launcher line and align the sample with the current execution and deployment model.

## Checklist

- [x] `samples/01-minimal/src/main/scala` contains the code required for execution
- [x] The command path is `minimal.main.hello`
- [x] `./run.sh` is defined as the development-directory startup entry point
- [x] `./invoke.sh` is defined as the component repository startup entry point
- [x] `./run.sh` uses class discovery for the actively developed component
- [x] `./invoke.sh` points to the sample active packaged source under `samples/component.d`
- [x] `./run.sh` succeeds under the current development-directory model
- [x] `./invoke.sh` succeeds under the current component repository model
- [x] The sample README explains selector format and command path
- [x] The sample README explains development-directory startup vs component repository startup
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
- [x] Development-directory CLI execution works
- [x] Component repository startup works
- [x] Expected output is confirmed
- [x] README is complete and aligned with the current execution model
