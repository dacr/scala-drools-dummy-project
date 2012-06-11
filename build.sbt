import AssemblyKeys._

seq(assemblySettings: _*)

name := "ScalaDroolsDummyProject"

version := "0.0.1"

scalaVersion := "2.9.2"

mainClass in assembly := Some("dummy.Dummy")

jarName in assembly := "dummy.jar"



libraryDependencies += "org.drools" % "drools-compiler" % "5.3.1.Final"

libraryDependencies += "org.drools" % "drools-core" % "5.3.1.Final"

libraryDependencies += "org.drools" % "drools-jsr94"  % "5.3.1.Final"

libraryDependencies += "org.drools" % "drools-decisiontables"  % "5.3.1.Final"

libraryDependencies += "org.drools" % "knowledge-api"  % "5.3.1.Final"



libraryDependencies += "com.thoughtworks.xstream" % "xstream" % "1.4.2"
            


libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"

resolvers += "JBoss third party releases repository" at "https://repository.jboss.org/nexus/content/repositories/thirdparty-releases"

