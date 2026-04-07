# 08.c-view-cache-lab rework design

## Decision

`10.c-view-cache-lab` will be split into:

- a user-facing sample
- a `cozy` scripted verification

The sample will no longer use a Scala demo main as its primary path.

## Sample-side target

The sample should demonstrate only the user-facing CNCF usage pattern:

1. define an entity and a view in CML
2. generate the component
3. start CNCF through the normal sample runner
4. invoke view search commands from the shell

The sample should communicate:

- view search is the main UI list path
- repeated page-oriented search is a normal CNCF usage pattern
- CNCF can be exercised directly from shell commands

The sample should not communicate:

- internal `ViewCollection` construction
- direct runtime primitive usage
- internal cache proof logic
- metrics assertion logic

## Sample-side execution shape

The preferred user-facing shape is `command`.

Reason:

- `08.c` does not need server/client state observation as its central teaching point
- the point is repeated list search invocation, not server lifecycle management

So the user-facing sequence should be:

1. `command help ...`
2. `command ... --query.limit ... --query.offset ...`
3. repeat nearby-page search commands
4. optionally show metadata or describe output

`run.sh` should only batch these explicit commands.

## Sample-side Scala policy

Target:

- no Scala demo main
- no direct `Subsystem` construction
- no direct `ViewCollection` usage

Allowed Scala:

- only minimal `ComponentFactory` override, if the sample absolutely needs it

Preferred outcome:

- CML-only sample

## Scripted-side target

The following move to `cozy` scripted:

- direct `ViewCollection` usage
- chunk cache hit/miss proof
- small-result cache proof
- metrics proof
- exact backend invocation count checks

The scripted test becomes the technical proof that the runtime cache policy works.

## Rework steps for 08.c

1. remove the current Scala demo main from the sample path
2. redesign README around shell commands
3. rewrite `run.sh` as a shell-first batch sequence
4. keep the model minimal and user-facing
5. create one new `cozy` scripted case that proves:
   - chunk reuse
   - small-result reuse
   - metrics emission

## Completion condition

`08.c` is considered reworked when:

- the sample can be understood from README and shell commands alone
- no internal runtime construction remains in the sample path
- cache proof lives in `cozy` scripted
