import org.goldenport.cozy.CozyPlugin.autoImport._

ThisBuild / organization := "org.sample"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .enablePlugins(org.goldenport.cozy.CozyPlugin)
  .settings(
    name := "cncf-samples-06-aggregate",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    cozyGeneratorBackend := "cozy",
    cozyDelegateProjectDir := Some(file("/Users/asami/src/dev2025/cozy")),
    resolvers ++= Seq(
      Resolver.defaultLocal,
      Resolver.mavenLocal,
      "SimpleModeling.org" at "https://www.simplemodeling.org/maven"
    ),
    libraryDependencies ++= Seq(
      "org.goldenport" %% "goldenport-cncf" % cncfVersion(baseDirectory.value),
      "org.goldenport" %% "goldenport-core" % "0.3.1-SNAPSHOT",
      "org.simplemodeling" %% "simplemodeling-model" % "0.1.1-SNAPSHOT"
    ),
    cozyManifestMetadata ++= Map(
      "component" -> "aggregate-sample",
      "boundedContext" -> "orders",
      "domain" -> "aggregate"
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
