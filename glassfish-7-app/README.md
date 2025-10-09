# A simple servlet application on Eclipse GlassFish 7

## Build

```
mvn -Pdownload-gf clean package
```

## Run

```
target/glassfish7/bin/startserv domain1
```

Then access the app at http://localhost:8080/simpleapp

## Building AOT cache

Requires Java 25

```
target/glassfish7/bin/asadmin start-domain domain1
asadmin create-jvm-options '-XX\:AOTCacheOutput=glassfish.aot'
target/glassfish7/bin/asadmin stop-domain domain1
target/glassfish7/bin/asadmin start-domain domain1
asadmin delete-jvm-options '-XX\:AOTCacheOutput=glassfish.aot'
asadmin create-jvm-options '-XX\:AOTCache=glassfish.aot'
target/glassfish7/bin/asadmin stop-domain domain1
```

Then run normally. It will run with the AOT Cache. Though gives some errors with GF 7.0.25 and ignores the cache.

## Run with timing

```
STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  glassfish7 http://localhost:8080/simpleapp target/glassfish7/bin/startserv domain1 
```

## Run with timing in Docker

```
STARTUP_PHRASE='successfully deployed\|simpleapp done' bash ../measure-startup-time.sh \
  glassfish7-docker http://localhost:8080/simpleapp/ docker run -p 8080:8080 -p 4848:4848 -v `pwd`/target/glassfish7/glassfish/domains/domain1/autodeploy:/opt/glassfish7/glassfish/domains/domain1/autodeploy ghcr.io/eclipse-ee4j/glassfish:latest
```

## Run on WildFly

### Run with timing in Docker

```
STARTUP_PHRASE='Deployed' bash ../measure-startup-time.sh \
  wildfly-docker http://localhost:8080/simpleapp/ docker run -p 8080:8080 -p 9990:9990 -v `pwd`/target/glassfish7/glassfish/domains/domain1/autodeploy:/opt/jboss/wildfly/standalone/deployments/ quay.io/wildfly/wildfly:latest /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
```