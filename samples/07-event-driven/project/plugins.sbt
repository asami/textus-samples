import java.io.File

@annotation.tailrec
def repoRoot(dir: File): File = {
  val marker = new File(dir, "versions/sbt-cozy-version.conf")
  if (marker.isFile) dir
  else Option(dir.getParentFile).map(repoRoot).getOrElse(sys.error("versions/sbt-cozy-version.conf is required"))
}

def sbtCozyVersion: String = {
  sys.env.get("SBT_COZY_VERSION").filter(_.nonEmpty).getOrElse {
    val versionFile = new File(repoRoot(new File(".").getCanonicalFile), "versions/sbt-cozy-version.conf")
    val version = scala.io.Source.fromFile(versionFile).mkString.trim
    if (version.nonEmpty) version else sys.error("sbt-cozy version is empty")
  }
}

resolvers += Resolver.defaultLocal

resolvers += "simplemodeling-maven" at "https://www.simplemodeling.org/repository/maven"

addSbtPlugin("org.goldenport" % "sbt-cozy" % sbtCozyVersion)
