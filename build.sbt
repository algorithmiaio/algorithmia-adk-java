
name := "algorithm-handler"

organization := "com.algorithmia"

version := "1.1.0.rc3"

autoScalaLibrary := false

// More compiler warnings
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xlint")

javacOptions ++= Seq("-source", "8", "-target", "8")

javacOptions in doc := Seq("-source", "8")

libraryDependencies ++= Seq(
  "com.google.code.gson" % "gson" % "2.6.2",
  "org.apache.httpcomponents" % "httpasyncclient" % "4.1.1",
  "commons-io" % "commons-io" % "2.5",
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "junit" % "junit" % "4.12",
  "org.springframework" % "spring-core" % "5.1.7.RELEASE"
)

// Disable using the Scala version in published artifacts
crossPaths := false

testOptions += Tests.Argument(TestFrameworks.JUnit, "-av")
