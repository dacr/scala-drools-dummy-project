name := "ScalaDroolsDummyProject"

version := "6"

scalaVersion := "2.12.6"

//testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/junitresults")

//enablePlugins(JavaAppPackaging)

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-Xexperimental",
  "-feature",
  "-language:implicitConversions",
  "-language:reflectiveCalls"
)

libraryDependencies ++= Seq(
    "drools-compiler",
    "drools-core"
).map("org.drools" % _ % "7.8.0.Final")


libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.2.3"
  //"org.codehaus.janino"      % "janino"            % "2.5.16"   // For drools
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

initialCommands in console := """
   import dummy._
"""

//resolvers += "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases"
//resolvers += "jboss-jsr94" at "http://repository.jboss.org/nexus/content/groups/public-jboss"
//resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

