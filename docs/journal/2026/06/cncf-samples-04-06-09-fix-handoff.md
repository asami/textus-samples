# CNCF Samples 04/06/09 Fix Handoff

Date: 2026-06-01
Status: handoff note

## Purpose

This note records the current fix state for the following `cncf-samples`
validation failures:

- `04.d-crud-server-memory-lab`
- `04.e-crud-explicit-sync-lab`
- `06.b-test-sync-command-lab`
- `09-aggregate`

The fixes include CNCF core changes. This is a working handoff note, not a
normative specification. The relevant behavior should be reflected into
design/spec documents later if it becomes part of the stable execution model.

## Current Outcome

The target samples have been validated against the local CNCF core checkout:

- `04.d-crud-server-memory-lab` completes create/load through the sample server.
- `04.e-crud-explicit-sync-lab` completes explicit server/client CRUD flow.
- `06.b-test-sync-command-lab` returns from sync-job command execution instead
  of leaving the command process alive.
- `09-aggregate` completes create-order, add-line, and load-order.

The `09-aggregate` validation result included an order and one order line:

```yaml
id: single-global-entity-order-...
name: alpha-...
status: Draft
lines:
- id: single-global-entity-order_line-...
  order_id: single-global-entity-order-...
  name: pen
  quantity: 2
```

## CNCF Core Changes

### Server Port Configuration

`cncf dev server` now uses the configured server port instead of always falling
back to the default server port.

Configuration lookup order:

- `textus.server.port`
- `cncf.server.port`
- `Http4sHttpServer.defaultPort`

This matters for sample validation because each server/client sample now uses a
dedicated port and must not collide with the Web development server.

### Command-Mode Shutdown

One-shot command execution now shuts down the subsystem after command
completion.

This is required for async/job-backed command execution. Without shutdown, the
in-memory job scheduler/worker can keep the JVM alive after the command has
already returned the job result.

The concrete failure was observed in `06.b-test-sync-command-lab`.

### Entity ID Canonicalization

Action-layer entity load/update/delete paths now canonicalize externally parsed
entity IDs to the registered entity collection when possible.

This is required because printed IDs such as:

```text
single-global-entity-item-...
```

do not preserve all internal entity collection major/minor information. A later
load can therefore parse the ID into a compatible but non-identical collection
identity. The runtime must map that external ID back to the registered
collection before store access.

Canonicalization was added to:

- action-level load
- action-level internal load
- action-level update by ID
- action-level delete
- action-level hard delete
- unit-of-work entity store load/update/delete operations

### Aggregate Authorization Record

Aggregate load for authorization now resolves the canonical aggregate root/member
entity ID and reads the raw persisted record for authorization material.

This avoids authorizing against a working-set record that does not include
security attributes. The concrete `09-aggregate` failure was:

```text
Security attributes are not available for authorization.
```

The authorization record is now built from the persistent collection descriptor
and the raw persisted record.

## Sample Changes

### 04.d and 04.e

The server/client CRUD labs now run on dedicated sample ports:

- `04.d`: `19542`
- `04.e`: `19543`

Client scripts pass `--baseurl` after the operation selector. Passing
`--baseurl` before the operation is interpreted as an operation selector and
produces an operation-not-found error.

The demo scripts now:

- start only their own sample server;
- wait for server readiness;
- extract either direct result IDs or job result IDs;
- report load errors instead of silently treating an error envelope as a valid
  result.

### 06.b

The sync command lab now uses current launcher form:

```bash
cncf dev command --project . TestSync.Item.createItem \
  --name beta \
  --title Beta \
  --textus.command.execution-mode sync-job-async-interface
```

The execution-mode option is passed after the operation selector and operation
arguments. This matches the current command parsing behavior.

The README title and sample references were corrected from the older `04.b`
wording to the `06.b` CQRS context.

### 09-aggregate

The aggregate sample now runs on dedicated sample port `19549`.

The runner no longer kills broad CNCF/java processes. It tracks and shuts down
only the server process that it starts. This is important because Web
development may be running concurrently.

The runner now:

- starts the sample server on `19549`;
- waits for server readiness;
- sends client calls with `--baseurl` after the operation selector;
- handles direct results and job results;
- uses current aggregate argument names such as `--orderId` and `--lineName`.

The sample no longer passes `--privilege system` in the aggregate flow. The
validated path is that the same subject creates and then reads the aggregate.

`AddLineAggregateBehavior` now also implements `ActionBehavior`, because the
behavior uses action helper methods such as entity creation.

## Operational Notes

Do not use broad process cleanup while Web development is running. In
particular, do not kill the repository default server port or unrelated JVMs.

Known sample ports from this fix set:

- `04.d`: `19542`
- `04.e`: `19543`
- `09-aggregate`: `19549`

The repository default server port remains separate from these focused sample
ports.

For `cncf dev client`, option placement matters. Use:

```bash
cncf dev client --project . <operation-selector> --baseurl http://localhost:<port>
```

not:

```bash
cncf dev client --project . --baseurl http://localhost:<port> <operation-selector>
```

For `cncf dev command`, execution options that affect command behavior should
also be placed after the operation selector unless the parser is explicitly
changed to support them before the selector.

## Validation

Validation completed:

```bash
sbt --batch Test/compile
```

in the CNCF core checkout.

Validation completed:

```bash
samples/09-aggregate/run.sh
```

against the local CNCF core checkout.

Syntax and whitespace checks completed:

```bash
bash -n samples/09-aggregate/run.sh
git diff --check
```

No leftover sample server was found on port `19549` after the final aggregate
run.

## Follow-Up

Consider centralizing entity ID canonicalization so the action layer,
unit-of-work interpreter, and aggregate load path do not drift.

Review unit-of-work authorization behavior for non-action callers. Store access
is now canonicalized, but authorization targets may still need a stricter shared
canonicalization rule outside the action-layer path.

Consider making `cncf dev client` return a non-zero exit status when the HTTP
response is an error envelope. Current scripts must explicitly detect error
payloads to fail early.

Apply the dedicated-port and own-process-cleanup pattern to any remaining
server/client samples that still depend on default ports or broad process
cleanup.
