import org.goldenport.cozy.CozyPlugin.autoImport._

ThisBuild / organization := "org.sample"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .enablePlugins(org.goldenport.cozy.CozyPlugin)
  .settings(
    name := "textus-samples-07-b-event-job-server-client-lab",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    cozyGeneratorBackend := "cozy",
    cozyDelegateProjectDir := None,
    cozyDelegateCommand := Seq("bash", baseDirectory.value.toPath.getParent.getParent.resolve("bin/cozy").toFile.getAbsolutePath),
    resolvers ++= Seq(
      Resolver.defaultLocal,
      Resolver.mavenLocal,
      "SimpleModeling.org" at "https://www.simplemodeling.org/repository/maven"
    ),
    libraryDependencies +=
      "org.goldenport" %% "goldenport-cncf" % cncfVersion(baseDirectory.value),
    cozyManifestMetadata ++= Map(
      "component" -> "event-driven",
      "boundedContext" -> "events",
      "domain" -> "event-driven"
    ),
    Test / fork := false
  )

def cncfVersion(base: java.io.File): String = {
  sys.env.get("CNCF_VERSION")
    .orElse {
      val versionFile = base.toPath.getParent.getParent.resolve("versions/cncf-version.conf").toFile
      if (versionFile.isFile) {
        Some(IO.read(versionFile).trim).filter(_.nonEmpty)
      } else {
        None
      }
    }
    .getOrElse(sys.error("CNCF_VERSION or versions/cncf-version.conf is required"))
}
