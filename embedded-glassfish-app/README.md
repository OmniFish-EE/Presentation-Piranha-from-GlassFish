# A simple servlet application on Eclipse GlassFish Embedded 7

## Build

```
mvn clean package
```

## Run with Maven

```
mvn exec:exec
```

Then access the app at http://localhost:8080


You can specify a different HTTP port on the command line:

```
mvn exec:exec -Dhttp.port=9090
```

## Run with plain `java`

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishApp
```

This variant creates a temporary WAR file on disk using the ScatteredArchive (an archive assembled during the startup)
Or, alternatively, to get a slightly better startup time, run the version that deploys an exploded WAR archive:

```
java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishExplodedWarApp
```

Then access the app at http://localhost:8080



## Run with timing

```
STARTUP_PHRASE='successfully deployed' bash ../measure-startup-time.sh \
  embedded-gf http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishApp
```

Or, with the exploded archive:

```
STARTUP_PHRASE='successfully deployed' bash ../measure-startup-time.sh \
  embedded-gf http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishExplodedWarApp
```