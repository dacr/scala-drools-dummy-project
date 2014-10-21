import AssemblyKeys._

seq(assemblySettings: _*)

name := "ScalaDroolsDummyProject"

version := "3"

scalaVersion := "2.11.2"

mainClass in assembly := Some("dummy.Dummy")

jarName in assembly := "dummy.jar"


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
    "drools-core",
    "drools-jsr94",
    "drools-decisiontables",
    "knowledge-api"
).map("org.drools" % _ % "6.1.0.Final")


libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.1.2",
  "com.sun.xml.bind"         % "jaxb-xjc"          % "2.2.4-1", // For drools
  "com.thoughtworks.xstream" % "xstream"           % "1.4.2",   // For drools
  "org.codehaus.janino"      % "janino"            % "2.5.16"   // For drools
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.1.+" % "test",
  "junit"          % "junit"     % "4.+"   % "test"
)

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2")
    case _ =>
      libraryDependencies.value
  }
}


initialCommands in console := """import dummy._"""

resolvers += "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"
