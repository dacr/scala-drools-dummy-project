name := "ScalaDroolsDummyProject"

version := "4"

scalaVersion := "2.11.8"

mainClass in assembly := Some("dummy.Dummy")

assemblyJarName := "dummy.jar"

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/junitresults")

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
).map("org.drools" % _ % "6.5.0.Final")


libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.2.1"
  //"org.codehaus.janino"      % "janino"            % "2.5.16"   // For drools
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
        "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4")
    case _ =>
      libraryDependencies.value
  }
}

assemblyMergeStrategy in assembly := {
    case PathList("javax", "xml", xs @ _*) => MergeStrategy.first
    case PathList("org", "xmlpull", xs @ _*) => MergeStrategy.first
    case x if x contains "rootdoc.txt" => MergeStrategy.first
    case x if x contains ".xls" => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
}

initialCommands in console := """import dummy._"""

resolvers += "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases"

resolvers += "jboss-jsr94" at "http://repository.jboss.org/nexus/content/groups/public-jboss"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"
