# 03-operation Implementation Record

- date: 2026-04-01
- status: done

## Internal Verification

Verified:

- `SERVICE > OPERATION`
- `INPUT > TYPE`
- `OUTPUT > TYPE`
- `SUMMARY`
- `DESCRIPTION`
- operation-level `TYPE = COMMAND | QUERY`

Observed through the Cozy/Kaleidox parser-model and generation test path.
The user-facing sample was kept free of direct parser/model runner dependencies.

## Verification

- Cozy/Kaleidox parser-model tests
- Cozy `modeler-scala` on `service-operation-contract.dox`

Confirmed generated `operationDefinitions` for `SERVICE > OPERATION` with:

- `name = "greeting"`
- `kind = "QUERY"`
- `inputType = "GreetingQuery"`
- `outputType = "GreetingResult"`
- `inputSummary`
- `inputDescription`
- `outputSummary`
- `outputDescription`

Confirmed runnable sample path:

- `command help operation-contract-sample.greeting.greeting`
- `returns: GreetingResult`

The generated component is discovered through `--discover=classes` after:

- class discovery support for `Component.Factory`
- generator suppression of empty generated services for operation-only components

The sample now uses inline operation-local values:

- `INPUT > VALUE`
- `OUTPUT > VALUE`

## Closure

The `03` line is continued and now closed by:

- `03.a-operation-command-lab`
- `03.b-operation-entity-lab`
