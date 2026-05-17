import org.goldenport.cozy.CozyPlugin.autoImport._

ThisBuild / organization := "org.sample"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .enablePlugins(org.goldenport.cozy.CozyPlugin)
  .settings(
    name := "textus-samples-07-b-aggregate-relation-boundary-model",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    cozyGeneratorBackend := "cozy",
    cozyDelegateProjectDir := None,
    cozyDelegateCommand := Seq(baseDirectory.value.toPath.getParent.getParent.resolve("bin/cozy").toFile.getAbsolutePath),
    resolvers ++= Seq(
      Resolver.defaultLocal,
      Resolver.mavenLocal,
      "SimpleModeling.org" at "https://www.simplemodeling.org/repository/maven"
    ),
    libraryDependencies ++= Seq(
      "org.goldenport" %% "goldenport-cncf" % cncfVersion(baseDirectory.value),
      "org.goldenport" %% "goldenport-core" % goldenportCoreVersion(baseDirectory.value),
      "org.simplemodeling" %% "simplemodeling-model" % simplemodelingModelVersion(baseDirectory.value)
    ),
    cozyManifestMetadata ++= Map(
      "component" -> "aggregate-relation-boundary-sample",
      "boundedContext" -> "orders",
      "domain" -> "aggregate"
    ),
    Test / fork := false
  )

def cncfVersion(base: java.io.File): String = {
  sampleVersion(base, "CNCF_VERSION", "cncf-version.conf")
}

def goldenportCoreVersion(base: java.io.File): String =
  sampleVersion(base, "GOLDENPORT_CORE_VERSION", "goldenport-core-version.conf")

def simplemodelingModelVersion(base: java.io.File): String =
  sampleVersion(base, "SIMPLEMODELING_MODEL_VERSION", "simplemodeling-model-version.conf")

def sampleVersion(base: java.io.File, envName: String, fileName: String): String = {
  sys.env.get(envName)
    .orElse {
      val versionFile = base.toPath.getParent.getParent.resolve("versions").resolve(fileName).toFile
      if (versionFile.isFile) Some(IO.read(versionFile).trim).filter(_.nonEmpty) else None
    }
    .getOrElse(sys.error(s"$envName or versions/$fileName is required"))
}
