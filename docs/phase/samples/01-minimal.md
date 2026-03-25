# 01-minimal Checklist

## Purpose

Deliver the smallest executable CNCF unit with one Component, one Service, and one Operation.

Stage Status:
- Current status: `OPEN`
- Owner: `Codex + human`
- Update rule: `Update this block whenever a checklist item changes state.`

## Focus

- Minimal Component / Service / Operation
- CLI execution
- Expected hello output

## Step

- Make `minimal.main.hello` executable through the CLI.

## Checklist

- [ ] `samples/01-minimal/component.d` defines the minimal structure
- [ ] `samples/01-minimal/src/main/scala` contains the code required for execution
- [ ] `sbt run command minimal.main.hello` succeeds
- [ ] The output is `Hello CNCF`
- [ ] `samples/01-minimal/README.md` is updated to match the implementation

## Exit Criteria

- [ ] Build succeeds
- [ ] CLI execution works
- [ ] Expected output is confirmed
- [ ] README is complete
