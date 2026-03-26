# CNCF Discover Classes Factory Mini-Low Verification Record

Status: `Completed`

Reviewed and updated on 2026-03-26.

## Summary

This verification checked whether generated model-driven components can run with `--discover=classes` alone.

The current result is complete verification.

## Verification

Executed in order:

1. `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud"`
2. `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help SimpleEntityCrudLab"`
3. `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.Item"`
4. `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help Crud.Item.createItem"`
5. `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help SimpleEntityCrudLab.Item"`
6. `sbt --batch "runMain org.goldenport.cncf.CncfMain --discover=classes command help SimpleEntityCrudLab.Item.createItem"`

Observed result:

- `Crud` resolved through `--discover=classes` alone
- `SimpleEntityCrudLab` resolved through `--discover=classes` alone
- `Crud.Item` resolved as a service through `--discover=classes`
- `Crud.Item.createItem` resolved as an operation through `--discover=classes`
- `SimpleEntityCrudLab.Item` resolved as a service through `--discover=classes`
- `SimpleEntityCrudLab.Item.createItem` resolved as an operation through `--discover=classes`

## Notes

- class discovery now reaches generated component, service, and operation selectors
- the acceptance criteria in the instruction are satisfied
- generated component companion factories are now applied correctly during class discovery
