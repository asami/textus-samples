# 04.a-designed-sync-command-lab Implementation Record

Status: `Completed`

## Summary

`04.a-designed-sync-command-lab` demonstrates a command that returns its
result immediately by design through CML operation metadata.

## Facts

- The sample lives under `samples/04.a-designed-sync-command-lab`.
- The component help target is `DesignedSync`.
- The command target is `DesignedSync.Item.createItem`.
- The command is modeled with `EXECUTION=sync` in `src/main/cozy/cqrs.cml`.
- The command returns the created item record immediately.
- The command does not return a job id.
- `CncfMain --discover=classes` is sufficient for help and execution.
- The README was updated to explain why this is design-time sync and how it differs from `04-cqrs`.

## Notes

- The designed-sync behavior now comes from the generated runtime surface, not
  from a hand-written component hook.
- The key runtime checks were:
  - `command help DesignedSync`
  - `command help DesignedSync.Item.createItem`
  - `command DesignedSync.Item.createItem --name beta --title Beta`
- This record only states facts that were confirmed by build and runtime checks.
