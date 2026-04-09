# 10-Phase-11-Subsystem-Spec-Review Checklist

## Goal

Fix the formal specification of phase `11-*` by comparing the original intent of the subsystem line with the current implementation and documentation.

## Review Scope

- `11-subsystem`
- `11.a-multi-component-subsystem-lab`
- `11.b-subsystem-bundled-component-lab`
- `11.c-subsystem-mixed-component-lab`
- `11.d-implicit-subsystem-lab`
- `11.e-sar-dir-lab`
- `11.f-subsystem-parameter-lab`

## Questions To Settle

- [x] What is the official baseline of phase 11?
  - [x] `11-subsystem` is confirmed as the baseline formal subsystem sample
  - [x] the baseline uses a generic component artifact rather than a bundled component
- [x] What is the official naming rule?
  - [x] subsystem and component are configured independently
  - [x] subsystem and component may use the same name, but the formal specification does not depend on that coincidence
  - [x] bundled and descriptor-direct variants follow the same independent naming rule
- [x] What is the official startup rule?
  - [x] samples show startup primarily through CLI parameters
  - [x] config-file startup is documented as the equivalent CNCF parameter/config expression rather than a subsystem-specific feature
  - [x] descriptor-direct startup is clearly separated as a development/test variant
  - [x] the documentation notes that real deployments will often prefer config because multiple startup parameters usually need to be managed together
- [x] What is the official packaging rule?
  - [x] the recommended subsystem operation does not bundle component artifacts
  - [x] the baseline formal subsystem uses reusable generic component artifacts distributed separately from the subsystem
  - [x] bundled subsystem packaging is treated as an exceptional variant rather than the recommended default
  - [x] mixed subsystem packaging is treated as an extension of that exceptional bundled case
  - [x] expanded `sar.d` is positioned as development/test startup, not the baseline distribution form
- [x] What is the official handling of generated artifacts in samples?
  - [x] `*.car` and `*.sar` are treated as generated artifacts rather than committed sample inputs
  - [x] READMEs describe how to generate and place them instead of assuming they are already present in the repository
  - [x] `car.d` and `sar.d` are treated as development and inspection shapes around generated artifacts
- [ ] What is the official status of the mixed subsystem sample?
  - [x] subsystem is confirmed to support multiple components as part of the formal phase 11 line
  - [x] the baseline extension after `11-subsystem` should be a multi-component subsystem sample
  - [x] that multi-component sample is limited to independently hosted components and does not introduce inter-component wiring
  - [x] bundled and mixed packaging samples are positioned as secondary variants after the multi-component line
  - [x] the intended mixed specification is written down
  - [x] the intended mixed specification now has a runnable sample implementation
- [x] What is the official operation output contract rule?
  - [x] operation output contracts are required
  - [x] missing output contracts are treated as errors rather than as `unknown`
  - [x] `void` is only valid when stated explicitly
  - [x] hand-written operation definitions are expected to converge toward the same explicit rule as the CML line

## Documentation Alignment

- [x] sample READMEs are aligned to the decided formal specification
- [x] phase checklists use `11-*` names rather than old `09-*` labels
- [x] outdated sample paths are removed from `11.f-subsystem-parameter-lab`
- [x] the bundled sample no longer has an unresolved naming mismatch in the runnable walkthrough
- [ ] journal implementation records describe the same baseline/variant split

## Exit Criteria

- [x] the phase 11 baseline is unambiguous
- [x] each lab has a single clear role relative to the baseline
- [x] implementation gaps are separated from specification decisions
- [x] the next implementation task for phase 11 is explicit
