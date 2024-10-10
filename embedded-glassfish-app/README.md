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

## Run with runnable JAR

java -jar target/dependencies/glassfish-embedded-all.jar target/webapp.war 

## Run with timing

```
STARTUP_PHRASE='successfully deployed' bash ../measure-startup-time.sh \
  embedded-gf http://localhost:8080/ java -jar target/dependencies/glassfish-embedded-all.jar target/webapp.war
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



### Run with timing

```
STARTUP_PHRASE='successfully deployed' bash ../measure-startup-time.sh \
  embedded-gf http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishApp
```

Or, with the exploded archive:

```
STARTUP_PHRASE='successfully deployed' bash ../measure-startup-time.sh \
  embedded-gf http://localhost:8080/ java -cp 'target/classes:target/dependencies/*' ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishExplodedWarApp
```

## Run with GlassFish static shell

The file `glassfish-embedded-static-shell.jar` is used instead of `glassfish-embedded.jar`. Classes from `grizzly-npn-api.jar` are missing, so we need to add it to the classpath. 

### With timing

```
STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  glassfish7 http://localhost:8080/ java -cp target/classes:../glassfish-7-app/target/glassfish7/glassfish/lib/embedded/glassfish-embedded-static-shell.jar:../glassfish-7-app/target/glassfish7/glassfish/lib/bootstrap/grizzly-npn-api.jar ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishApp
```

### With timing in Docker

STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  glassfish7 http://localhost:8080/ docker run -p 8080:8080 -p 4848:4848 -v `pwd`/target:/target ghcr.io/eclipse-ee4j/glassfish:latest java -cp /target/classes:/opt/glassfish7/glassfish/lib/embedded/glassfish-embedded-static-shell.jar:/opt/glassfish7/glassfish/lib/bootstrap/grizzly-npn-api.jar ee.omnifish.piranhafromgf.embeddedgf.EmbeddedGlassfishApp