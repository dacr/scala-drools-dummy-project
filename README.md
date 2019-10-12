# Drools & Scala usage example

```bash
$ sbt test
$ sbt run
$ sbt universal:packageBin
# -- packaged artifact is placed in target/universal directory
```

Some advices :

- don't use fat jar with drools, switch to sbt-native-packager
- Scala case classes are perfect for your rules
- Use only java collections within the classes used by your rules.
  Avoid the scala collections in that precise case but rely on
  collection.JavaConversions._ implicits to hide that restriction.
- In knowledge bases only use declarative classes (declare) for
  internal usage, such as intermediary reasoning state.
