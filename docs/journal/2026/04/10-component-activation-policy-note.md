# 10-Component-Activation-Policy Note

## Purpose

Record the current CNCF activation policy discussion while phase 12 is still being implemented.

This note focuses on the distinction between:

- discovery
- search eligibility
- activation

The goal is to improve DX without reintroducing noisy implicit loading.

## Agreed Direction

### 1. Development-Time Sources May Auto-Activate

Development-time sources are allowed to auto-activate.

This includes:

- `--discover=classes`
- `car.d`
- `sar.d`

Rationale:

- these shapes are used while actively developing or debugging
- the purpose is to make the current work immediately executable
- explicit activation would add friction without much safety benefit in this context

### 2. Packaged Artifacts Should Not Auto-Activate By Default

Packaged artifacts should remain searchable but should not auto-activate by default.

This includes:

- `component.d/*.car`
- packaged subsystem artifacts outside explicit subsystem selection

Rationale:

- packaged artifacts are closer to deployable inventory than to a live work area
- auto-activating everything in `component.d` creates noise and ambiguity
- explicit selection is more suitable for production and for predictable CLI behavior

### 3. Packaged Component Artifacts Should Be Explicitly Selected

The preferred direction is:

- `component.d/*.car`
  - searchable
  - not auto-activated
- explicit component activation by component name

This mirrors the subsystem approach:

- subsystem artifact
  - explicitly selected by subsystem name
- component artifact
  - explicitly selected by component name

### 4. Discovery And Activation Must Be Distinguished

The discussion clarified three separate concepts:

- discovery
  - an artifact or source is found
- search eligibility
  - the found artifact may participate in selector resolution or assembly lookup
- activation
  - the artifact becomes part of the effective runtime/component set

The intended policy is not “everything discovered becomes active”.

### 5. Intended Baseline Policy

The current intended baseline is:

- `--discover=classes`
  - discover + activate
- `car.d`
  - discover + activate
- `sar.d`
  - discover + activate
- `component.d/*.car`
  - discover/search only
  - no default activation
- subsystem artifact
  - explicit subsystem selection

## DX Implication

This direction is intended to improve DX while also supporting real operations.

Benefits:

- development-time sources remain fast to try
- packaged artifacts do not create accidental runtime noise
- subsystem and component activation become conceptually symmetric
- the same activation policy can be used in both samples and production

## Next Step

The next implementation step is:

- add component activation policy to CNCF runtime behavior
- then review existing samples and remove unnecessary `--component-repository` and `--no-default-components` options where possible
