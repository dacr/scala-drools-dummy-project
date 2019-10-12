name := "scala-drools-dummy-project"

version := "7"

scalaVersion := "2.12.10"

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/junitresults")

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
    "drools-compiler",
    "drools-core"
).map("org.drools" % _ % "7.27.0.Final")


libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.2.3"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

initialCommands in console := """
   import dummy._
"""
