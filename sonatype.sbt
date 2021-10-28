publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

credentials += Credentials("Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  scala.sys.env("SONATYPE_USERNAME"),
  scala.sys.env("SONATYPE_PASSWORD"))

// Stuff sonatype wants
pomExtra := (
  <url>http://www.github.com/algorithmiaio/algorithm-handler-java</url>
  <licenses>
    <license>
      <name>The MIT License (MIT)</name>
      <url>http://opensource.org/licenses/mit-license.php</url>
       <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:git@github.com:algorithmiaio/algorithm-handler-java.git</connection>
    <url>https://github.com/algorithmiaio/algorithm-handler-java</url>
  </scm>
  <developers>
    <developer>
      <name>Algorithmia</name>
      <email>support@algorithmia.com</email>
      <organization>Algorithmia</organization>
    </developer>
  </developers>
)

// Stuff sonatype does not want
pomIncludeRepository := { _ => false }
