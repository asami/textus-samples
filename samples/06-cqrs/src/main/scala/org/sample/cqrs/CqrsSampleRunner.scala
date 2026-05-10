package org.sample.cqrs

import io.circe.Json
import org.goldenport.Consequence
import org.goldenport.protocol.{Property, Request}
import org.goldenport.cncf.cli.{CncfRuntime, RunMode}
import org.goldenport.cncf.component.{ComponentCreate, ComponentFactory, ComponentOrigin}

object CqrsSampleRunner {
  def main(args: Array[String]): Unit = {
    val itemId = args.headOption.getOrElse(sys.error("missing item id"))
    val runtime = new CncfRuntime
    val subsystem = runtime.initializeForEmbedding(modeHint = Some(RunMode.Command)).TAKE
    val component = ComponentFactory().bootstrap(
      new CqrsComponent.Factory().createPrimary(ComponentCreate(subsystem, ComponentOrigin.Builtin))
    )
    val _ = subsystem.add(Vector(component))
    try {
      val created = _executeString(
        subsystem,
        Request.of(
          component = "Cqrs",
          service = "entity",
          operation = "createItemRecord",
          properties = List(
            Property("textus.runtime.command.execution-mode", "sync-direct-no-job", None),
            Property("cncf.security.privilege", "content_manager", None),
            Property("id", itemId, None),
            Property("name", "gamma", None),
            Property("title", "Gamma", None)
          )
        )
      )
      val loaded = _executeString(
        subsystem,
        Request.of(
          component = "Cqrs",
          service = "entity",
          operation = "loadItem",
          properties = List(
            Property("cncf.security.privilege", "content_manager", None),
            Property("id", itemId, None)
          )
        )
      )
      println(Json.obj("created" -> Json.fromString(created), "loaded" -> Json.fromString(loaded)).noSpaces)
    } finally {
      runtime.closeEmbedding()
    }
  }

  private def _executeString(
    subsystem: org.goldenport.cncf.subsystem.Subsystem,
    request: Request
  ): String =
    subsystem.execute(request) match {
      case Consequence.Success(response) => response.print
      case Consequence.Failure(c) => throw new IllegalStateException(c.show)
    }
}
