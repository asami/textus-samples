#!/usr/bin/env -S scala-cli shebang
//> using repository "https://www.simplemodeling.org/maven"
//> using dep "org.goldenport:goldenport-cncf_3:0.3.14-SNAPSHOT"

import org.goldenport.cncf.dsl.script.*

@main def main(args: String*): Unit =
  run(args) { call =>
    "Hello CNCF"
  }
