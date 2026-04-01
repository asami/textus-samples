# 03.a-operation-command-lab Implementation Record

- date: 2026-04-01
- status: done

## Purpose

Fix the first command-oriented operation contract line after `03-operation`.

## Target

- `SERVICE > OPERATION`
- `TYPE = COMMAND`
- `INPUT/OUTPUT > VALUE`
- `SUMMARY`
- `DESCRIPTION`

## Verification

- `bash run.sh`
- `command help operation-command-contract-sample.greeting.submit-greeting`

Confirmed:

- `SERVICE > OPERATION`
- `TYPE = COMMAND`
- `INPUT > VALUE`
- `OUTPUT > VALUE`
- help surface through `--discover=classes`
- `returns: GreetingAccepted`

## Follow-up

- entity-integrated command operation can be added later if needed
