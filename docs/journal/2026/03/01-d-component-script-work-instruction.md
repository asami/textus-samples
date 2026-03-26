# 01.d-component-script Work Instruction

Status: `Active Instruction`

This is the active work-order document for `01.d-component-script`.
Do not rewrite this file into a result note or completion report.

The phase checklist is the status authority.

Status authority:

- [`docs/phase/samples/01.d-component-script.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.d-component-script.md)

## Purpose

Build `01.d-component-script` as a single-file `scala-cli` script sample.

This lab is not a normal multi-file sample project.
This lab must demonstrate the script form described in the SimpleModeling HelloWorld article:

- one script file
- `scala-cli` shebang
- CNCF script DSL
- a management-program style use case

The intended message is:

- a small management program can be written as one script file
- the script still runs on CNCF
- the script is appropriate for operational tooling and management tasks

## Reference

Base the sample on this article:

- [HelloWorldで理解するCNCFの実行モデル](https://www.simplemodeling.org/ja/component-based-development/cncf-component-helloworld.html)

Important reference shape from the article:

```scala
#!/usr/bin/env -S scala-cli shebang
//> using repository "https://www.simplemodeling.org/maven"
//> using dep "org.goldenport:goldenport-cncf_3:..."
import org.goldenport.cncf.dsl.script.*
@main def main(args: String*): Unit = run(args) { call =>
  "hello world"
}
```

## Mini-Low Rules

If a smaller model is assigned this work, follow only this process:

1. read the current sample README
2. read the phase checklist
3. read the reference article
4. replace the wrong multi-file-project idea with the single-file `scala-cli` script idea
5. update the README and checklist
6. stop

Do not invent a full sbt project.
Do not create a large Scala source tree unless the user explicitly asks for it.

## Required Outcome

`01.d-component-script` must explain and demonstrate:

- one file script execution
- `scala-cli` based startup
- CNCF script DSL
- management-program style usage

At minimum the sample must provide:

- one concrete script file example
- README guidance for how that script is executed
- explanation of why this form is useful for management commands

## Fixed Work Order

1. Rewrite the sample definition.

`01.d-component-script` must be treated as a script sample, not as a normal Component project sample.

2. Provide one concrete single-file script example.

The example must use:

- `#!/usr/bin/env -S scala-cli shebang`
- `import org.goldenport.cncf.dsl.script.*`
- `run(args) { ... }`

3. Make the example management-oriented.

The script should be described as a small management/operational program, not as a business application.

4. Rewrite the README.

The README must clearly explain:

- this sample is a single-file `scala-cli` script
- why script form is useful for management commands
- how it differs from the formal Component samples
- how it still runs on CNCF
- how it relates to `command`, `server`, `client`, and `script`

5. Update the phase checklist honestly.

Do not mark it `DONE` unless the README and sample definition actually match the script-based design.

## Concrete README Requirements

The README must explicitly state all of the following:

- this sample is centered on one script file
- this is suitable for management programs and operational tooling
- this is different from the earlier multi-file Component samples
- the script DSL still generates and runs through CNCF runtime behavior
- for larger or more structured behavior, formal Component definition should be preferred

## Pass Conditions

This task is complete only if all of the following are true:

- `01.d-component-script` is described as a single-file `scala-cli` script sample
- the README no longer implies a normal sbt multi-file sample is the main target
- the sample contains or describes one concrete script example
- the management-program use case is explicit
- [`docs/phase/samples/01.d-component-script.md`](/Users/asami/src/dev2026/cncf-samples/docs/phase/samples/01.d-component-script.md) is updated honestly

## Notes For Execution

Prefer simple and direct wording.
Prefer one concrete script example over a broad taxonomy.
