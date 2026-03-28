ThisBuild / organization := "org.sample"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .settings(
    name := "cncf-samples-02c-crud-sqlite-lab",
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    resolvers ++= Seq(
      Resolver.defaultLocal,
      Resolver.mavenLocal,
      "SimpleModeling.org" at "https://www.simplemodeling.org/maven"
    ),
    libraryDependencies +=
      "org.goldenport" %% "goldenport-cncf" % "0.3.14-SNAPSHOT",
    Test / fork := false
  )
