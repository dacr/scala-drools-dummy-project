name := "scala-drools-dummy-project"

version := "7"

scalaVersion := "2.13.12"

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/junitresults")

enablePlugins(JavaAppPackaging)

libraryDependencies ++= Seq(
  "drools-compiler",
  "drools-ruleunits-engine",
  "drools-mvel",
).map("org.drools" % _ % "9.44.0.Final")

libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.2.3"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

initialCommands in console := """
   import dummy._
"""
