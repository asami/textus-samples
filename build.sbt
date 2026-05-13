ThisBuild / organization := "org.example.textussamples"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.3.6"

enablePlugins(org.goldenport.cozy.CozyPlugin)

// The repository root is a coordination point.
// Each sample under samples/ is intended to remain independently buildable.
