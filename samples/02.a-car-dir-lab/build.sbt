ThisBuild / scalaVersion := "3.3.7"
name := "cncf-samples-02-a-car-dir-lab"

def cncfVersion(base: java.io.File): String = {
  sys.env.get("CNCF_VERSION")
    .orElse {
      val versionFile = base.toPath.getParent.getParent.resolve("versions/cncf-version.conf").toFile
      if (versionFile.isFile) Some(IO.read(versionFile).trim).filter(_.nonEmpty) else None
    }
    .getOrElse(sys.error("CNCF_VERSION or versions/cncf-version.conf is required"))
}

resolvers += Resolver.defaultLocal
resolvers += Resolver.mavenLocal
resolvers += "SimpleModeling.org" at "https://www.simplemodeling.org/maven"

libraryDependencies +=
  "org.goldenport" %% "goldenport-cncf" % cncfVersion(baseDirectory.value)
