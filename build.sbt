
name := "algorithm-handler"

organization := "com.algorithmia"

version := "2.0.0"


autoScalaLibrary := false

// More compiler warnings
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xlint", "--illegal-access=permit")

javacOptions ++= Seq("-source", "8", "-target", "8")

javacOptions in doc := Seq("-source", "8")

libraryDependencies ++= Seq(
  "com.google.code.gson" % "gson" % "2.8.5",
  "org.apache.httpcomponents" % "httpasyncclient" % "4.1.1",
  "commons-io" % "commons-io" % "2.5",
  "org.apache.commons" % "commons-lang3" % "3.5",
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.springframework" % "spring-core" % "5.1.7.RELEASE"
)

// Disable using the Scala version in published artifacts
crossPaths := false

testOptions += Tests.Argument(TestFrameworks.JUnit, "-av")
