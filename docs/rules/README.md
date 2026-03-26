# Rules

This directory is reserved for truly repository-specific rules only.

Default policy:

- use the shared AI contract under `ai/directive`
- do not restate or shadow shared directive rules here
- add a project rule only when the repository has a concrete local constraint that cannot be expressed as ordinary sample documentation

This means most implementation guidance should live in:

- `docs/phase/` for progress tracking
- `docs/journal/` for instructions and records
- `guide/` for user-facing documentation

The shared AI contract under `ai/directive` takes precedence.
