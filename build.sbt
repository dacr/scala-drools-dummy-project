import AssemblyKeys._

seq(assemblySettings: _*)

name := "ScalaDroolsDummyProject"

version := "2"

scalaVersion := "2.9.2"

mainClass in assembly := Some("dummy.Dummy")

jarName in assembly := "dummy.jar"



libraryDependencies ++= {
  Seq("drools-compiler", "drools-core","drools-jsr94", "drools-decisiontables", "knowledge-api")
    .map("org.drools" % _ % "5.4.0.Final")
}

libraryDependencies += "com.sun.xml.bind" % "jaxb-xjc" % "2.2.4-1"

libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.2"



libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

resolvers += "JBoss third party releases repository" at "https://repository.jboss.org/nexus/content/repositories/thirdparty-releases"

