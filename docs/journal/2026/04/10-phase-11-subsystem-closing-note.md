# 10-Phase-11-Subsystem-Closing Note

## Summary

Phase 11 is now established as the subsystem line before wiring.

The line now covers:

- the formal subsystem baseline
- the multi-component extension without wiring
- bundled packaging as a secondary variant
- mixed packaging as a secondary variant
- implicit subsystem startup
- expanded `sar.d` startup
- descriptor-direct startup

## Confirmed Sample Roles

1. `11-subsystem`
   - the baseline formal subsystem
   - one subsystem
   - one generic component artifact
2. `11.a-multi-component-subsystem-lab`
   - the baseline extension after `11-subsystem`
   - one subsystem
   - multiple independently hosted components
   - no inter-component wiring
3. `11.b-subsystem-bundled-component-lab`
   - the bundled subsystem variant
   - the selected subsystem carries its component artifact
4. `11.c-subsystem-mixed-component-lab`
   - the mixed subsystem variant
   - one subsystem hosts both a standalone generic component and a bundled subsystem-local component
   - no inter-component wiring
5. `11.d-implicit-subsystem-lab`
   - the implicit subsystem form
6. `11.e-sar-dir-lab`
   - expanded `sar.d` startup for loader/debug inspection
7. `11.f-subsystem-parameter-lab`
   - descriptor-direct startup for development and testing

## Specification Outcomes

- `11-subsystem` is the baseline sample of the phase
- subsystem and component are configured independently
- phase 11 samples show startup primarily through CLI parameters
- config-file startup remains the equivalent production-oriented expression
- the recommended subsystem operation does not bundle component artifacts
- bundled and mixed packaging remain secondary variants
- `*.car` and `*.sar` are generated artifacts, not committed sample inputs
- operation output contracts are required and `unknown` is no longer accepted as the implicit result shape

## Implementation Outcomes

- all current phase 11 samples are runnable
- generic subsystem startup now includes builtin components by default
- subsystem assembly warnings are recorded and visible through `admin.assembly.warnings`
- duplicate component selection now applies a documented winner-selection policy

## Boundary To Phase 12

Phase 11 stops at subsystem composition and packaging.

Phase 12 begins where:

- components are explicitly wired to one another
- inter-component dependencies become part of the walkthrough
- subsystem composition is no longer only coexistence but also coordination

## Next Step

The next implementation line is `12-subsystem-wiring`.
