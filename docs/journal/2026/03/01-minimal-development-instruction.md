# 01-minimal Development Instruction

## Purpose

Implement the first executable CNCF sample as the smallest possible unit that still demonstrates:

- one Component
- one Service
- one Operation
- one CLI execution path

This sample is the baseline for all later samples.
Keep it intentionally small.

Related shared guidance:

- `guide/invocation/component-and-subsystem-invocation-guide.md`

## Target Sample

- Directory: `samples/01-minimal`
- Sample name: `01-minimal`
- CLI target: `minimal.main.hello`
- Expected output: `Hello CNCF`

## Primary Goal

Make the local sample runner succeed:

```bash
./run.sh
```

And make the observed output match:

```text
Hello CNCF
```

## Required Structure

The implementation must stay within the sample boundary:

```text
samples/01-minimal/
├─ README.md
├─ build.sbt
├─ component.d/
└─ src/main/scala/
```

Use exactly one component, one service, and one operation for this sample.

## Scope

Implement only what is necessary to demonstrate the minimum executable structure.

In scope:

- minimum component definition
- minimum service definition
- single hello operation
- CLI execution path
- README update to match the implementation

Out of scope:

- persistence
- repository abstraction
- async execution
- event handling
- multiple components
- subsystem composition
- Docker-based deployment

## Design Constraints

- The sample must be independently buildable and executable
- The sample must not depend on any other sample
- Prefer the smallest implementation that proves the pattern
- Avoid abstractions that are not required for `minimal.main.hello`
- Do not introduce domain modeling beyond the hello behavior

## Implementation Guidance

### Component

Define one component named to support the command path:

- component: `minimal`

### Service

Define one service:

- service: `main`

### Operation

Define one operation:

- operation: `hello`

The operation should have one job only:

- return or print `Hello CNCF`

### Output Behavior

The user-visible result must be unambiguous.
Do not mix the intended output with unrelated logging in the sample behavior definition.

## Files Expected To Change

At minimum, expect to update:

- `samples/01-minimal/component.d/...`
- `samples/01-minimal/src/main/scala/...`
- `samples/01-minimal/README.md`

You may also update sample-local build settings if required.

## Verification

Implementation is not complete until all of the following are true:

1. The sample builds successfully.
2. `./run.sh` succeeds from `samples/01-minimal`.
3. The output is confirmed as `Hello CNCF`.
4. The README explains the implemented structure and command.
5. `docs/phase/samples/01-minimal.md` is updated to reflect actual progress.

Note:

- `01-minimal` resolves the CNCF version from `versions/cncf-version.conf`, with `CNCF_VERSION` as an override.
- In this workspace, the shared version currently resolves to the published `org.goldenport %% goldenport-cncf % 0.3.14-SNAPSHOT` artifact and can be made available locally via `publishLocal` from the CNCF framework repo.
- The command path remains `minimal.main.hello`.

## Completion Checklist

- [ ] One component is implemented
- [ ] One service is implemented
- [ ] One operation is implemented
- [ ] CLI command `minimal.main.hello` works
- [ ] Output is `Hello CNCF`
- [ ] No dependency on other samples is introduced
- [ ] README is aligned with the implementation
- [ ] Phase checklist is updated

## Notes For Later Samples

This sample should establish the reference style for:

- component naming
- service naming
- operation naming
- README command examples
- smallest acceptable executable sample shape

Do not pre-build features needed only by later samples.
