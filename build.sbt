name := "learn-scalacheck"

version := "1.0"

scalaVersion := "2.12.2"

val scalatestVersion = "3.0.3"
val scalacheckVersion = "1.13.5"
val guavaVersion = "22.0"

libraryDependencies ++= Seq(
  "com.google.guava" % "guava" % guavaVersion,
  "org.scalatest" % "scalatest_2.12" % scalatestVersion % "test",
  "org.scalacheck" % "scalacheck_2.12" % scalacheckVersion // % "test"
)
