# 10-Phase-11-Subsystem-Spec Decisions

## Purpose

Record the phase 11 subsystem specification decisions that have already been settled in journal form before the final notes/design write-up.

This note is an implementation-driving specification memo.
The consolidated explanation is expected to move into `docs/notes` or `docs/design` when phase 11 is completed.

## Confirmed Decisions

### 1. Phase 11 Baseline

- `11-subsystem` is the baseline sample of phase 11.
- It represents the formal subsystem line.
- Its baseline form is:
  - one subsystem
  - one component
  - explicit subsystem selection
  - generic component artifact distributed separately from the subsystem

### 2. Naming Rule

- Subsystem and component are configured independently.
- Subsystem and component may use the same name.
- The formal specification does not depend on them being identical.

### 3. Startup Rule

- Samples in phase 11 should primarily show startup by CLI parameters.
- Config-file startup is the equivalent CNCF parameter/config expression.
- This is not a subsystem-specific feature but part of the ordinary CNCF parameter/config transparency rule.
- In real deployment, config-based startup will often be preferred because multiple startup parameters usually need to be managed together.
- Descriptor-direct startup remains a development/test variation rather than the baseline line.

### 4. Packaging Rule

- The recommended subsystem operation does not bundle component artifacts.
- The reason is that bundling works against the subsystem line's emphasis on reusable components.
- Therefore the baseline formal subsystem uses reusable generic component artifacts distributed separately from the subsystem.
- Bundled subsystem packaging is allowed, but it is an exceptional variant.
- Mixed packaging is an extension of that exceptional case.
- Expanded `sar.d` is treated as development/test startup rather than the baseline distribution form.

### 5. Generated Artifacts

- `*.car` and `*.sar` are generated artifacts.
- They are not treated as committed sample inputs.
- Sample documentation should explain how to generate, place, and execute them.
- `car.d` and `sar.d` are development and inspection shapes around those generated artifacts.

### 6. Multi-Component Direction

- A subsystem is confirmed to support multiple components.
- The natural baseline extension after `11-subsystem` is therefore a multi-component subsystem sample.
- That multi-component sample should stop at independently hosted components.
- Inter-component wiring belongs to phase 12 and should not be pulled into the phase 11 multi-component baseline extension.

### 7. Sample Sequence Direction

The intended sequence direction is:

1. `11-subsystem`
   - baseline formal subsystem
   - one subsystem
   - one generic component
2. new `11.a`
   - multi-component subsystem
   - independently hosted components
   - no inter-component wiring
3. current bundled and mixed packaging labs
   - moved behind the multi-component line

### 8. Mixed Subsystem Intention

The intended mixed subsystem specification is:

- one subsystem
- multiple components
- component sources may be mixed
  - generic component artifacts
  - bundled component artifacts
- inter-component wiring is out of scope for phase 11

This means the mixed subsystem sample is not primarily a wiring sample.
It is a composition and packaging sample that remains below the phase 12 wiring line.

## Remaining Work

- reflect the new sequence in sample numbering
- align bundled and descriptor-direct samples to the agreed baseline conventions
- document the mixed subsystem implementation gap separately from this specification note
