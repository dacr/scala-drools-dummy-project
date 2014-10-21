import AssemblyKeys._

seq(assemblySettings: _*)

name := "ScalaDroolsDummyProject"

version := "3"

scalaVersion := "2.10.4"

mainClass in assembly := Some("dummy.Dummy")

jarName in assembly := "dummy.jar"



libraryDependencies ++= {
  Seq("drools-compiler", "drools-core","drools-jsr94", "drools-decisiontables", "knowledge-api")
    .map("org.drools" % _ % "5.5.0.Final")
}

libraryDependencies += "com.sun.xml.bind" % "jaxb-xjc" % "2.2.4-1"

libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.2"


libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.+" % "test"

libraryDependencies += "junit" % "junit" % "4.+" % "test"

initialCommands in console := """import dummy._"""


resolvers += "JBoss third party releases repository" at "https://repository.jboss.org/nexus/content/repositories/thirdparty-releases"

