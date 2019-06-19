
name := "algorithm-handler"

organization := "com.algorithmia"

version := "1.2.0-rc1"

autoScalaLibrary := false

// More compiler warnings
scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xlint")

javacOptions ++= Seq("-source", "9", "-target", "9")

//javacOptions in doc := Seq("-source", "9")

libraryDependencies ++= Seq(
  "com.google.code.gson" % "gson" % "2.6.2",
  "org.apache.httpcomponents" % "httpasyncclient" % "4.1.1",
  "commons-io" % "commons-io" % "2.5",
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "junit" % "junit" % "4.12",
  "org.springframework" % "spring-core" % "5.1.7.RELEASE",
  "com.github.javasync" % "AsyncFileRw" % "1.0.0"
)

// Disable using the Scala version in published artifacts
crossPaths := false

testOptions += Tests.Argument(TestFrameworks.JUnit, "-av")
