name := "ScalaDroolsDummyProject"

version := "5"

scalaVersion := "2.12.4"

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
    "drools-core"
).map("org.drools" % _ % "7.5.0.Final")


libraryDependencies ++= Seq(
  "ch.qos.logback"           % "logback-classic"   % "1.2.3"
  //"org.codehaus.janino"      % "janino"            % "2.5.16"   // For drools
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
)

initialCommands in console := """import dummy._"""

resolvers += "jboss-releases" at "https://repository.jboss.org/nexus/content/repositories/releases"

resolvers += "jboss-jsr94" at "http://repository.jboss.org/nexus/content/groups/public-jboss"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"


assemblyMergeStrategy in assembly  := {
    case PathList("javax", "xml", xs @ _*) => MergeStrategy.first
    case PathList("org", "xmlpull", xs @ _*) => MergeStrategy.first
    case "META-INF/kie.conf" => MergeStrategy.first
    case "META-INF/ErraiApp.properties" => MergeStrategy.first // META-INF/ErraiApp.properties
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
}
