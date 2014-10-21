Drools & Scala usage example

$ sbt test

Some advices :
 - Scala case classes are perfect for your rules
 - Use only java collections within classes used by your rules
   Avoid the scala collections in that precise case but rely on
   collections.JavaConversions._ implicits to hide that restriction
 - In knowledge bases only use declarative classes (declare) for
   internal usage, such as intermediary reasoning state.

